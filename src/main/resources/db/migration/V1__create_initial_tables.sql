CREATE TABLE user
(
    id              VARCHAR(52) NOT NULL,
    name            VARCHAR(50) NOT NULL,
    last_name       VARCHAR(50) NOT NULL,
    document_number VARCHAR(10) NOT NULL,
    document_type   INT         NOT NULL COMMENT '0 = UNKNOWN,1 = DNI,2 = CE,3 = RUC',
    created_date    DATETIME    NULL,
    last_modified   DATETIME    NULL,
    phone_number    VARCHAR(10) NULL,
    CONSTRAINT user_pk
        PRIMARY KEY (id)
) COMMENT 'User Data';

CREATE TABLE `order`
(
    id           VARCHAR(52)        NOT NULL,
    user_id      VARCHAR(52)        NOT NULL,
    total        DOUBLE DEFAULT 0.0 NULL,
    sub_total    DOUBLE DEFAULT 0.0 NULL,
    taxes        DOUBLE DEFAULT 0.0 NULL,
    discounts    DOUBLE DEFAULT 0.0 NULL,
    created_date DATETIME           NULL,
    CONSTRAINT order_pk
        PRIMARY KEY (id)
);

CREATE TABLE order_products
(
    id           VARCHAR(52)  NOT NULL,
    order_id     VARCHAR(52)  NOT NULL,
    product_sku  VARCHAR(100) NOT NULL,
    product_name VARCHAR(200) NOT NULL,
    quantity     DOUBLE       NULL,
    price        DOUBLE       NULL,
    discount     DOUBLE       NULL,
    CONSTRAINT order_products_pk
        PRIMARY KEY (id)
) COMMENT 'Linking product entity with an order';
