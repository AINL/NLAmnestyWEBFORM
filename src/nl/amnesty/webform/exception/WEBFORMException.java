package nl.amnesty.webform.exception;

/**
 *
 * @author evelzen
 */
public class WEBFORMException extends java.lang.Exception {

    public WEBFORMException() {
        super();
    }

    public WEBFORMException(String reason) {
        super(reason);
    }

    public WEBFORMException(String reason, Throwable cause) {
        super(reason, cause);
    }
}
