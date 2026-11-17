package entity;

/**
 * Тип операции с точки зрения владельца истории:
 * TRANSFER_OUT — исходящий перевод (у отправителя),
 * TRANSFER_IN — входящий (у получателя).
 */
public enum TransactionType {
    DEPOSIT,
    WITHDRAW,
    TRANSFER_OUT,
    TRANSFER_IN
}
