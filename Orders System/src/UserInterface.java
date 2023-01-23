import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.Arrays;

public class UserInterface extends JFrame {

    private Connection connection;
    private Car car;
    private Client client;
    private Model model;
    private Order order;

    private JButton addClientButton, updateClientAddressButton, updateClientPhoneNumberButton,
            findClientByIdButton, removeClientByIdButton, addModelButton, addCarButton,
            updateCarPriceButton, removeCarByIdButton, createOrderButton, returnCarButton,
            displayClientsButton, displayCarsButton, displayOrdersButton;

    public UserInterface(Car car, Client client, Model model, Order order, Connection con) {
        this.car = car;
        this.model = model;
        this.client = client;
        this.order = order;
        this.connection = con;
    }

    public void start() {

        JFrame mainFrame = new JFrame("Car Management System");
        mainFrame.setSize(400, 300);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setSize(400, 300);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel roleLabel = new JLabel("Role:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(roleLabel, gbc);

        JComboBox<String> roleCombo = new JComboBox<>();
        roleCombo.addItem("Client");
        roleCombo.addItem("Admin");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(roleCombo, gbc);

        JButton continueButton = new JButton("Continue");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(continueButton, gbc);

        JButton SignupButton = new JButton("Sign Up");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(SignupButton, gbc);

        roleCombo.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Object item = e.getItem();
                    if (item.equals("Admin")) {
                        SignupButton.setVisible(false);
                    } else {
                        SignupButton.setVisible(true);
                    }
                }
            }
        });

        SignupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addClient();
            }
        });

        mainFrame.add(mainPanel, BorderLayout.CENTER);

        mainFrame.setVisible(true);

        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String role = (String) roleCombo.getSelectedItem();
                if (role.equals("Client")) {
                    createClientLogin();
                } else {
                    createAdminLogin();
                }
                mainFrame.setVisible(false);
            }
        });
    }

    public void createClientLogin() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(400, 300);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLayout(new BorderLayout());

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setSize(400, 300);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        loginPanel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        usernameField.setColumns(15);
        loginPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        loginPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        passwordField.setColumns(15);
        loginPanel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        loginPanel.add(loginButton, gbc);

        loginFrame.add(loginPanel, BorderLayout.CENTER);
        loginFrame.setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String enteredUsername = usernameField.getText();
                char[] enteredPassword = passwordField.getPassword();

                if (checkCredentials(enteredUsername, enteredPassword)) {
                    JOptionPane.showMessageDialog(null, "Welcome to Car Management System");
                    loginFrame.setVisible(false);
                    createClientButtonWindow();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password, please try again");
                }
            }
        });
    }

    public void createAdminLogin() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(400, 300);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLayout(new BorderLayout());

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setSize(400, 300);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        loginPanel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        usernameField.setColumns(15);
        loginPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        loginPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        passwordField.setColumns(15);
        loginPanel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        loginPanel.add(loginButton, gbc);

        loginFrame.add(loginPanel, BorderLayout.CENTER);

        loginFrame.setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String enteredUsername = usernameField.getText();
                char[] enteredPassword = passwordField.getPassword();

                if (checkCredentials(enteredUsername, enteredPassword)) {
                    JOptionPane.showMessageDialog(null, "Welcome to Car Management System");
                    loginFrame.setVisible(false);
                    createButtonWindow();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password, please try again");
                }
            }
        });
    }

    public void createButtonWindow() {
        addClientButton = new JButton("Add Client");
        updateClientAddressButton = new JButton("Update Client Address");
        updateClientPhoneNumberButton = new JButton("Update Client Phone Number");
        findClientByIdButton = new JButton("Find Client by ID");
        removeClientByIdButton = new JButton("Remove Client by ID");
        addModelButton = new JButton("Add Model");
        addCarButton = new JButton("Add Car");
        updateCarPriceButton = new JButton("Update Car Price");
        removeCarByIdButton = new JButton("Remove Car by ID");
        createOrderButton = new JButton("Create Order");
        returnCarButton = new JButton("Return Car");
        displayClientsButton = new JButton("Display Clients");
        displayCarsButton = new JButton("Display Cars");
        displayOrdersButton = new JButton("Display Orders");

        JFrame frame = new JFrame();
        frame.setSize(600, 400);
        frame.setTitle("Button Example");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        panel.add(addClientButton);
        panel.add(updateClientAddressButton);
        panel.add(updateClientPhoneNumberButton);
        panel.add(findClientByIdButton);
        panel.add(removeClientByIdButton);
        panel.add(addModelButton);
        panel.add(addCarButton);
        panel.add(updateCarPriceButton);
        panel.add(removeCarByIdButton);
        panel.add(createOrderButton);
        panel.add(returnCarButton);
        panel.add(displayClientsButton);
        panel.add(displayCarsButton);
        panel.add(displayOrdersButton);

        frame.add(panel);
        frame.setVisible(true);

        addClientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addClient();
            }
        });

        updateClientAddressButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateClientAddress();
            }
        });

        updateClientPhoneNumberButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateClientPhoneNumber();
            }
        });

        findClientByIdButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findClientById();
            }
        });

        removeClientByIdButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeClientById();
            }
        });

        addModelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addModel();
            }
        });

        addCarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addCar();
            }
        });

        updateCarPriceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateCarPrice();
            }
        });

        removeCarByIdButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeCarById();
            }
        });

        createOrderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createOrder();
            }
        });

        returnCarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                returnCar();
            }
        });

        displayClientsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                client.displayClients();
            }
        });

        displayCarsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                car.displayCars();
            }
        });

        displayOrdersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                order.displayOrders();
            }
        });
    }

    public void createClientButtonWindow() {
        updateClientAddressButton = new JButton("Update Client Address");
        updateClientPhoneNumberButton = new JButton("Update Client Phone Number");
        removeClientByIdButton = new JButton("Remove Client by ID");
        createOrderButton = new JButton("Create Order");
        displayCarsButton = new JButton("Display Cars");
        displayOrdersButton = new JButton("Display Orders");

        JFrame frame = new JFrame();
        frame.setSize(600, 400);
        frame.setTitle("Client Window");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        panel.add(updateClientAddressButton);
        panel.add(updateClientPhoneNumberButton);
        panel.add(removeClientByIdButton);
        panel.add(createOrderButton);
        panel.add(displayCarsButton);
        panel.add(displayOrdersButton);

        frame.add(panel);
        frame.setVisible(true);

        updateClientAddressButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateClientAddress();
            }
        });

        updateClientPhoneNumberButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateClientPhoneNumber();
            }
        });

        removeClientByIdButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeClientById();
            }
        });

        createOrderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createOrder();
            }
        });

        displayCarsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                car.displayCars();
            }
        });

        displayOrdersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                order.displayOrders();
            }
        });
    }

    private boolean checkCredentials(String enteredUsername, char[] enteredPassword) {
        return client.checkClientPassword(enteredUsername, enteredPassword);
    }

    private void createOrder() {
        car.showAvailableCars();
        String carId = JOptionPane.showInputDialog("Choose a car (Enter Registration Number)");
        client.displayClients();
        String id = JOptionPane.showInputDialog("Enter the ID of the person renting the car");
        order.createOrder(carId, Long.parseLong(id));
    }

    private void returnCar() {
        order.displayOrders();
        String carId = JOptionPane.showInputDialog("Enter Registration Number of the Car you want to return:");
        order.returnCar(carId);
    }

    private void removeCarById() {
        car.displayCars();
        String carId = JOptionPane.showInputDialog("Registration number of the car you want to remove:");
        car.remove(carId);
    }

    private void updateCarPrice() {
        car.displayCars();
        String carId = JOptionPane.showInputDialog("Registration number of the car you want to change price of:");
        String price = JOptionPane.showInputDialog("Enter new price:");
        car.updatePrice(carId, Integer.parseInt(price));
    }

    private void addCar() {
        model.displayModels();
        String modelId = JOptionPane.showInputDialog("Enter the Model ID:");
        String carId = JOptionPane.showInputDialog("Enter the license plate number");
        String price = JOptionPane.showInputDialog("Enter the Price:");
        car.addCar(Integer.parseInt(modelId), carId, Integer.parseInt(price));
    }

    private void removeClientById() {
        client.displayClients();
        String id = JOptionPane.showInputDialog("Enter the ID of the client you want to remove:");
        client.removeClient(Long.parseLong(id));
    }

    private void findClientById() {
        String id = JOptionPane.showInputDialog("Enter the ID of the client you want to find:");
        client.findClientById(Long.parseLong(id));
    }

    private void updateClientPhoneNumber() {
        client.displayClients();
        String id = JOptionPane.showInputDialog("Enter the ID of the client you want to update:");
        String phoneNumber = JOptionPane.showInputDialog("Enter the new phone number:");
        client.updatePhoneNumber(Long.parseLong(id), phoneNumber);
    }

    private void updateClientAddress() {
        client.displayClients();
        String id = JOptionPane.showInputDialog("Enter the ID of the client you want to update:");
        String address = JOptionPane.showInputDialog("Enter the new address:");
        client.updateAddress(Long.parseLong(id), address);
    }

    private void addModel() {
        String brand = JOptionPane.showInputDialog("Enter manufacturer:");
        String models = JOptionPane.showInputDialog("Enter Model:");
        int yer = Integer.parseInt(JOptionPane.showInputDialog("Enter the year:"));
        model.addModel(brand, models, yer);
    }

    private void addClient() {
        long id = Long.parseLong(JOptionPane.showInputDialog("Enter cid:"));
        String name = JOptionPane.showInputDialog("Enter a name:");
        String surname = JOptionPane.showInputDialog("Enter last name:");
        String phone = JOptionPane.showInputDialog("Enter phone number:");
        String address = JOptionPane.showInputDialog("Enter the address:");
        client.addClient(id, name, surname, phone, address);
    }
}