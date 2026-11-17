package service.exception;

public class InvalidPaymentException extends PaymentException {

    public InvalidPaymentException(String detail) {
        super("INVALID_AMOUNT", "Некорректная сумма: " + detail);
    }
}
