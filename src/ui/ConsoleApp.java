package ui;

import entity.Transaction;
import service.PaymentService;
import service.exception.InvalidPaymentException;
import service.exception.PaymentException;

import java.math.BigDecimal;
import java.util.List;

/**
 * Слой ввода-вывода: разбор команд, маппинг доменных исключений в
 * `ERROR <код>` протокола. Логики здесь нет — только парсинг и формат.
 */
public class ConsoleApp {

    private final PaymentService service;

    public ConsoleApp(PaymentService service) {
        this.service = service;
    }

    /**
     * @return строка ответа или null — завершить программу (EXIT).
     */
    public String handle(String line) {
        String[] parts = line.trim().split("\\s+");
        String command = parts[0];
        try {
            return switch (command) {
                case "EXIT" -> null;
                case "CREATE" -> {
                    requireArgs(parts, 2);
                    service.createUser(parts[1]);
                    yield "OK";
                }
                case "BALANCE" -> {
                    requireArgs(parts, 2);
                    yield service.balance(parts[1]).toPlainString();
                }
                case "DEPOSIT" -> {
                    requireArgs(parts, 3);
                    service.deposit(parts[1], parseAmount(parts[2]));
                    yield "OK";
                }
                case "WITHDRAW" -> {
                    requireArgs(parts, 3);
                    service.withdraw(parts[1], parseAmount(parts[2]));
                    yield "OK";
                }
                case "TRANSFER" -> {
                    requireArgs(parts, 4);
                    service.transfer(parts[1], parts[2], parseAmount(parts[3]));
                    yield "OK";
                }
                case "HISTORY" -> {
                    requireArgs(parts, 2);
                    yield formatHistory(service.history(parts[1]));
                }
                default -> "ERROR UNKNOWN_COMMAND";
            };
        } catch (PaymentException e) {
            return "ERROR " + e.getErrorCode();
        }
    }

    private void requireArgs(String[] parts, int count) {
        if (parts.length != count) {
            throw new UnknownCommand();
        }
    }

    private BigDecimal parseAmount(String raw) {
        try {
            return new BigDecimal(raw);
        } catch (NumberFormatException e) {
            throw new InvalidPaymentException(raw);
        }
    }

    /**
     * HISTORY печатает по строке на операцию — поэтому возвращаем
     * многострочный ответ (пустая история → пустой ответ).
     */
    private String formatHistory(List<Transaction> history) {
        StringBuilder out = new StringBuilder();
        for (Transaction tx : history) {
            if (!out.isEmpty()) {
                out.append('\n');
            }
            out.append(switch (tx.type()) {
                case DEPOSIT -> "DEPOSIT " + tx.amount().toPlainString();
                case WITHDRAW -> "WITHDRAW " + tx.amount().toPlainString();
                case TRANSFER_OUT -> "TRANSFER_OUT " + tx.counterparty() + " " + tx.amount().toPlainString();
                case TRANSFER_IN -> "TRANSFER_IN " + tx.counterparty() + " " + tx.amount().toPlainString();
            });
        }
        return out.toString();
    }

    /** Неверное число аргументов/сумма — по протоколу это UNKNOWN_COMMAND. */
    private static final class UnknownCommand extends PaymentException {
        UnknownCommand() {
            super("UNKNOWN_COMMAND", "Неверный формат команды");
        }
    }
}
