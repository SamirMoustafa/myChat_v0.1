package MainWindows.SignIn;

import ChatException.AccountNotExistsException;
import ChatException.AlreadyLogInException;
import ChatException.ImageNotExistsException;
import Core.CONSTANTS;
import Core.MainController;
import Core.MongoHandler;
import Core.UserAccount;
import MainWindows.Account.Account;
import MainWindows.SignUp.SignUp;
import MessageBox.MessageBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

/**
 * Created by Samir Moustafa on 4/30/2017.
 */

public class SignInController extends MainController implements Initializable, CONSTANTS {

    public double x0 = SignIn.x0, y0 = SignIn.y0;
    @FXML
    ImageView mainLogo, btnImage1;
    Stage mainWindow = SignIn.window;
    @FXML
    private TextField emailText, passText;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        mainLogo.setImage(new Image("bin/logo.png"));
        mainLogo.setFitWidth(300);
        mainLogo.setFitHeight(150);

        btnImage1.setImage(new Image("bin/loginBtn.png"));
        btnImage1.setFitWidth(291);
        btnImage1.setFitHeight(48);

        mainWindow.setOnShowing(event -> {
            mainWindow.getScene().setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER)
                    login();
            });
        });
;
    }

    public void login() {

        UserAccount currentAccount = null;
        try {
            currentAccount = MongoHandler.getUserAccountForLogin(emailText.getText(), passText.getText());
            if (currentAccount == null)
                throw new AlreadyLogInException();

            try (InputStream in1 = new ByteArrayInputStream(MongoHandler.getImageArrayByte(currentAccount.getEMail()))) {
                new File("src/main/resources/bin/image.png").delete();
                Files.copy(in1, Paths.get("src/main/resources/bin/image.png"));
            } catch (ImageNotExistsException e) {
                new MessageBox("Caution", "Server error in loading image.\n").show();
                return;
            } catch (IOException e) {
                new MessageBox("Caution", "Please try to choose a PNG image that matches required.\n").show();
            }
            new Account(currentAccount).show();

        } catch (AccountNotExistsException e) {
            new MessageBox("Caution", "It's seem that you entered wrong e-mail or password.\nPlease try to enter one of them correctly.\n").show();
            return;
        } catch (AlreadyLogInException e) {
            new MessageBox("Caution", "It's seem that you are login from another device.\nPlease logout from it and try again.\nYour entered e-mail : " + emailText.getText()).show();
            return;
        }
    }

    public void signup() {
        new SignUp().show();
    }

    public void close() {
        mainWindow.close();
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
}
