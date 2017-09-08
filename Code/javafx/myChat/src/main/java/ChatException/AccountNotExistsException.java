package ChatException;

/**
 * Created by Samir Moustafa on 5/4/2017.
 */
public class AccountNotExistsException extends RuntimeException {
    public AccountNotExistsException() {
        super();
    }

    public AccountNotExistsException(String message) {
        super(message);
    }

    public AccountNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountNotExistsException(Throwable cause) {
        super(cause);
    }

    protected AccountNotExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
