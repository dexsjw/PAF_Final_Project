package ibf2021.paf.finalproject.model;

import static ibf2021.paf.finalproject.Constants.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Subscription;

import ibf2021.paf.finalproject.FinalProjectApplication;

public class StripeSubscription {

    private String id;
    private String customerId;      // tele user.id
    private String priceId;
    private String paymentBehavior = "default_incomplete";
    private String currentPeriodStart;
    private String currentPeriodEnd;
    private String clientSecret;
    private String latestInvoice;

    private static final Logger logger = Logger.getLogger(FinalProjectApplication.class.getName());
    private String stripeKey = System.getenv(ENV_STRIPE_KEY);

    public Subscription create(String priceId, String customerId) throws StripeException {
        Stripe.apiKey = stripeKey;
        Map<String, Object> item1 = new HashMap<>();
        item1.put("price", priceId);
        List<Object> items = new ArrayList<>();
        items.add(item1);
        Map<String, Object> params = new HashMap<>();
        params.put("customer", customerId);
        params.put("items", items);
        params.put("payment_behavior", paymentBehavior);
        Subscription subscription = Subscription.create(params);         
        id = subscription.getId();    
        return subscription;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getPriceId() { return priceId; }
    public void setPriceId(String priceId) { this.priceId = priceId; }

    public String getPaymentBehavior() { return paymentBehavior; }
    public void setPaymentBehavior(String paymentBehavior) { this.paymentBehavior = paymentBehavior; }

    public String getCurrentPeriodStart() { return currentPeriodStart; }
    public void setCurrentPeriodStart(String currentPeriodStart) { this.currentPeriodStart = currentPeriodStart; }

    public String getCurrentPeriodEnd() { return currentPeriodEnd; }
    public void setCurrentPeriodEnd(String currentPeriodEnd) { this.currentPeriodEnd = currentPeriodEnd; }

    public String getClientSecret() { return clientSecret; }
    public void setClientSecret(String clientSecret) { this.clientSecret = clientSecret; }

    public String getLatestInvoice() { return latestInvoice; }
    public void setLatestInvoice(String latestInvoice) { this.latestInvoice = latestInvoice; }

}
