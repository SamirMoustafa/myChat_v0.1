package MessageBox;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Samir Moustafa on 5/3/2017.
 */
public class MessageBoxController implements Initializable {

    static String _Header, _Content;
    double x0 = MessageBox.x0, y0 = MessageBox.y0;
    Stage mainWindow = MessageBox.window;
    @FXML
    Label msgBoxHeader, msgBoxContent;

    protected static void setMsgBoxHeaderString(String Header) {
        _Header = Header;
    }

    protected static void setMsgBoxContentString(String Content) {
        _Content = Content;
    }

    public void initialize(URL location, ResourceBundle resources) {
        msgBoxHeader.setText(this._Header);
        msgBoxContent.setText(this._Content);

        mainWindow.setOnShowing(event -> {
            mainWindow.getScene().setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER)
                    close();
            });
        });
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

    public void close() {
        MessageBox.window.close();
    }
}
