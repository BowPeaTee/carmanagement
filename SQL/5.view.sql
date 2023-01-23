CREATE VIEW AutoInfo AS
SELECT manufacturer, modelName, yer, licensePlate, price
FROM models AS A
	INNER JOIN automobiles AS B
		ON b.model = a.id
GROUP BY b.licensePlate, a.id;

SELECT * FROM AutoInfo;


CREATE VIEW ClientOrders AS
SELECT name, lastName, dateOfPurchase, dateOfDelivery, manufacturer, modelName, yer, licensePlate, price
FROM clients AS A
	INNER JOIN orders AS B
		ON b.client = a.cid
	INNER JOIN AutoInfo as C
		ON b.automobile = c.licensePlate;

SELECT * FROM ClientOrders;
