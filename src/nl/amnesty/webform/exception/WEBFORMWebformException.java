package nl.amnesty.webform.exception;

/**
 *
 * @author evelzen
 */
public class WEBFORMWebformException extends WEBFORMException {

    public WEBFORMWebformException() {
        super();
    }

    public WEBFORMWebformException(String reason) {
        super(reason);
    }

    public WEBFORMWebformException(String reason, Throwable cause) {
        super(reason, cause);
    }
}
