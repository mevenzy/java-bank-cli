package service.exception;

public class SameUserTransferException extends PaymentException {

    public SameUserTransferException(String name) {
        super("SAME_USER_TRANSFER", "Перевод самому себе: " + name);
    }
}
