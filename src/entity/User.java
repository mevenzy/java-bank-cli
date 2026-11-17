package entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Пользователь платёжной системы: имя (уникально, регистрозависимо) + баланс.
 * История операций хранится отдельным списком — транзакции неизменны.
 */
public class User {

    private final String name;
    private BigDecimal balance = BigDecimal.ZERO;
    private final List<Transaction> history = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        balance = balance.subtract(amount);
    }

    public void addToHistory(Transaction tx) {
        history.add(tx);
    }

    public List<Transaction> getHistory() {
        return List.copyOf(history);
    }
}
