package ibf2021.paf.finalproject.model;

import static ibf2021.paf.finalproject.Constants.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Product;

import ibf2021.paf.finalproject.FinalProjectApplication;

public class StripeProduct {

    private static final Logger logger = Logger.getLogger(FinalProjectApplication.class.getName());
    private String stripeKey = System.getenv(ENV_STRIPE_KEY);

    private String id;
    private String name;
    private String description;

    public Product create(String name, String description) throws StripeException {
        Stripe.apiKey = stripeKey;
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("description", description);
        Product product = Product.create(params);
        id = product.getId();
        return product;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
