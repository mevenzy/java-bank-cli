import repository.UserRepository;
import service.PaymentService;
import ui.ConsoleApp;

import java.util.Scanner;

/**
 * Точка входа: собираем слои (repository → service → ui) и крутим цикл
 * чтения команд из stdin. Пустая история печатает пустую строку —
 * это корректный ответ протокола.
 */
public class Main {

    public static void main(String[] args) {
        ConsoleApp app = new ConsoleApp(new PaymentService(new UserRepository()));
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String response = app.handle(line);
                if (response == null) {
                    break;
                }
                System.out.println(response);
            }
        }
    }
}
