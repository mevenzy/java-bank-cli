# Payment System

Консольный процессинг платежей: пользователи, балансы, переводы, история операций.
Пет-проект JavaCase — полное ТЗ со стадиями и протоколом:
https://javacase.ru/projects/payment-system

## Требования

- JDK 21+
- IntelliJ IDEA Community (или любая другая IDE)

## Запуск

```bash
javac -d out src/Main.java
java -cp out Main
```

Или просто запусти `Main` из IDE.

## Протокол (кратко)

Программа читает команды со стандартного ввода, по одной на строку, и на каждую
печатает **ровно одну строку** ответа. По `EXIT` или концу ввода — завершение.
Никаких приглашений (`>`, «Введите команду») печатать нельзя.

Команды: `CREATE`, `BALANCE`, `DEPOSIT`, `WITHDRAW`, `TRANSFER`, `HISTORY`, `EXIT`.
Полная таблица команд, форматы и пример сессии — в ТЗ на странице проекта.

## Как сдавать

1. Создай публичный репозиторий на GitHub и залей проект:
   ```bash
   git init
   git add .
   git commit -m "payment-system: stage 1"
   git remote add origin https://github.com/<username>/payment-system.git
   git push -u origin main
   ```
2. Пришли ссылку на репозиторий и номер стадии в форме на странице проекта.
3. Этот же репозиторий потом пойдёт в резюме — веди историю коммитов аккуратно.
