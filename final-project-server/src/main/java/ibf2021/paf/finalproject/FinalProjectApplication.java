package ibf2021.paf.finalproject;

import java.util.logging.Logger;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import ibf2021.paf.finalproject.service.BillerBotService;

@SpringBootApplication
public class FinalProjectApplication {

	private static final Logger logger = Logger.getLogger(FinalProjectApplication.class.getName());

	public static void main(String[] args) {
		try {
			TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
			telegramBotsApi.registerBot(new BillerBotService());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		// SpringApplication.run(FinalProjectApplication.class, args);
	}

}
