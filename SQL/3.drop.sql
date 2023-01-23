/* DROP VIEWS */

DROP VIEW AutoInfo;
DROP VIEW ClientOrders;

/* DROP INDEX */

DROP INDEX client_index;
DROP INDEX phone;

/* DROP TRIGGERS */

DROP TRIGGER futureDelivery ON orders;
DROP FUNCTION noFutureDeliveryConfirmations();

DROP TRIGGER soldStock ON orders;
DROP FUNCTION noPurchaseSoldStock();


/* Drop Tables */

DROP TABLE clients CASCADE;
DROP TABLE models CASCADE;
DROP TABLE automobiles CASCADE;
DROP TABLE orders;