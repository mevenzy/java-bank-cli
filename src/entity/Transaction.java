package entity;

import java.math.BigDecimal;

/**
 * Запись в истории операций пользователя — неизменна (record).
 * counterparty — второй участник перевода (для DEPOSIT/WITHDRAW — null).
 * seq — глобальный порядковый номер операции, задаёт порядок в истории.
 */
public record Transaction(
        TransactionType type,
        String counterparty,
        BigDecimal amount,
        long seq
) {
}
