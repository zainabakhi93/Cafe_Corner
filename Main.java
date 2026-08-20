import Backend.DBConnection;
import Frontend.LoginFrame;

public class Main {

    public static void main(String[] args) {

        try {
            DBConnection.getConnection();
            System.out.println("Connected successfully!");
        } catch (Exception e) {
            System.out.println("Connection failed: " + e.getMessage());
        }

        new LoginFrame();

    }

}