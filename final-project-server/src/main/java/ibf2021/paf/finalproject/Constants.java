package ibf2021.paf.finalproject;

public class Constants {

    public static final String URL_TELE_REQ = "https://api.telegram.org/bot";
    public static final String URL_STRIPE_REQ = "https://api.stripe.com";

    public static final String ENV_TELEBOT_TOKEN = "TELEBOT_TOKEN";
    public static final String ENV_TELEBOT_PROVIDER_TOKEN = "TELEBOT_PROVIDER_TOKEN";
    public static final String ENV_STRIPE_KEY = "STRIPE_KEY";

    public static final String TELE_METHOD_GET_MY_COMMANDS = "/getMyCommands";

    public static final String STRIPE_ENDPOINT_CREATE_PRODUCT = "/v1/products";
    public static final String STRIPE_ENDPOINT_CREATE_PRICE = "/v1/prices";
    
}
