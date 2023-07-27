


CREATE TABLE categories (
   categories_id int NOT NULL AUTO_INCREMENT ,
   categorise_name varchar(64) ,
   PRIMARY KEY (categories_id) 
 );
 
 
 CREATE TABLE city (
   city_id int NOT NULL AUTO_INCREMENT,
   city_name varchar(32) DEFAULT NULL,
   streetName varchar(32) DEFAULT NULL,
   buildingNumber int DEFAULT NULL,
   PRIMARY KEY (city_id)
 );
 
 CREATE TABLE customer (
   customer_id int NOT NULL AUTO_INCREMENT,
   city_id int not null ,
   customer_name varchar(32) DEFAULT NULL,
   customer_email varchar(50) DEFAULT NULL,
   customer_phone varchar(10) DEFAULT NULL,
   customer_city varchar(32) NOT NULL,
   customer_streetName varchar(64) NOT NULL,
   customer_buildingNumber int NOT NULL,
   customer_password varchar(32) DEFAULT NULL,
   PRIMARY KEY (customer_id),
   foreign key (city_id) references city(city_id)
 );
  
 CREATE TABLE employee (
   employee_id int NOT NULL AUTO_INCREMENT,
   employee_name varchar(32) DEFAULT NULL,
   employee_email varchar(50) DEFAULT NULL,
   employee_password varchar(50) DEFAULT NULL,
   employee_phone varchar(10) DEFAULT NULL,
   PRIMARY KEY (employee_id)
 );
 
 
 CREATE TABLE orders (
   order_id int NOT NULL AUTO_INCREMENT,
   order_date date NOT NULL,
   order_price float NOT NULL,
   payment_method_id int NOT NULL,
   customer_id int NOT NULL,
   PRIMARY KEY (order_id),
   KEY payment_method_id (payment_method_id),
   KEY customer_id (customer_id),
   foreign key (payment_method_id) references payment_method(payment_method_id),
   foreign key (customer_id) references customer(customer_id)
 );
 
 CREATE TABLE payment_method (
   payment_method_id int NOT NULL,
   payment_method_name varchar(32) DEFAULT NULL,
   PRIMARY KEY (payment_method_id)
 );
 
 CREATE TABLE product (
   product_id int NOT NULL AUTO_INCREMENT,
   product_name varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
   product_purchasing_price double DEFAULT NULL,
   product_selling_price double DEFAULT NULL,
   product_quantity int DEFAULT NULL,
   product_description varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
   categories_id int DEFAULT NULL,
   img_URL varchar(360) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
   PRIMARY KEY (product_id),
   KEY categories_id (categories_id) ,
    FOREIGN KEY (categories_id) REFERENCES categories (categories_id) 
 );
 
 CREATE TABLE product2order (
   product_id int NOT NULL,
   quantity int NOT NULL,
   order_id int NOT NULL,
   PRIMARY KEY (product_id,order_id),
   KEY product2order2_ibfk_1 (order_id),
   CONSTRAINT foreignProduct FOREIGN KEY (product_id) REFERENCES product (product_id),
    FOREIGN KEY (order_id) REFERENCES orders (order_id) 
 );
 
 
 
 
 
 
 
 
 