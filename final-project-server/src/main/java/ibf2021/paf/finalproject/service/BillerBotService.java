package ibf2021.paf.finalproject.service;

import static ibf2021.paf.finalproject.Constants.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerPreCheckoutQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendInvoice;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ibf2021.paf.finalproject.FinalProjectApplication;
import ibf2021.paf.finalproject.model.TeleUser;
import ibf2021.paf.finalproject.repository.TeleRepo;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

@Component
@Service
public class BillerBotService extends TelegramLongPollingBot {

    @Autowired
    private TeleRepo teleRepo;

    private static final Logger logger = Logger.getLogger(FinalProjectApplication.class.getName());
    private String stripeKey = System.getenv(ENV_STRIPE_KEY);

    private Long currentUserId;
    private Long currentChatId;
    private TeleUser user = new TeleUser();
    
    public Long getCurrentUserId() { return currentUserId; }
    public void setCurrentUserId(Long currentUserId) { this.currentUserId = currentUserId; }

    public Long getCurrentChatId() { return currentChatId; }
    public void setCurrentChatId(Long currentChatId) { this.currentChatId = currentChatId; }

    public TeleUser getUser() { return user; }
    public void setUser(TeleUser user) { this.user = user; }

    @Override
    public String getBotUsername() {
        return "BillTheBillerBot";
    }
    
    @Override
    public String getBotToken() {
        String teleBotToken = System.getenv(ENV_TELEBOT_TOKEN);
        return teleBotToken;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            currentUserId = update.getMessage().getFrom().getId();
            currentChatId = update.getMessage().getChatId();
            user = TeleUser.create(
                currentUserId,
                update.getMessage().getFrom().getFirstName(),
                update.getMessage().getFrom().getUserName()
            );
        }
        
        if (update.hasPreCheckoutQuery()) {
            String pcqId = update.getPreCheckoutQuery().getId();
            AnswerPreCheckoutQuery ansPreCkoutQuery = new AnswerPreCheckoutQuery();
            ansPreCkoutQuery.setPreCheckoutQueryId(pcqId);
            ansPreCkoutQuery.setOk(true);
            ansPreCkoutQuery.setErrorMessage("An error has occured with your payment");
            try {
                execute(ansPreCkoutQuery);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            String text1 = "Welcome to a new billing service! Let Bill, our very own Biller Bot, provide its services to you!\n\n";
            String text2 = "Use /help to get some commands to get you started!";
            String text = text1 + text2;
            SendMessage message = new SendMessage(); 
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(text);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if ((update.hasMessage() && update.getMessage().getText().equals("/help"))) {
            String text = getMyCommands(user);
            SendMessage message = new SendMessage(); 
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(text);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if ((update.hasMessage() && update.getMessage().getText().equals("/create"))) {
            List<InlineKeyboardButton> ikbList = new ArrayList<>();
            ikbList.add(
                InlineKeyboardButton.builder()
                    .text("Click here to create a subscription")
                    .url("http://youtube.com")                    // needs be changed
                    .build()
            );
            InlineKeyboardMarkup ikbm = InlineKeyboardMarkup.builder()
                .keyboardRow(ikbList)
                .build();
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText("Create a new subscription with the button below!");
            message.setReplyMarkup(ikbm);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if ((update.hasMessage() && update.getMessage().getText().equals("/list"))) {
/*             Stripe.apiKey = stripeKey;
            Map<String, Object> params = new HashMap<>();
            params.put("payment_method", "pm_1KaaVaFlorMUS1SNmNFAubEt");
            try {
                PaymentIntent payInt = PaymentIntent.retrieve("pi_3Kaa8lFlorMUS1SN0zBmOJZs");
                PaymentIntent updatedPayInt = payInt.confirm(params);
                logger.info(">>>>>>>>>>>>>>>>>>>>\nPaymentIntent Id: %s\n ".formatted(updatedPayInt.getId()));
                logger.info(">>>>>>>>>>>>>>>>>>>>\n %s\n ".formatted(updatedPayInt.toString()));
            } catch (StripeException e) {
                e.printStackTrace();
            } */
        }
    }

    private String getMyCommands(TeleUser user) {
        String jsonStr = Json.createObjectBuilder().build().toString();
        String respStr = user.respForPostReq(getBotToken(), TELE_METHOD_GET_MY_COMMANDS, jsonStr);
        JsonObject respObj = user.jsonStrToJsonObj(respStr);
        JsonArray commandsArr = respObj.getJsonArray("result");
        List<String> textList = commandsArr.stream()
            .map(v -> (JsonObject)v)
            .map(obj -> {
                String command = obj.getString("command");
                String description = obj.getString("description");
                String comText = "/" + command + " - " + description;
                return comText;
            })
            .toList();
            String text = "Use these commands to get started\n\n" + String.join("\n", textList);
            return text;
    }

    public void sendInvoice(int amount, String title, String description) {
        List<LabeledPrice> prices = new ArrayList<>();
        prices.add(
            LabeledPrice.builder()
                .label(description)
                .amount(amount)
                .build()
        );
        SendInvoice sendInvoice = new SendInvoice();
        sendInvoice.setChatId(currentChatId.toString());
        sendInvoice.setTitle(title);
        sendInvoice.setDescription(description);
        sendInvoice.setPayload("4242");
        sendInvoice.setProviderToken(ENV_TELEBOT_PROVIDER_TOKEN);
        sendInvoice.setCurrency("sgd");
        sendInvoice.setPrices(prices);
        try {
            execute(sendInvoice);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void insertTeleUser(TeleUser user) {
        teleRepo.insertTeleUser(user);
    }
    
}
