package service.exception;

public class InsufficientFundsException extends PaymentException {

    public InsufficientFundsException(String name) {
        super("INSUFFICIENT_FUNDS", "Недостаточно средств у пользователя: " + name);
    }
}
