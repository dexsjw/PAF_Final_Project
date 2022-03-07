package ibf2021.paf.finalproject.repository;

import static ibf2021.paf.finalproject.repository.SQL.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import ibf2021.paf.finalproject.model.TeleUser;

@Repository
public class TeleRepo {

    @Autowired
    private JdbcTemplate template;

    public boolean checkUser(int teleUserId) {
        final SqlRowSet rs = template.queryForRowSet(SQL_TELE_CHECK_USER_ID_FROM_USER, teleUserId);
        if (rs.next()) {
            int count = rs.getInt("count");
            if (count == 0) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public int insertTeleUser(TeleUser user) {
        final int userId = user.getUserId().intValue();
        final String firstName = user.getFirstName();
        final String userName = user.getUserName();
        final int row = template.update(SQL_TELE_INSERT_INTO_USER, userId, firstName, userName);
        return row;
    }

}
