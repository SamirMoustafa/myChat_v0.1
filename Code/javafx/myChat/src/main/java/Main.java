import Core.MongoHandler;
import MainWindows.SignIn.SignIn;
import MessageBox.MessageBox;

/**
 * Created by Samir Moustafa on 4/30/2017.
 */

public class Main {

    public static void main(String... args){

        MongoHandler myDataBase = null;

        try {
            myDataBase = new MongoHandler();
            new SignIn().main(args);
        }catch (Exception e){
            new MessageBox("Error", "Error may cased by \n" + e.getCause() + "\n" + e.getMessage()).show();
        }
        finally {
            if (myDataBase != null)
                myDataBase.closeConnection();
        }

    }
}