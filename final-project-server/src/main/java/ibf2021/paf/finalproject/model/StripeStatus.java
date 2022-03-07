package ibf2021.paf.finalproject.model;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class StripeStatus {
    
    private int teleUserId;
    private String subId;
    private String subStatus;
    private String invoiceStatus;
    private String payIntStatus;

    public static StripeStatus create(SqlRowSet rs) {
        final StripeStatus ss = new StripeStatus();
        ss.setTeleUserId(rs.getInt("tele_user_id"));
        ss.setSubId(rs.getString("sub_id"));
        ss.setSubStatus(rs.getString("sub_status"));
        ss.setInvoiceStatus(rs.getString("invoice_status"));
        ss.setPayIntStatus(rs.getString("pay_int_status"));
        return ss;
    }

    public JsonObject toJsonObj() {
        return Json.createObjectBuilder()
            .add("teleUserId", getTeleUserId())
            .add("subId", getSubId())
            .add("subStatus", getSubStatus())
            .add("invoiceStatus", getInvoiceStatus())
            .add("payIntStatus", getPayIntStatus())
            .build();
    }

    public int getTeleUserId() { return teleUserId; }
    public void setTeleUserId(int teleUserId) { this.teleUserId = teleUserId; }

    public String getSubId() { return subId; }
    public void setSubId(String subId) { this.subId = subId; }

    public String getSubStatus() { return subStatus; }
    public void setSubStatus(String subStatus) { this.subStatus = subStatus; }

    public String getInvoiceStatus() { return invoiceStatus; }
    public void setInvoiceStatus(String invoiceStatus) { this.invoiceStatus = invoiceStatus; }

    public String getPayIntStatus() { return payIntStatus; }
    public void setPayIntStatus(String payIntStatus) { this.payIntStatus = payIntStatus; }

}
