package Core;

/**
 * Created by Samir Moustafa on 5/16/2017.
 */
public interface CONSTANTS {
    String HOST = "localhost";
    short PORT = 27017;
    String DATA_BASE_NAME = "test";
    String ACCOUNTS_COLLECTION_NAME = "myChat";
    String CHAT_COLLECTION_NAME = "messages";

    String EMAIL_REG = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    String NAME_REG = "^[\\p{L} .'-]+$";
    String AGE_REG = "^(0?[1-9]|[1-9][0-9])$";
    String PHONE_REG = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";


}
