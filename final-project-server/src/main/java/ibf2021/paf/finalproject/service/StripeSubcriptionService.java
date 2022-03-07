package ibf2021.paf.finalproject.service;

import static ibf2021.paf.finalproject.Constants.*;

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

    private String prodId;
    private String priceId;
    private String custId;
    private String subId;
    private String invoiceId;
    private String payMtdId;
    private String payIntId;

    public void createSubscriptionService(String prodName, String prodDesc, String priceInterval,
        int priceUnitAmount, int teleUserId, String teleFirstName, String teleUserName,
            String pmCardNum, int pmCardExpMth, int pmCardExpYear, String pmCardCvc) {
        
        Stripe.apiKey = stripeKey;
        StripeProduct stripeProduct = new StripeProduct();
        StripePrice stripePrice = new StripePrice();
        StripeCustomer stripeCust = new StripeCustomer();
        StripeSubscription stripeSub = new StripeSubscription();
        StripeInvoice stripeInvoice = new StripeInvoice();
        StripePaymentMethod stripePayMtd = new StripePaymentMethod();
        StripePaymentIntent stripePayInt = new StripePaymentIntent();
        
        try {
            stripeProduct.create(prodName, prodDesc);
            prodId = stripeProduct.getId();

            stripePrice.create(priceInterval, prodId, priceUnitAmount);
            priceId = stripePrice.getId();

            stripeCust.create(String.valueOf(teleUserId), teleFirstName, teleUserName);
            custId = stripeCust.getId();
            logger.info(">>>>>>>>>> CustId: %s".formatted(custId));
            stripeSub.create(priceId, custId);
            subId = stripeSub.getId();

            invoiceId = stripeInvoice.getIdFromSubscriptionId(subId);

            stripePayMtd.create(pmCardNum, pmCardExpMth, pmCardExpYear, pmCardCvc);
            payMtdId = stripePayMtd.getId();

            payIntId = stripePayInt.getIdFromInvoiceId(invoiceId);
            stripePayInt.update(payIntId, payMtdId);
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }

    public void confirmPaymentIntent() {
        Stripe.apiKey = stripeKey;
        StripePaymentIntent stripePayInt = new StripePaymentIntent();
        try {
            stripePayInt.confirm(payIntId, payMtdId);
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }

    public String getProdId() { return prodId; }
    public void setProdId(String prodId) { this.prodId = prodId; }

    public String getPriceId() { return priceId; }
    public void setPriceId(String priceId) { this.priceId = priceId; }

    public String getCustId() { return custId; }
    public void setCustId(String custId) { this.custId = custId; }

    public String getSubId() { return subId; }
    public void setSubId(String subId) { this.subId = subId; }

    public String getInvoiceId() { return invoiceId; }
    public void setInvoiceId(String invoiceId) { this.invoiceId = invoiceId; }

    public String getPayMtdId() { return payMtdId; }
    public void setPayMtdId(String payMtdId) { this.payMtdId = payMtdId; }

    public String getPayIntId() { return payIntId; }
    public void setPayIntId(String payIntId) { this.payIntId = payIntId; }

}
