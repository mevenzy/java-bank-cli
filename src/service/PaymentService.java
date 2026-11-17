package service;

import entity.Transaction;
import entity.TransactionType;
import entity.User;
import repository.UserRepository;
import service.exception.DuplicateUserException;
import service.exception.InsufficientFundsException;
import service.exception.InvalidPaymentException;
import service.exception.SameUserTransferException;
import service.exception.UserNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Вся бизнес-логика. Правила:
 * — деньги только в BigDecimal (double для денег теряет точность);
 * — сначала ВСЕ проверки, потом мутации: команда либо применяется целиком,
 *   либо не меняет состояние (атомарность — мини-ACID);
 * — сумма: положительная, не больше 2 знаков после точки.
 */
public class PaymentService {

    private final UserRepository repository;
    private final AtomicLong seq = new AtomicLong();

    public PaymentService(UserRepository repository) {
        this.repository = repository;
    }

    public void createUser(String name) {
        if (repository.existsByName(name)) {
            throw new DuplicateUserException(name);
        }
        repository.save(new User(name));
    }

    public BigDecimal balance(String name) {
        return findUser(name).getBalance();
    }

    public void deposit(String name, BigDecimal amount) {
        User user = findUser(name);
        validateAmount(amount);
        user.deposit(amount);
        record(user, new Transaction(TransactionType.DEPOSIT, null, amount, nextSeq()));
    }

    public void withdraw(String name, BigDecimal amount) {
        User user = findUser(name);
        validateAmount(amount);
        checkFunds(user, amount);
        user.withdraw(amount);
        record(user, new Transaction(TransactionType.WITHDRAW, null, amount, nextSeq()));
    }

    /**
     * Перевод — единая операция: проверяем всё до списания, при сбое
     * зачисления откатываем списание, чтобы не получить деньги из воздуха.
     */
    public void transfer(String fromName, String toName, BigDecimal amount) {
        User from = findUser(fromName);
        User to = findUser(toName);
        if (fromName.equals(toName)) {
            throw new SameUserTransferException(fromName);
        }
        validateAmount(amount);
        checkFunds(from, amount);

        from.withdraw(amount);
        try {
            to.deposit(amount);
        } catch (RuntimeException e) {
            from.deposit(amount); // rollback — состояние как до команды
            throw e;
        }

        long txSeq = nextSeq();
        record(from, new Transaction(TransactionType.TRANSFER_OUT, toName, amount, txSeq));
        record(to, new Transaction(TransactionType.TRANSFER_IN, fromName, amount, txSeq));
    }

    public List<Transaction> history(String name) {
        return findUser(name).getHistory();
    }

    private User findUser(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new UserNotFoundException(name));
    }

    /** Положительная сумма, до 2 знаков после точки — как у настоящих денег. */
    private void validateAmount(BigDecimal amount) {
        if (amount.signum() <= 0 || amount.stripTrailingZeros().scale() > 2) {
            throw new InvalidPaymentException(amount.toPlainString());
        }
    }

    private void checkFunds(User user, BigDecimal amount) {
        if (user.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException(user.getName());
        }
    }

    private void record(User user, Transaction tx) {
        user.addToHistory(tx);
    }

    private long nextSeq() {
        return seq.incrementAndGet();
    }
}
