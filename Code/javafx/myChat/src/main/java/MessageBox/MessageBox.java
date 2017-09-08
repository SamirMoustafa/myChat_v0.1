package MessageBox;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Created by Samir Moustafa on 5/3/2017.
 */
public class MessageBox {

    public static Stage window;
    public static double x0 = 500, y0 = 210;

    public MessageBox(String Header, String Content) {
        //Make new windows
        window = new Stage();
        //Set Core for MessageBox
        MessageBoxController.setMsgBoxHeaderString(Header);
        MessageBoxController.setMsgBoxContentString(Content);
        //Put header for main windows
        window.setTitle("Error");
        //Remove toolbar
        window.initStyle(StageStyle.UNDECORATED);
        window.initStyle(StageStyle.TRANSPARENT);
        //Load myFXML file
        try {
            //Load FXML file
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Accessories/MessageBox.fxml"));
            //Make new scene with fxml file
            Scene scene = new Scene(root, x0, y0);
            //Set myCSS file in scene
            scene.getStylesheets().add(getClass().getClassLoader().getResource("bin/myCSS.css").toExternalForm());
            //Load scene in main windows
            window.setScene(scene);

        } catch (IOException e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage() + "\nError may be in MessageBox.fxml or myCSS.css check it");
        } catch (NullPointerException e) {
            System.out.println(e.getMessage() + "\nError in path");
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println(e.getMessage() + "\nError may be in MessageBox.fxml or myCSS.css check it");
        }

    }

    public void show() {
        //Make Windows screen always on top
        window.initModality(Modality.APPLICATION_MODAL);
        //Show main windows for user
        window.show();
    }

}
