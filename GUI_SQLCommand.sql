-- drop table command
DROP TABLE ratereview;
DROP TABLE discount;
DROP TABLE cartlist;
DROP TABLE cart;
DROP TABLE orderlist;
DROP TABLE product;
DROP TABLE imagetable;
DROP TABLE orders;
DROP TABLE member_address;
DROP TABLE member;
DROP TABLE addressbook;
DROP TABLE staff;

CREATE TABLE staff(
	staff_id integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1000, INCREMENT BY 1),
	staff_name varchar(50),
	staff_pass varchar(20),
	staff_ic varchar(12),
	staff_phone varchar(10),
	staff_email varchar(50),
	staff_birthdate date,
	PRIMARY KEY(staff_id)
);

CREATE TABLE addressbook(
	address_id integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1000, INCREMENT BY 1),
	address_name varchar(255),
	address_phone varchar(10),
	address_no varchar(255),
	address_street varchar(255),
	address_state varchar(255),
	address_city varchar(255),
	address_postcode varchar(255),
	PRIMARY KEY(address_id)
);

CREATE TABLE member(
	member_id integer not null GENERATED ALWAYS AS IDENTITY (START WITH 2000, INCREMENT BY 1),
	member_name varchar(255),
	member_pass varchar(20),
	PRIMARY KEY(member_id)
);

CREATE TABLE member_address(
	address_id integer not null,
	member_id integer not null,
	PRIMARY KEY(address_id, member_id),
	FOREIGN KEY(address_id) REFERENCES addressbook(address_id),
	FOREIGN KEY(member_id) REFERENCES member(member_id)
);


CREATE TABLE orders(
	orders_id integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1000, INCREMENT BY 1),
	orders_date date,
	orders_payment_type varchar(50), -- CASH PAYMENT OR CARD
	orders_ttlprice double,
	orders_tax double,
	orders_delivery_fee double,
	orders_express_shipping double,
	member_id integer,
	address_id integer,
	PRIMARY KEY(orders_id),
	FOREIGN KEY(member_id) REFERENCES member(member_id),
	FOREIGN KEY(address_id) REFERENCES addressbook(address_id)
);

CREATE TABLE imagetable(
	image_id integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1000, INCREMENT BY 1),
	image_name varchar(255),
	image_contenttype varchar(255),
	image blob,
	trans_id varchar(255),
	PRIMARY KEY(image_id)
);

CREATE TABLE product(
	product_id integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1000, INCREMENT BY 1),
	product_name varchar(255),
	product_desc varchar(1000),
	product_price double,
	product_active char(1),
	image_id integer,
	PRIMARY KEY(product_id),
	FOREIGN KEY(image_id) REFERENCES imagetable(image_id)
);

CREATE TABLE orderlist(
	orders_id integer not null,
	product_id integer not null,
	orders_quantity integer,
	orders_subprice double,
	PRIMARY KEY(orders_id, product_id),
	FOREIGN KEY(orders_id) REFERENCES orders(orders_id),
	FOREIGN KEY(product_id) REFERENCES product(product_id)
);

CREATE TABLE cart(
	cart_id integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	member_id integer,
	PRIMARY KEY(cart_id),
	FOREIGN KEY(member_id) REFERENCES member(member_id)
);

CREATE TABLE cartlist(
	cart_id integer not null,
	product_id integer not null,
	cart_quantity integer,
	PRIMARY KEY(cart_id, product_id),
	FOREIGN KEY(cart_id) REFERENCES cart(cart_id),
	FOREIGN KEY(product_id) REFERENCES product(product_id)
);

CREATE TABLE discount(
	discount_id integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1000, INCREMENT BY 1),
	discount_percentage integer,
	discount_start_date date,
	discount_end_date date,
	product_id integer,
	PRIMARY KEY(discount_id),
	FOREIGN KEY(product_id) REFERENCES product(product_id)
);

CREATE TABLE ratereview(
	review_id integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1000, INCREMENT BY 1),
	review_text varchar(255),
	review_rating integer, -- maybe somthing 1 to 5 rating number
	review_date date,
	product_id integer,
	member_id integer,
	PRIMARY KEY(review_id),
	FOREIGN KEY(product_id) REFERENCES product(product_id),
	FOREIGN KEY(member_id) REFERENCES member(member_id)
);

ALTER TABLE RATEREVIEW
ADD ORDERS_ID int;

ALTER TABLE RATEREVIEW
ADD FOREIGN KEY (ORDERS_ID) REFERENCES ORDERS(ORDERS_ID);

