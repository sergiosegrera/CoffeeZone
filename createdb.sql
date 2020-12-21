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
    
    CONSTRAINT cart_pk PRIMARY KEY (cart_id)
);

CREATE TABLE menus (
    menu_id NUMBER GENERATED ALWAYS AS IDENTITY,
    name VARCHAR2(20),
    total_price NUMBER(6, 2),
    
    CONSTRAINT menu_pk PRIMARY KEY (menu_id)
);

CREATE TABLE orders (
    order_id NUMBER GENERATED ALWAYS AS IDENTITY,
    customer_id VARCHAR2(20),
    cart_id NUMBER,
    card VARCHAR2(20),
    cvc VARCHAR2(20),
    address VARCHAR2(20),
    placed DATE,
    sent DATE,
    delivered DATE,
    
    CONSTRAINT o_customer_fk FOREIGN KEY (customer_id) REFERENCES customers (customer_id),
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


CREATE OR REPLACE TRIGGER  before_orders_insert
BEFORE INSERT
ON orders
FOR EACH ROW
BEGIN
    SELECT stock INTO prod_stock FROM products
    WHERE product_id = (
    SELECT product_id FROM products
    INNER JOIN menu_products USING(product_id)
    INNER JOIN menus USING(menu_id)
    INNER JOIN cart_menus USING(menu_id)
    INNER JOIN carts USING(cart_id)
    INNER JOIN orders USING(cart_id)
    WHERE cart_id = :NEW.cart_id);
    
    IF(prod_stock = 0) THEN 
        raise_application_error(-20100, 'Product out of stock. This order cannot be placed');
    END IF;
END;

INSERT INTO products VALUES (DEFAULT, 'Filtered Coffee', 1, 32, 1.50);
INSERT INTO products VALUES (DEFAULT, 'Americano', 1, 30, 1.50);
INSERT INTO products VALUES (DEFAULT, 'Espresso', 1, 32, 2.00);
INSERT INTO products VALUES (DEFAULT, 'Cappuccino', 1, 20, 3.00);
INSERT INTO products VALUES (DEFAULT, 'Frappuccino', 1, 20, 3.50);
INSERT INTO products VALUES (DEFAULT, 'Iced Coffee', 1, 35, 2.50);
INSERT INTO products VALUES (DEFAULT, 'French Vanilla', 1, 25, 3.00);
INSERT INTO products VALUES (DEFAULT, 'Iced Tea', 1, 20, 2.50);

INSERT INTO products VALUES (DEFAULT, 'Bagel', 0, 15, 2.25);
INSERT INTO products VALUES (DEFAULT, 'Banana Bread', 0, 12, 1.50);
INSERT INTO products VALUES (DEFAULT, 'Brownie', 0, 20, 2.50);
INSERT INTO products VALUES (DEFAULT, 'Croissant', 0, 30, 2.00);
INSERT INTO products VALUES (DEFAULT, 'Muffin', 0, 25, 2.25);
INSERT INTO products VALUES (DEFAULT, 'Grilled Cheese Sandwich', 0, 15, 3.00);

SELECT product_id, name, is_drink, stock, price FROM products;