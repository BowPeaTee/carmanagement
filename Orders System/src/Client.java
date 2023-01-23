import java.sql.*;
import java.util.Arrays;

public class Client extends BaseFunctions{

    public Client(Connection con) {
        super(con);
    }


    protected void displayClients() {
        System.out.println("\nclients:");
        try {
            Statement select = connection.createStatement();
            ResultSet result = select.executeQuery("SELECT * FROM clients");
            displayResultSet(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected boolean checkClientPassword(String enteredUsername, char[] enteredPassword) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean success = false;
        try {
            stmt = connection.prepareStatement("SELECT phoneNo FROM clients WHERE cid = ?");
            stmt.setString(1, enteredUsername);
            rs = stmt.executeQuery();
            if (rs.next()) {
                String correctPassword = rs.getString("phoneNo");
                if (Arrays.equals(enteredPassword, correctPassword.toCharArray())) {
                    success = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
    public void addClient(long id, String name, String surname, String phone, String address) {
        String sql = "INSERT INTO clients VALUES (?, ?, ?, ?, ?)";
        setAutoCommit(false);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, String.valueOf(id));
            statement.setString(2, name);
            statement.setString(3, surname);
            statement.setString(4, phone);
            statement.setString(5, address);
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

    public void updateAddress(long id, String address) {
        try {
            String sql = "UPDATE clients SET \"Address\" = ? WHERE cid = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, address);
            statement.setString(2, String.valueOf(id));
            statement.execute();
            System.out.println("Done");
        } catch (SQLException e) {
            System.out.println("UPDATE ERROR: " + e);

        }
    }

    public void updatePhoneNumber(long id, String phone) {
        try {
            String sql = "UPDATE clients SET \"phoneNo\" = ? WHERE \"cid\" = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, phone);
            statement.setString(2, String.valueOf(id));
            statement.execute();
            System.out.println("Done");
        } catch (SQLException e) {
            System.out.println("UPDATE ERROR: " + e.getMessage());
        }
    }

    public void findClientById(long id) {
        try {
            String sql = "SELECT * FROM clients WHERE \"cid\" = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, String.valueOf(id));
            ResultSet result = statement.executeQuery();
            displayResultSet(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeClient(long id) {
        setAutoCommit(false);
        try {
            String sql = "DELETE FROM clients WHERE \"cid\" = ?";
            String sql2 = "DELETE FROM orders WHERE \"client\" = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement2 = connection.prepareStatement(sql2);
            statement.setString(1, String.valueOf(id));
            statement2.setString(1, String.valueOf(id));
            statement2.execute();
            statement.execute();
            System.out.println("Done");
        } catch (SQLException e) {
            if (connection != null) {
                rollback();
            }
            System.out.println("REMOVE KLAIDA: " + e.getMessage());
        } finally {
            setAutoCommit(true);
        }
    }
}
