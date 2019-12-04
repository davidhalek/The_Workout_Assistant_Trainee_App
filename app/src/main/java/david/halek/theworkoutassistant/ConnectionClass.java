package david.halek.theworkoutassistant;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionClass {
    String ip = "exercise.ckiipompt8fh.us-east-2.rds.amazonaws.com";
    String db = "WorkoutAssistant";
    String port = "1433";
    String username = "bigmoney";
    String password = "andnowhammies";
    String cl = "net.sourceforge.jtds.jdbc.Driver";

    @SuppressLint("NewApi")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {
            Log.e("ConnectionClass", "Attempting Connection");
            Class.forName(cl);
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                    + "databaseName=" + db + ";user=" + username + ";password=" + password + ";";
            conn = DriverManager.getConnection(ConnURL);
            System.out.println("Connection Successful.");
        } catch (SQLException se) {
            Log.e("-->SQLException-->", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("-->ClassNotFound-->", e.getMessage());
        } catch (Exception e) {
            Log.e("-->Generic-->", e.getMessage());
        }
        return conn;
    }

    public static int validateLogin(String username, String password) {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection con = connectionClass.CONN();
        int userid = -1;

        String query = "select [UserID], [Password] from [User] where [LogonD] = ?";

        try {
            PreparedStatement preparedQuery = con.prepareStatement(query);
            preparedQuery.setString( 1, username);

            ResultSet rs = preparedQuery.executeQuery();

            if (rs.next()) {
                if (rs.getString("Password").equals(password)) {
                    userid = rs.getInt("UserID");
                    Log.e("-----LOGIN-----", "Successful: " + userid);
                } else {
                    Log.e("-----LOGIN-----", "FAILED: " + userid);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userid;
    }
 }
