drop database if exists storefront;
create database storefront;
use storefront;
select "Create table purchase_order" as "";
create table purchase_order (
    o_id int auto_increment primary key,
    name varchar(64) not null,
    email varchar(64)
);
select "Create table line_item" as "";
create table line_item (
    item_id int auto_increment primary key,
    product_name varchar(64) not null,
    quantity int,
    unit_price float,
    o_id int,
    constraint fk_o_id foreign key(o_id) references purchase_order(o_id)
);
