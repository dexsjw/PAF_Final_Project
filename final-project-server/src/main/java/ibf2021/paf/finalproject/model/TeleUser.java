package ibf2021.paf.finalproject.model;

import static ibf2021.paf.finalproject.Constants.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class TeleUser {

    private Long userId;
    private String firstName;
    private String userName;

    public static TeleUser create(Long userId, String firstName, String userName) {
        final TeleUser user = new TeleUser();
        user.setUserId(userId);
        user.setFirstName(firstName);
        user.setUserName(userName);
        return user;
    }

    public String respForPostReq(String teleBotToken, String method, String jsonBody) {
        final RequestEntity<String> req = RequestEntity
            .post(URL_TELE_REQ + teleBotToken + method)
            .accept(MediaType.APPLICATION_JSON)
            .body(jsonBody);
        final RestTemplate template = new RestTemplate();
        final ResponseEntity<String> resp = template.exchange(req, String.class);
        if (resp.getStatusCode() != HttpStatus.OK) {
            try { }
            catch (IllegalArgumentException iae) {
                iae.printStackTrace();
                "Error: Status code %s".formatted(resp.getStatusCode());
            }
        }
        final String respBody = resp.getBody();
        return respBody;
    }

    public JsonObject jsonStrToJsonObj(String jsonStr) {
        try {
            InputStream is = new ByteArrayInputStream(jsonStr.getBytes());
            final JsonReader reader = Json.createReader(is);
            final JsonObject respObj = reader.readObject();
            return respObj;
        } catch (Exception e) {
            e.printStackTrace();
            return Json.createObjectBuilder()
                .add("error", "An exception has occurred")
                .build();
        }
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
}
