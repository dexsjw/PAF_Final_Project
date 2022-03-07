package ibf2021.paf.finalproject.model;

import static ibf2021.paf.finalproject.Constants.ENV_STRIPE_KEY;

import java.util.logging.Logger;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Subscription;

import ibf2021.paf.finalproject.FinalProjectApplication;

public class StripeInvoice {

    private String id;

    private static final Logger logger = Logger.getLogger(FinalProjectApplication.class.getName());
    private String stripeKey = System.getenv(ENV_STRIPE_KEY);

    public String getIdFromSubscriptionId(String subId) throws StripeException {
        Stripe.apiKey = stripeKey;
        Subscription subscription = Subscription.retrieve(subId);
        id = subscription.getLatestInvoice();
        return id;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

}
