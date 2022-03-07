package ibf2021.paf.finalproject.model;

import static ibf2021.paf.finalproject.Constants.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Invoice;
import com.stripe.model.PaymentIntent;

import ibf2021.paf.finalproject.FinalProjectApplication;

public class StripePaymentIntent {

    private String id;
    private String paymentMethodId;

    private static final Logger logger = Logger.getLogger(FinalProjectApplication.class.getName());
    private String stripeKey = System.getenv(ENV_STRIPE_KEY);

    public String getIdFromInvoiceId(String invoiceId) throws StripeException {
        Stripe.apiKey = stripeKey;
        Invoice invoice = Invoice.retrieve(invoiceId);
        id = invoice.getPaymentIntent();
        return id;
    }

    public PaymentIntent update(String id, String paymentMethodId) throws StripeException {
        Stripe.apiKey = stripeKey;
        Map<String, Object> params = new HashMap<>();
        params.put("payment_method", paymentMethodId);
        PaymentIntent payInt = PaymentIntent.retrieve(id);
        PaymentIntent updatedPayInt = payInt.update(params);
        return updatedPayInt;
    }

    public PaymentIntent confirm(String id, String paymentMethodId) throws StripeException {
        Stripe.apiKey = stripeKey;
        Map<String, Object> params = new HashMap<>();
        params.put("payment_method", paymentMethodId);
        PaymentIntent payInt = PaymentIntent.retrieve(id);
        PaymentIntent updatedPayInt = payInt.confirm(params);
        return updatedPayInt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPaymentMethodId() { return paymentMethodId; }
    public void setPaymentMethodId(String paymentMethodId) { this.paymentMethodId = paymentMethodId; }

}
