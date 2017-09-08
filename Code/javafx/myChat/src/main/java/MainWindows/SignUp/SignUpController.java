package MainWindows.SignUp;

import Core.CONSTANTS;
import Core.MainController;
import Core.MongoHandler;
import Core.UserAccount;
import MainWindows.SignIn.SignIn;
import MessageBox.MessageBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Samir Moustafa on 5/2/2017.
 */
public class SignUpController extends MainController implements Initializable, CONSTANTS {

    @FXML
    ImageView uploadImageView;
    @FXML
    TextField mailText, passText, nameText, ageText, mobileText, browseText;
    private Stage mainWindow = SignUp.window;
    private double centerX, centerY, radius;
    private double x0 = SignUp.x0, y0 = SignUp.y0;
    private String profileImagePath = "src/main/resources/bin/pp.png";

    public void initialize(URL location, ResourceBundle resources) {
        try {
            handImage(uploadImageView, new File(profileImagePath), browseText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void backToSignIn() {
        mainWindow.close();
        SignIn.window.show();
    }

    @FXML
    private void moveWin0(MouseEvent e) {
        x0 = ((Node) (e.getSource())).getScene().getWindow().getX() - e.getScreenX();
        y0 = ((Node) (e.getSource())).getScene().getWindow().getY() - e.getScreenY();
    }

    @FXML
    private void moveWin1(MouseEvent e) {
        mainWindow.setX(e.getScreenX() + x0);
        mainWindow.setY(e.getScreenY() + y0);
    }

    public void browseForImage() {
        browsController(mainWindow, uploadImageView, browseText);
    }

    public void submit() {
        String validationError = "";
        validationError += ((!isValid(EMAIL_REG, mailText.getText())) ? "E-Mail " : "");
        validationError += ((!isValid(NAME_REG, nameText.getText())) ? ",name " : "");
        validationError += ((!isValid(AGE_REG, ageText.getText())) ? ",age " : "");

        if (!validationError.trim().equals("")) {
            new MessageBox("Invalid data", "Not a valid " + validationError + ".\nTry to enter correct data.").show();
            return;
        }

        if (!MongoHandler.newRegisteredAccount(new UserAccount(
                nameText.getText().toString(),
                Double.parseDouble(ageText.getText().toString()),
                "",
                mobileText.getText().toString(),
                mailText.getText().toString(),
                passText.getText().toString(),
                browseText.getText().toString()
        ))) {
            MessageBox error = new MessageBox("Try to login", "Your entered e-mail is already in myChat\nTry to login and enter your password");
            error.show();
        } else {
            MessageBox done = new MessageBox("Congratulations !", "You have create a new account on myChat.\nPlease try to login with your e-mail and password.");
            backToSignIn();
            done.show();
        }
    }
}
