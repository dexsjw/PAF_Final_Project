package ibf2021.paf.finalproject.repository;

import static ibf2021.paf.finalproject.repository.SQL.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import ibf2021.paf.finalproject.model.StripeStatus;

@Repository
public class StripeRepo {

    @Autowired
    private JdbcTemplate template;

    public int insertStatuses(int teleUserId, String subId, String subStatus, String invoiceStatus, String payIntStatus) {
        final int row = template.update(SQL_STRIPE_INSERT_INTO_STATUS, teleUserId, subId, subStatus, invoiceStatus, payIntStatus);
        return row;
    }

    public int insertIds(int teleUserId, String custId, String prodId,
        String priceId, String subId, String invoiceId, String payMtdId, String payIntId) {
        
            final int row = template.update(SQL_STRIPE_INSERT_INTO_IDS,
                teleUserId, custId, prodId, priceId, subId, invoiceId, payMtdId, payIntId);
            return row;
    }

    public List<StripeStatus> getStatuses(int teleUserId) {
        final List<StripeStatus> statuses = new ArrayList<>();
        final SqlRowSet rs = template.queryForRowSet(SQL_STRIPE_GET_STATUSES, teleUserId);
        while (rs.next()) {
            statuses.add(StripeStatus.create(rs));
        }
        return statuses;
    }
    
}
