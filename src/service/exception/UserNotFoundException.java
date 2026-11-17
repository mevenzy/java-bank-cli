package service.exception;

public class UserNotFoundException extends PaymentException {

    public UserNotFoundException(String name) {
        super("USER_NOT_FOUND", "Пользователь не найден: " + name);
    }
}
