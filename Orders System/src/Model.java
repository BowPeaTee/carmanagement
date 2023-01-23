import java.sql.*;

public class Model extends BaseFunctions{

    public Model(Connection con) {
        super(con);
    }

    public void displayModels() {
        System.out.println("\nModels:");
        try {
            Statement select = connection.createStatement();
            ResultSet result = select.executeQuery("SELECT * FROM models");
            displayResultSet(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addModel(String brand, String model, int yer){
        String sql = "INSERT INTO models VALUES (default, ?, ?, ?)";
        setAutoCommit(false);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, brand);
            statement.setString(2, model);
            statement.setInt(3, yer);
            statement.execute();
            commit();
            System.out.println("Success");
        } catch (SQLException e) {
            if (connection != null) {
                rollback();
            }
            System.out.println("ĮDĖJIMO KLAIDA: " + e.getMessage());
        } finally {
            setAutoCommit(true);
        }
    }
}
