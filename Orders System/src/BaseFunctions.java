import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class BaseFunctions {
    protected Connection connection;

    public BaseFunctions(Connection con) {
        this.connection = con;
    }

    protected void displayResultSet(ResultSet rs) throws SQLException {
        if (rs != null) {
            ResultSetMetaData md = rs.getMetaData();
            int ncols = md.getColumnCount();
            DefaultTableModel model = new DefaultTableModel();

            // Create column names
            for (int i = 1; i <= ncols; i++) {
                model.addColumn(md.getColumnName(i));
            }

            // Add rows to table
            while (rs.next()) {
                Object[] row = new Object[ncols];
                for (int i = 1; i <= ncols; i++) {
                    row[i-1] = rs.getObject(i);
                }
                model.addRow(row);
            }

            // Create table
            JTable table = new JTable(model);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            table.setRowHeight(25);
            table.setIntercellSpacing(new Dimension(10, 10));
            table.setShowGrid(false);

            // set table header font and background color
            JTableHeader header = table.getTableHeader();
            header.setBackground(new Color(0, 150, 136));
            header.setForeground(Color.WHITE);
            header.setFont(new Font("Arial", Font.BOLD, 16));

            // set table font and background color
            table.setBackground(Color.WHITE);
            table.setForeground(new Color(0, 150, 136));
            table.setFont(new Font("Arial", Font.PLAIN, 16));

            // Create scroll pane
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());

            // Create frame
            JFrame frame = new JFrame("Table");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(scrollPane);
            frame.setSize(1100, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } else {
            throw new SQLException("There is no such data!");
        }
    }


    public void setAutoCommit(boolean value) {
        try {
            connection.setAutoCommit(value);
        } catch (SQLException e) {
            System.out.println("TRANSACTION ERROR: " + e);
        }
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            System.out.println("TRANSACTION ERROR: " + e);
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println("TRANSACTION ERROR: " + e);
        }
    }
}