CREATE FUNCTION noFutureDeliveryConfirmations() RETURNS "trigger" AS
$$
	BEGIN
		IF NEW.dateOfDelivery > CURRENT_DATE
			THEN RAISE EXCEPTION 'Cannot confirm future delivery date!';
		END IF;
		RETURN NEW;
	END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER futureDelivery
BEFORE INSERT ON orders
FOR EACH ROW EXECUTE PROCEDURE noFutureDeliveryConfirmations();

--Disallow purchase of unavailable(out of stock) car
CREATE FUNCTION noPurchaseSoldStock() RETURNS "trigger" AS
$$
	BEGIN
		IF (SELECT COUNT(*) FROM orders
			WHERE (automobile = NEW.automobile)) > 0
		THEN
			RAISE EXCEPTION 'Car already purchased by another user! Kindly purchase from new stock.';
		END IF;
		RETURN NEW;
	END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER soldStock
BEFORE INSERT ON orders
FOR EACH ROW EXECUTE PROCEDURE noPurchaseSoldStock();
