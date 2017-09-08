package ChatException;

/**
 * Created by Samir Moustafa on 5/4/2017.
 */
public class ImageNotExistsException extends RuntimeException {
    public ImageNotExistsException() {
        super();
    }

    public ImageNotExistsException(String message) {
        super(message);
    }

    public ImageNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageNotExistsException(Throwable cause) {
        super(cause);
    }

    protected ImageNotExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
