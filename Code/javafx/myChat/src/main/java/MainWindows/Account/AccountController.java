package MainWindows.Account;

/**
 * Created by Samir Moustafa on 5/1/2017.
 */

import Core.MainController;
import Core.MongoHandler;
import Core.UserAccount;
import MainWindows.SignIn.SignIn;
import com.mongodb.DBObject;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AccountController extends MainController {
    private final String profileImagePath = "src/main/resources/bin/image.png";
    @FXML
    ImageView mainAccountImageView, updateAccountImageView, sendImageView;
    @FXML
    Label wellcomeLabel, conversationHeaderLabel;
    @FXML
    TextField mailText, passText, nameText, ageText, mobileText, browseText, sendMsgText, searchTxt;
    @FXML
    VBox accountsVBox;
    @FXML
    ListView<HBox> messagesList;
    private double x0 = Account.x0, y0 = Account.y0;
    private String chatWithName, chatWithMail;
    private Stage mainWindow = Account.window;
    private UserAccount currentAccount;

    @FXML
    public void initialize() {
        currentAccount = Account.currentAccount;
        wellcomeLabel.setText(wellcomeLabel.getText() + currentAccount.getName());
        sendImageView.setImage(new Image("bin/sendImage.png"));
        sendImageView.setFitWidth(20);
        sendImageView.setFitHeight(20);
        setToDefault();
        mainWindow.setOnShowing(event -> {
            mainWindow.getScene().setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.F5)
                    viewMessagesForAccount();
            });
        });
        sendMsgText.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER)
                sendMessage();
            if (e.getCode() == KeyCode.F5)
                viewMessagesForAccount();
        });
        searchTxt.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER)
                searchForAccounts();
        });

        MongoHandler.setAccountloggedIn(currentAccount);
        mainWindow.setOnCloseRequest(event -> MongoHandler.setAccountloggedOut(currentAccount));
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

    @FXML
    private void setToDefault() {
        mailText.setText(currentAccount.getEMail());
        passText.setText(currentAccount.getPass());
        nameText.setText(currentAccount.getName());
        ageText.setText((int) currentAccount.getAge() + "");
        mobileText.setText(currentAccount.getPhone());
        browseText.setText(currentAccount.getPhoto());
        try {
            handImage(mainAccountImageView, new File(profileImagePath), browseText);
            handImage(updateAccountImageView, new File(profileImagePath), browseText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logout() {
        MongoHandler.setAccountloggedOut(currentAccount);
        SignIn backToMain = new SignIn();
        mainWindow.close();
        backToMain.show();
    }

    @FXML
    public void browseForImage() {
        browsController(mainWindow, updateAccountImageView, browseText);
    }

    @FXML
    public void saveAccountChanges() {
        MongoHandler.updateAccount(new UserAccount(
                nameText.getText().toString(),
                Double.parseDouble(ageText.getText().toString()),
                "",
                mobileText.getText().toString(),
                mailText.getText().toString(),
                passText.getText().toString(),
                browseText.getText().toString()));
        try {
            mainAccountImageView.setImage(null);
            updateAccountImageView.setImage(null);
        } catch (NullPointerException e) {
            System.out.println("error in handle images\n" + e.getMessage());
        }
        try {
            handImage(updateAccountImageView, new File(browseText.getText().toString()), browseText);
            mainAccountImageView = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentAccount.updateUserAccount(
                nameText.getText().toString(),
                Integer.parseInt(ageText.getText().toString()),
                "",
                mobileText.getText().toString(),
                mailText.getText().toString(),
                browseText.getText().toString());
    }

    @FXML
    public void searchForAccounts() {
        accountsVBox.getChildren().clear();
        List<DBObject> accounts = MongoHandler.getAccountsList(searchTxt.getText());
        for (DBObject i : accounts) {
            Button tempBtn = new Button(i.get("name").toString());
            if (tempBtn.getText().compareTo(currentAccount.getName()) == 0)
                continue;
            tempBtn.setPrefWidth(320);
            tempBtn.setOnMouseClicked(e -> {
                conversationHeaderLabel.setText("chat with " + i.get("name").toString());
                chatWithMail = i.get("email").toString();
                chatWithName = i.get("name").toString();
                messagesList.getItems().clear();
                viewMessagesForAccount();
            });
            accountsVBox.getChildren().add(tempBtn);
        }
    }

    @FXML
    public void viewMessagesForAccount() {
        messagesList.getItems().clear();
        List<DBObject> conv = MongoHandler.getChatBetweenTwoAccounts(currentAccount.getEMail(), chatWithMail);
        for (DBObject i : conv) {
            HBox tempHBox = new HBox(2);
            Label tempLabel = new Label(String.valueOf(i.get("text")));
            tempLabel.setStyle("-fx-font-size: 2.5em;");
            if (String.valueOf(i.get("senderName")).compareTo(currentAccount.getName()) == 0) {
                tempHBox.setAlignment(Pos.BASELINE_RIGHT);
                tempLabel.setStyle("-fx-font-size: 2.5em;-fx-text-fill: #6dcdff;");
            }
            tempHBox.getChildren().add(tempLabel);
            messagesList.getItems().add(tempHBox);
        }
        messagesList.scrollTo(messagesList.getItems().size());
    }

    @FXML
    public void sendMessage() {
        if (sendMsgText.getText().compareTo("") != 0) {
            MongoHandler.setChatBetweenTwoAccounts(currentAccount.getEMail(), chatWithMail, currentAccount.getName(), chatWithName, sendMsgText.getText());
            sendMsgText.setText("");
            viewMessagesForAccount();
        }
    }
}
