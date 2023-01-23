import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class BaseFunctions {
    protected Connection connection;

    public BaseFunctions(Connection con) {
        this.connection = con;
    }

    protected void displayResultSet (ResultSet rs) throws SQLException {
        if (rs != null) {
            ResultSetMetaData md = rs.getMetaData ( );
            int ncols = md.getColumnCount ( );
            int nrows = 0;
            int[ ] width = new int[ncols + 1];       // array to store widths in a column
            StringBuilder b = new StringBuilder ( ); // buffer protecting bar line

            // column widths are calculated
            for (int i = 1; i <= ncols; i++)
            {
                width[i] = md.getColumnDisplaySize (i);
                if (width[i] < md.getColumnName (i).length ( ))
                    width[i] = md.getColumnName (i).length ( );
                // isNullable( ) returns 1/0 instead of true/false
                if (width[i] < 4 && md.isNullable (i) != 0)
                    width[i] = 4;
            }

            // we construct a +---+--- line
            b.append ("+");
            for (int i = 1; i <= ncols; i++)
            {
                for (int j = 0; j < width[i]; j++)
                    b.append ("-");
                b.append ("+");
            }

            // for printing bar line, column header, bar line
            System.out.println (b.toString ( ));
            System.out.print ("|");
            for (int i = 1; i <= ncols; i++)
            {
                System.out.print (md.getColumnName (i));
                for (int j = md.getColumnName (i).length ( ); j < width[i]; j++)
                    System.out.print (" ");
                System.out.print ("|");
            }
            System.out.println ( );
            System.out.println (b.toString ( ));

            // result set's contents for printing

            while (rs.next())
            {
                ++nrows;
                System.out.print ("|");
                for (int i = 1; i <= ncols; i++)
                {
                    String s = rs.getString(i);
                    if (rs.wasNull())
                        s = "None";
                    System.out.print (s);
                    for (int j = s.length(); j < width[i]; j++)
                        System.out.print(" ");
                    System.out.print("|");
                }
                System.out.println();
            }

            // we print the bar line and the number of lines
            System.out.println (b.toString ( ));
            System.out.println (nrows + " irasai");
        } else {
            throw new SQLException("There is no such data!");
        }
    }


    public void setAutoCommit(boolean value)
    {
        try
        {
            connection.setAutoCommit(value);
        }
        catch (SQLException e)
        {
            System.out.println("TRANSACTION ERROR: " + e);
        }
    }

    public void commit()    {
        try {
            connection.commit();
        }
        catch (SQLException e)        {
            System.out.println("TRANSACTION ERROR: " + e);
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        }
        catch (SQLException e){
            System.out.println("TRANSACTION ERROR: " + e);
        }
    }
}
