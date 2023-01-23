import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Couldn't find driver class!");
            cnfe.printStackTrace();
            System.exit(1);
        }
    }

    public static Connection getConnection() {
        Connection postGresConn = null;
        try {
            postGresConn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/car", "moit", "beans");
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
            sqle.printStackTrace();
            return null;
        }
        System.out.println("Successfully connected to Postgres Database");

        return postGresConn;
    }


    public static void main(String[] args) {
        loadDriver();
        Connection con = getConnection();

        Car cars = new Car(con);
        Client clients = new Client(con);
        Model models = new Model(con);
        Order orders = new Order(con);
        UserInterface ui = new UserInterface(cars, clients, models, orders, con);
        ui.start();
    }
}
