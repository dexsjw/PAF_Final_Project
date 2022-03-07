package ibf2021.paf.finalproject.repository;

public class SQL {

    public static final String SQL_TELE_CHECK_USER_ID_FROM_USER = 
        "select count(*) as count from user where tele_user_id = ?";
    public static final String SQL_TELE_INSERT_INTO_USER = 
        "insert into user (tele_user_id, tele_first_name, tele_user_name) values (?, ?, ?)";
    
    public static final String SQL_STRIPE_INSERT_INTO_STATUS = 
        "insert into status (tele_user_id, sub_id, sub_status, invoice_status, pay_int_status) values (?, ?, ?, ?, ?)";
    public static final String SQL_STRIPE_INSERT_INTO_IDS = 
        "insert into ids (tele_user_id, cust_id, prod_id, price_id, sub_id, invoice_id, pay_mtd_id, pay_int_id) values (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String SQL_STRIPE_GET_STATUSES = 
        "select * from status where tele_user_id = ?";
    
}
