import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        ATM atm = new ATM();
        Scanner scanner = new Scanner(System.in);

        while (true)
        {
            System.out.println("Введите номер карты : ");
            System.out.println("Для выхода нажмите 1.");
            String card_number = scanner.nextLine();

            if (card_number.equals("1")) {
                System.out.println("Работа с банкоматом завершена.");
                return;
            }

            if (!atm.checkCardNumber(card_number))
                continue;

            boolean outerLoop = true;
            while (outerLoop)
            {
                System.out.println("Введите ПИН-код: ");
                System.out.println("Для выхода нажмите 1.");
                System.out.println("Чтобы вернуться назад, нажмите 2.");
                String pinStr = scanner.nextLine();
                int pin = Integer.parseInt(pinStr);

                if (pin == 1)
                    return;
                else if (pin == 2)
                    break;

                int pinCheckResult = atm.checkPin(card_number, pin);

                switch (pinCheckResult)
                {
                    case 0:
                        cardActivities(atm, card_number);
                        outerLoop = false;
                        break;
                    case 1:
                        outerLoop = false;
                        break;
                    case 2:
                        continue;
                    default:
                        System.out.println("Ошибка!");
                        return;
                }
            }
        }
    }

    public static void cardActivities(ATM atm, String card_number)
    {
        Scanner scanner = new Scanner(System.in);

        while (true)
        {
            System.out.println("Выберите действие:");
            System.out.println("1. Проверить баланс");
            System.out.println("2. Снять средства");
            System.out.println("3. Пополнить баланс");
            System.out.println("4. Выйти");
            int choice = scanner.nextInt();

            switch (choice)
            {
                case 1:
                {
                    double balance = atm.checkBalance(card_number);
                    System.out.println("Баланс: " + balance);
                    break;
                }
                case 2:
                {
                    System.out.println("Введите сумму для снятия: ");
                    double amount = scanner.nextDouble();
                    if (atm.withdraw(card_number, amount))
                        System.out.println("Денежные средства успешно сняты.");
                    else
                        System.out.println("Ошибка снятия денежных средств!");
                    break;
                }
                case 3:
                {
                    System.out.println("Введите сумму пополения: ");
                    double amount = scanner.nextDouble();
                    if (atm.deposit(card_number, amount))
                        System.out.println("Баланс успешно пополнен.");
                    else
                        System.out.println("Ошибка при пополнении баланса!");
                    break;
                }
                case 4:
                {
                    System.out.println("Работа с картой завершена.");
                    return;
                }
                default:
                    System.out.println("Некорректный выбор.");
            }
        }
    }
}

