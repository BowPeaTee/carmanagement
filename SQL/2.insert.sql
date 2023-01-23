INSERT INTO clients VALUES('39510102889', 'Frank', 'Tomiyasu');
INSERT INTO clients VALUES('48508097779', 'Ona', 'Ndombele', '9847522352');
INSERT INTO clients VALUES('37508097779', 'Lionel', 'Messi', '9706267722', 'TF Road, Kollam');
INSERT INTO clients VALUES('44404044444', 'Hyeung', 'Min-Son', '9706267700', 'Kolam Road, Kochi');

INSERT INTO models VALUES(default, 'Tata', 'Tiago', '1995');
INSERT INTO models VALUES(default, 'Audi', '100', '1997');
INSERT INTO models VALUES(default, 'Audi', 'A6', '2004');
INSERT INTO models VALUES(default, 'Toyota', 'Supra', '2004');

INSERT INTO automobiles VALUES('KL07BC123', 1, 140000);
INSERT INTO automobiles VALUES('KL14BC124', 1, 150000);
INSERT INTO automobiles VALUES('KL02AC125', 1, 160000);
INSERT INTO automobiles VALUES('TN01AA789', 2, 210000);
INSERT INTO automobiles VALUES('KL11UB007', 3, 220000);
INSERT INTO automobiles VALUES('TN04CG111', 2, 230000);

INSERT INTO orders VALUES(default, '39510102889', 'KL07BC123', '2016-04-23', '2016-05-01');
INSERT INTO orders VALUES(default, '37508097779', 'TN01AA789', '2016-05-13');
INSERT INTO orders VALUES(default, '37508097779', 'KL11UB007', '2016-05-19');
INSERT INTO orders VALUES(default, '39510102889', 'KL14BC124', '2016-05-19');

-- Impossible as date of delivery is greater than today
INSERT INTO orders VALUES(default, '48508097779', 'KL02AC125', '2015-05-23', '2024-05-16');

--EXCEPTION "Cannot purchase car purchased already by another"
INSERT INTO orders VALUES(default, '39510102889', 'KL11UB007', '2016-05-13');
INSERT INTO orders VALUES(default, '39510102889', 'TN04CG111', '2016-05-12');



SELECT * 
FROM clients;

SELECT *
FROM models;

SELECT *
FROM automobiles;

SELECT *
FROM orders order by dateOfPurchase;