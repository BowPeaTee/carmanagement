import java.sql.*;

public class Order extends BaseFunctions{

    public Order(Connection con) {
        super(con);
    }

    public void displayOrders() {
        System.out.println("\nOrders:");
        try {
            Statement select = connection.createStatement();
            ResultSet result = select.executeQuery("SELECT * FROM ClientOrders ORDER BY dateOfPurchase");
            displayResultSet(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayMyOrders(String cid) {
        System.out.println("\nOrders:");
        try {
            Statement select = connection.createStatement();
            ResultSet result = select.executeQuery("SELECT * FROM ClientOrders where cid like '"+cid+"' ORDER BY dateOfPurchase;");
            displayResultSet(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnCar(String carId) {
        try {
            String sql = "UPDATE orders SET dateOfDelivery = CURRENT_DATE WHERE automobile = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            //statement.setDate(1, Date.valueOf(LocalDate.now()));
            statement.setString(1, carId);
            statement.execute();
            System.out.println("Done");
        } catch (SQLException e) {
            System.out.println("UPDATE ERROR: " + e.getMessage());
        }
    }

    public void createOrder(String carId, long id) {
        //INSERT INTO orders VALUES(default, '37508097779', 'HUB007', '2016-05-19');
        String sql = "INSERT INTO orders VALUES (default , ?, ?, CURRENT_DATE )";
        setAutoCommit(false);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, String.valueOf(id));
            statement.setString(2, carId);
            //statement.setDate(3, Date.valueOf(LocalDate.now()));
            statement.execute();
            commit();
            System.out.println("Success");
        } catch (SQLException e) {
            if (connection != null) {
                rollback();
            }
            System.out.println("ERROR: " + e.getMessage());
        } finally {
            setAutoCommit(true);
        }
    }
}
