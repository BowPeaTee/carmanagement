import java.sql.*;

public class Car  extends BaseFunctions{

    public Car(Connection con) {
        super(con);
    }

    public void displayCars() {
        System.out.println("\nautomobiles:");
        try {
            Statement select = connection.createStatement();
            ResultSet result = select.executeQuery("SELECT * FROM AutoInfo ORDER BY licensePlate");
            displayResultSet(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCar(int id, String carId, int price) {
        String sql = "INSERT INTO automobiles VALUES (?, ?, ?)";
        setAutoCommit(false);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, carId);
            statement.setInt(2, id);
            statement.setInt(3, price);
            statement.execute();
            commit();
            System.out.println("Success");
        } catch (SQLException e) {
            if (connection != null) {
                rollback();
            }
            System.out.println("INSERT ERROR: " + e.getMessage());
        } finally {
            setAutoCommit(true);
        }
    }

    public void updatePrice(String carId, int price) {
        try {
            String sql = "UPDATE automobiles SET \"price\" = ? WHERE \"licensePlate\" = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(2, carId);
            statement.setInt(1, price);
            statement.execute();
            System.out.println("Done");
        } catch (SQLException e) {
            System.out.println("UPDATE ERROR: " + e.getMessage());
        }
    }

    public void remove(String carId) {
        try {
            String sql = "DELETE FROM automobiles WHERE \"licensePlate\" = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, carId);
            statement.execute();
        } catch (SQLException e) {
            System.out.println("REMOVE ERROR: " + e);
        }
    }

    public void showAvailableCars() {
        System.out.println("\nautomobiles:");
        try {
            Statement select = connection.createStatement();
            ResultSet result = select.executeQuery("SELECT * FROM AutoInfo WHERE licensePlate NOT IN (SELECT automobile from orders);");
            displayResultSet(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
