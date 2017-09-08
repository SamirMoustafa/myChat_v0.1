package ChatException;

/**
 * Created by Samir Moustafa on 5/4/2017.
 */
public class AlreadyLogInException extends RuntimeException {

    public AlreadyLogInException() {
        super();
    }

    public AlreadyLogInException(String message) {
        super(message);
    }

    public AlreadyLogInException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyLogInException(Throwable cause) {
        super(cause);
    }

    protected AlreadyLogInException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
