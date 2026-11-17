package service.exception;

public class DuplicateUserException extends PaymentException {

    public DuplicateUserException(String name) {
        super("DUPLICATE_USER", "Пользователь уже существует: " + name);
    }
}
