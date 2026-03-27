-- CUSTOMER
INSERT INTO customer (name) VALUES ('Srilatha');

INSERT INTO transactions (customer_id, amount, transaction_date)
VALUES (1, 30, CURRENT_DATE - 5);

INSERT INTO transactions (customer_id, amount, transaction_date)
VALUES (1, 60, CURRENT_DATE - 10);

INSERT INTO transactions (customer_id, amount, transaction_date)
VALUES (1, 80, CURRENT_DATE - 20);

INSERT INTO transactions (customer_id, amount, transaction_date)
VALUES (1, 120, CURRENT_DATE - 30);

-- ✅ High value → (180-100)*2 + 50 = 210 points
INSERT INTO transactions (customer_id, amount, transaction_date)
VALUES (1, 180, CURRENT_DATE - 40);

INSERT INTO transactions (customer_id, amount, transaction_date)
VALUES (1, 250, CURRENT_DATE - 50);

INSERT INTO transactions (customer_id, amount, transaction_date)
VALUES (1, 50, CURRENT_DATE - 15);

INSERT INTO transactions (customer_id, amount, transaction_date)
VALUES (1, 100, CURRENT_DATE - 25);

INSERT INTO transactions (customer_id, amount, transaction_date)
VALUES (1, -55, CURRENT_DATE - 12);

INSERT INTO transactions (customer_id, amount, transaction_date)
VALUES (1, 200, CURRENT_DATE - 120);