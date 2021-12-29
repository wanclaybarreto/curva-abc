package br.com.asnsoftware.relfatprodabc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionJDBC {
    private static ConnectionJDBC connectionJDBC;

    private ConnectionJDBC() {}

    public static ConnectionJDBC getInstance() {
            if(connectionJDBC == null) {
               connectionJDBC = new ConnectionJDBC();
            }
            return connectionJDBC;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/asn?useTimezone=true&serverTimezone=UTC",
                                               "user",
                                               "password");
    }
}
