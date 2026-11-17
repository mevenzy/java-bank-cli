import java.util.Scanner;

/**
 * Payment System — консольный процессинг платежей.
 *
 * Полное ТЗ со стадиями и протоколом: https://javacase.ru/projects/payment-system
 *
 * Кратко: читаем команды из stdin (по одной на строку), на каждую печатаем
 * РОВНО одну строку ответа. EXIT или конец ввода — завершение.
 */
public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String response = handle(line);
                if (response == null) {
                    break;
                }
                System.out.println(response);
            }
        }
    }

    /**
     * Обрабатывает одну команду.
     *
     * @return строка ответа или null — если пора завершать программу (EXIT).
     */
    private static String handle(String line) {
        String[] parts = line.split("\\s+");
        String command = parts[0];

        return switch (command) {
            case "EXIT" -> null;
            // TODO: стадия 1 — CREATE <name>, BALANCE <name>
            // TODO: стадия 2 — DEPOSIT, WITHDRAW
            // TODO: стадия 3 — TRANSFER
            // TODO: стадия 5 — HISTORY
            // Полный контракт команд и коды ошибок — в ТЗ проекта.
            default -> "ERROR UNKNOWN_COMMAND";
        };
    }
}
