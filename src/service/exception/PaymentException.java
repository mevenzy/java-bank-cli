package service.exception;

/**
 * Базовое исключение домена платежей.
 * errorCode — машинный код, который ui-слой печатает как `ERROR <код>`.
 * Иерархия unchecked: нарушение контракта команды — ошибка вызова,
 * а не штатная ситуация метода.
 */
public abstract class PaymentException extends RuntimeException {

    private final String errorCode;

    protected PaymentException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
