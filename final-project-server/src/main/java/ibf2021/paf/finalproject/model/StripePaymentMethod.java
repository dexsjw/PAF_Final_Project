package ibf2021.paf.finalproject.model;

import static ibf2021.paf.finalproject.Constants.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentMethod;

import ibf2021.paf.finalproject.FinalProjectApplication;

public class StripePaymentMethod {

    private String id;
    private String cardNumber;
    private int cardExpYear;
    private int cardExpMonth;
    private String cardCvc;

    private static final Logger logger = Logger.getLogger(FinalProjectApplication.class.getName());
    private String stripeKey = System.getenv(ENV_STRIPE_KEY);

    public PaymentMethod create(String cardNum, int cardExpMonth, int cardExpYear, String cardCvc) throws StripeException {
        Stripe.apiKey = stripeKey;
        Map<String, Object> card = new HashMap<>();
        card.put("number", cardNum);
        card.put("exp_month", cardExpMonth);
        card.put("exp_year", cardExpYear);
        card.put("cvc", cardCvc);
        Map<String, Object> params = new HashMap<>();
        params.put("type", "card");
        params.put("card", card);
        PaymentMethod paymentMethod = PaymentMethod.create(params);
        id = paymentMethod.getId();
        return paymentMethod;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public int getCardExpYear() { return cardExpYear; }
    public void setCardExpYear(int cardExpYear) { this.cardExpYear = cardExpYear; }

    public int getCardExpMonth() { return cardExpMonth; }
    public void setCardExpMonth(int cardExpMonth) { this.cardExpMonth = cardExpMonth; }

    public String getCardCvc() { return cardCvc; }
    public void setCardCvc(String cardCvc) { this.cardCvc = cardCvc; }
    
}
