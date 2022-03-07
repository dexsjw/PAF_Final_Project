package ibf2021.paf.finalproject.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ibf2021.paf.finalproject.FinalProjectApplication;
import ibf2021.paf.finalproject.model.StripeStatus;
import ibf2021.paf.finalproject.model.TeleUser;
import ibf2021.paf.finalproject.service.BillerBotService;
import ibf2021.paf.finalproject.service.StripeSubcriptionService;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@RestController
@RequestMapping(path="/api", produces=MediaType.APPLICATION_JSON_VALUE)
public class TeleRestController {

    private static final Logger logger = Logger.getLogger(FinalProjectApplication.class.getName());

    @Autowired
    private StripeSubcriptionService stripeSubSvc;

    @Autowired
    private BillerBotService billBotSvc;

    @PostMapping(path="/tele", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postProduct(@RequestBody String payload) {
        JsonObject resp;
        try {
            InputStream is = new ByteArrayInputStream(payload.getBytes());
            JsonReader jr = Json.createReader(is);
            JsonObject productObj = jr.readObject();
            logger.info(">>>>>>>>>> payload: %s".formatted(productObj));
            String prodName = productObj.getString("prodName");
            String prodDesc = productObj.getString("prodDesc");
            int unitAmount = productObj.getInt("unitAmount");
            String interval = productObj.getString("interval");
            String cardNum = productObj.getString("cardNum");
            int cardExpMth = productObj.getInt("cardExpMth");
            int cardExpYear = productObj.getInt("cardExpYear");
            String cardCvc = productObj.getString("cardCvc");            
            
            TeleUser user = billBotSvc.getUser();
            int teleUserId = user.getUserId().intValue();
            String teleFirstName = user.getFirstName();
            String teleUserName = user.getUserName();
            logger.info(">>>>>>>>>> TeleUserId: %s".formatted(user.getUserId().toString()));

            stripeSubSvc.createSubscriptionService(prodName, prodDesc, interval, unitAmount,
                teleUserId, teleFirstName, teleUserName, cardNum, cardExpMth, cardExpYear, cardCvc);
                
            billBotSvc.sendInvoice(unitAmount, prodName, prodDesc);
            
            stripeSubSvc.insertIds(teleUserId);
            stripeSubSvc.insertStatuses(teleUserId);
            billBotSvc.insertTeleUser(user);
            
            resp = Json.createObjectBuilder()
                .add("Result", "You have successfully subscribed to Bill's service!")
                .build();

        } catch (Exception e) {
            e.printStackTrace();
            resp = Json.createObjectBuilder()
                .add("Error msg", "%s".formatted(e.toString()))
                .build();
        }
        return ResponseEntity.ok(resp.toString());
    }

    @GetMapping(path="/tele/values")
    public ResponseEntity<String> getStatuses() {
        int teleUserId = billBotSvc.getCurrentUserId().intValue();
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        List<StripeStatus> statuses = stripeSubSvc.getStatuses(teleUserId);
        statuses.stream()
            .map(status -> status.toJsonObj())
            .forEach(arrBuilder::add);
        return ResponseEntity.ok(arrBuilder.build().toString());
    }

    
}
