package top.liyf.mywebstore.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;


public class C3P0Util {
    public static ComboPooledDataSource ds ;

    static{
        ds = new ComboPooledDataSource("c3p0");
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = ds.getConnection();
        return connection;
    }
}
