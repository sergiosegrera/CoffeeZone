DROP TABLE cart_menus;
DROP TABLE menu_products;
DROP TABLE menus;
DROP TABLE orders;
DROP TABLE carts;
DROP TABLE customers;
DROP TABLE products;

CREATE TABLE customers (
    customer_id VARCHAR2(20) NOT NULL,
    password RAW(38) NOT NULL,
    salt VARCHAR2(32) NOT NULL,
    address VARCHAR(20),
    phone VARCHAR2(20) NOT NULL,
    email VARCHAR2(20) NOT NULL,
    last_connection TIMESTAMP,
    reffered_by VARCHAR(20),
    
    CONSTRAINT customer_pk PRIMARY KEY (customer_id)
);

CREATE TABLE products (
    product_id NUMBER GENERATED ALWAYS AS IDENTITY,
    name VARCHAR2(20),
    is_drink NUMBER(1),
    stock NUMBER(6),
    price NUMBER(6, 2),
    
    CONSTRAINT product_pk PRIMARY KEY (product_id)
);

CREATE TABLE carts (
    cart_id NUMBER GENERATED ALWAYS AS IDENTITY,
    customer_id VARCHAR2(20),
    total_price NUMBER(6, 2),
    
    CONSTRAINT cart_pk PRIMARY KEY (cart_id),
    CONSTRAINT customer_fk FOREIGN KEY (customer_id) REFERENCES customers (customer_id)
);

CREATE TABLE menus (
    menu_id NUMBER GENERATED ALWAYS AS IDENTITY,
    name VARCHAR2(20),
    total_price NUMBER(6, 2),
    
    CONSTRAINT menu_pk PRIMARY KEY (menu_id)
);

CREATE TABLE orders (
    order_id NUMBER GENERATED ALWAYS AS IDENTITY,
    cart_id NUMBER,
    card VARCHAR(20),
    cvc VARCHAR(20),
    address VARCHAR(20),
    placed DATE,
    sent DATE,
    delivered DATE,
    
    CONSTRAINT cart_fk FOREIGN KEY (cart_id) REFERENCES carts (cart_id)
);

CREATE TABLE cart_menus (
    cart_id NUMBER,
    menu_id NUMBER,
    quantity NUMBER(6),
    
    CONSTRAINT cm_cart_fk FOREIGN KEY (cart_id) REFERENCES carts (cart_id),
    CONSTRAINT cm_menu_fk FOREIGN KEY (menu_id) REFERENCES menus (menu_id)
);

CREATE TABLE menu_products (
    menu_id NUMBER,
    product_id NUMBER,
    sugars NUMBER(3),
    milks NUMBER(3),
    creams NUMBER(3),
    quantity NUMBER(6),
    
    CONSTRAINT menu_id FOREIGN KEY (menu_id) REFERENCES menus (menu_id),
    CONSTRAINT product_id FOREIGN KEY (product_id) REFERENCES products (product_id)
);