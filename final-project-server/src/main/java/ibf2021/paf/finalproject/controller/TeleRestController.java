package ibf2021.paf.finalproject.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ibf2021.paf.finalproject.FinalProjectApplication;
import ibf2021.paf.finalproject.service.BillerBotService;
import ibf2021.paf.finalproject.service.StripeSubcriptionService;
import jakarta.json.Json;

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
        logger.info(payload);
        return ResponseEntity.ok(Json.createObjectBuilder().build().toString());
    }

    
}
