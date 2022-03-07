package ibf2021.paf.finalproject.service;

import static ibf2021.paf.finalproject.Constants.ENV_STRIPE_KEY;

import java.util.logging.Logger;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;

import org.springframework.stereotype.Service;

import ibf2021.paf.finalproject.FinalProjectApplication;
import ibf2021.paf.finalproject.model.StripeCustomer;
import ibf2021.paf.finalproject.model.StripeInvoice;
import ibf2021.paf.finalproject.model.StripePaymentIntent;
import ibf2021.paf.finalproject.model.StripePaymentMethod;
import ibf2021.paf.finalproject.model.StripePrice;
import ibf2021.paf.finalproject.model.StripeProduct;
import ibf2021.paf.finalproject.model.StripeSubscription;

@Service
public class StripeSubcriptionService {

    private static final Logger logger = Logger.getLogger(FinalProjectApplication.class.getName());
    private String stripeKey = System.getenv(ENV_STRIPE_KEY);

    public void createSubscriptionService(String prodName, String prodDescription, String priceInterval,
        int priceUnitAmount, String teleUserId, String teleFirstName, String teleUserName,
            String pmCardNum, int pmCardExpMonth, int pmCardExpYear, String pmCardCvc) {
        
        Stripe.apiKey = stripeKey;
        StripeProduct stripeProduct = new StripeProduct();
        StripePrice stripePrice = new StripePrice();
        StripeCustomer stripeCust = new StripeCustomer();
        StripeSubscription stripeSub = new StripeSubscription();
        StripeInvoice stripeInvoice = new StripeInvoice();
        StripePaymentMethod stripePayMtd = new StripePaymentMethod();
        StripePaymentIntent stripePayInt = new StripePaymentIntent();
        
        try {
            stripeProduct.create(prodName, prodDescription);
            String prodId = stripeProduct.getId();
            stripePrice.create(priceInterval, prodId, priceUnitAmount);
            String priceId = stripePrice.getId();
            stripeCust.create(teleUserId, teleFirstName, teleUserName);
            String custId = stripeCust.getId();
            stripeSub.create(priceId, custId);
            String subId = stripeSub.getId();
            String invoiceId = stripeInvoice.getIdFromSubscriptionId(subId);
            stripePayMtd.create(pmCardNum, pmCardExpMonth, pmCardExpYear, pmCardCvc);
            String payMtdId = stripePayMtd.getId();
            String payIntId = stripePayInt.getIdFromInvoiceId(invoiceId);
            stripePayInt.update(payIntId, payMtdId);
            stripePayInt.confirm(payIntId, payMtdId);
        } catch (StripeException e) {
            e.printStackTrace();
        }

    }

}
