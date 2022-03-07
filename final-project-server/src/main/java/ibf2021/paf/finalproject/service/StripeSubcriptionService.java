package ibf2021.paf.finalproject.service;

import static ibf2021.paf.finalproject.Constants.ENV_STRIPE_KEY;

import java.util.logging.Logger;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentMethod;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.Subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf2021.paf.finalproject.FinalProjectApplication;
import ibf2021.paf.finalproject.model.StripeCustomer;
import ibf2021.paf.finalproject.model.StripeInvoice;
import ibf2021.paf.finalproject.model.StripePaymentIntent;
import ibf2021.paf.finalproject.model.StripePaymentMethod;
import ibf2021.paf.finalproject.model.StripePrice;
import ibf2021.paf.finalproject.model.StripeProduct;
import ibf2021.paf.finalproject.model.StripeSubscription;
import ibf2021.paf.finalproject.repository.StripeRepo;
import ibf2021.paf.finalproject.repository.TeleRepo;

@Service
public class StripeSubcriptionService {

    @Autowired
    private TeleRepo teleRepo;

    @Autowired
    private StripeRepo stripeRepo;

    private static final Logger logger = Logger.getLogger(FinalProjectApplication.class.getName());
    private String stripeKey = System.getenv(ENV_STRIPE_KEY);

    private String prodId;
    private String priceId;
    private String custId;
    private String subId;
    private String invoiceId;
    private String payMtdId;
    private String payIntId;
    
    StripeProduct stripeProduct = new StripeProduct();
    StripePrice stripePrice = new StripePrice();
    StripeCustomer stripeCust = new StripeCustomer();
    StripeSubscription stripeSub = new StripeSubscription();
    StripeInvoice stripeInvoice = new StripeInvoice();
    StripePaymentMethod stripePayMtd = new StripePaymentMethod();
    StripePaymentIntent stripePayInt = new StripePaymentIntent();

    public void createSubscriptionService(String prodName, String prodDesc, String priceInterval,
        int priceUnitAmount, int teleUserId, String teleFirstName, String teleUserName,
            String pmCardNum, int pmCardExpMth, int pmCardExpYear, String pmCardCvc) {

        Stripe.apiKey = stripeKey;

        try {
            Product product = stripeProduct.create(prodName, prodDesc);
            prodId = product.getId();

            Price price = stripePrice.create(priceInterval, prodId, priceUnitAmount);
            priceId = price.getId();

            if (teleRepo.checkUser(teleUserId)) {
                Customer customer = stripeCust.retrieve(String.valueOf(teleUserId));
                custId = customer.getId();
            } else {
                Customer customer = stripeCust.create(String.valueOf(teleUserId), teleFirstName, teleUserName);
                custId = customer.getId();
            }

            logger.info(">>>>>>>>>> CustId: %s".formatted(custId));
            Subscription sub = stripeSub.create(priceId, custId);
            subId = sub.getId();

            invoiceId = stripeInvoice.getIdFromSubscriptionId(subId);

            PaymentMethod payMtd = stripePayMtd.create(pmCardNum, pmCardExpMth, pmCardExpYear, pmCardCvc);
            payMtdId = payMtd.getId();

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

    public void insertIds(int teleUserId) {
        stripeRepo.insertIds(teleUserId, custId, prodId, priceId, subId, invoiceId, payMtdId, payIntId);
    }

    public void insertStatuses(int teleUserId) {
        Stripe.apiKey = stripeKey;
        try {
            String subStatus = stripeSub.getStatus(subId);
            String invoiceStatus = stripeInvoice.getStatus(invoiceId);
            String payIntStatus = stripePayInt.getStatus(payIntId);
            stripeRepo.insertStatuses(teleUserId, subId, subStatus, invoiceStatus, payIntStatus);
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
