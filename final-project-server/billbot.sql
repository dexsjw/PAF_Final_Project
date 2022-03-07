drop database if exists billbot;

create database billbot;

use billbot;

create table user (
    tele_user_id int not null,
    tele_first_name varchar(64) not null,
    tele_user_name varchar(64) not null,
    primary key(tele_user_id)
);

create table ids (
    table_id int auto_increment not null,
    tele_user_id int,
    cust_id varchar(128) not null,
    prod_id varchar(128) not null,
    price_id varchar(128) not null,
    sub_id varchar(128) not null,
    invoice_id varchar(128) not null,
    pay_mtd_id varchar(128) not null,
    pay_int_id varchar(128) not null,

    primary key(table_id),
    constraint fk_tele_user_id
        foreign key(tele_user_id)
        references user(tele_user_id)
);

create table status (
    status_id int auto_increment not null,
    tele_user_id int,
    sub_id varchar(128),
    sub_status varchar(64) not null,
    invoice_status varchar(64) not null,
    pay_int_status varchar(64) not null,
    
    primary key(status_id),
    constraint fk_tele_user_id_status
        foreign key(tele_user_id)
        references user(tele_user_id)
);