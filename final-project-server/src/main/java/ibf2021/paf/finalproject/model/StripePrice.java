package ibf2021.paf.finalproject.model;

import static ibf2021.paf.finalproject.Constants.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;

import ibf2021.paf.finalproject.FinalProjectApplication;

public class StripePrice {

    private static final Logger logger = Logger.getLogger(FinalProjectApplication.class.getName());
    private String stripeKey = System.getenv(ENV_STRIPE_KEY);

    private String id;
    private String productId;
    private String currency ="sgd";
    private int unitAmount;
    private Object[] recurring;
    private String interval;

    public Price create(String interval, String productId, int unitAmount) throws StripeException {
        Stripe.apiKey = stripeKey;
        Map<String, Object> recurring = new HashMap<>();
        recurring.put("interval", interval);
        Map<String, Object> params = new HashMap<>();
        params.put("product", productId);
        params.put("currency", currency);
        params.put("unit_amount", unitAmount);
        params.put("recurring", recurring);
        Price price = Price.create(params);
        id = price.getId();
        return price;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
  
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public int getUnitAmount() { return unitAmount; }
    public void setUnitAmount(int unitAmount) { this.unitAmount = unitAmount; }

    public Object[] getRecurring() { return recurring; }
    public void setRecurring(Object[] recurring) { this.recurring = recurring; }

    public String getInterval() { return interval; }
    public void setInterval(String interval) { this.interval = interval; }

}
