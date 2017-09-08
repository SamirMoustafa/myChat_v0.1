package MainWindows.Account;

import Core.UserAccount;
import MainWindows.SignIn.SignIn;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Created by Samir Moustafa on 5/1/2017.
 */
public class Account {

    public static Stage window;
    public static double x0 = 1000, y0 = 618;

    protected static UserAccount currentAccount;

    public Account(UserAccount currentAccount) {

        setUserAccount(currentAccount);
        SignIn.window.close();
        window = new Stage();
        //Put header for main windows
        window.setTitle("myChat");
        //Remove toolbar
        window.initStyle(StageStyle.UNDECORATED);
        window.initStyle(StageStyle.TRANSPARENT);
        //Load myFXML file
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MainWindows/Account.fxml"));
            //Make new scene with fxml file
            Scene scene = new Scene(root, x0, y0);
            //Set myCSS file in scene
            scene.getStylesheets().add(getClass().getClassLoader().getResource("bin/myCSS.css").toExternalForm());
            //Load scene in main windows
            window.setScene(scene);
        } catch (IOException e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage() + "\nError may be in Account.fxml or myCSS.css check it");
        } catch (NullPointerException e) {
            System.out.println(e.getMessage() + "\nError in path");
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println(e.getMessage() + "\nError may be in Account.fxml or myCSS.css check it");
        }

    }

    public static UserAccount getCurrentAccount() {
        return currentAccount;
    }

    public void setUserAccount(UserAccount UserAccount) {
        this.currentAccount = UserAccount;
    }

    public void show() {
        //Show main windows for user
        window.show();
    }
}
