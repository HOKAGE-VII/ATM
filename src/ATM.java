import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class ATM
{
    private static final String CARDS_PATH_FILE = "cards.txt"; // Файл с данными по картам
    private static final int MAX_AMOUNT_WITHDRAW = 1000000; // Макс. кол-во снятия денежных средств
    private static final int MAX_PIN_ATTEMPTS = 3; // Макс. количество попыток ввода ПИН-кода
    private static final int BLOCK_TIME_HOURS = 24; // Время блокировки карты (в часах)
    private double atm_balance = 99999999; // Баланс банкомата

    private Map<String, Card> cards;

    private CardsFileActivities cards_file;

    public ATM()
    {
        cards = new HashMap<>();
        cards_file = new CardsFileActivities();
        cards_file.loadCardsFromFile(CARDS_PATH_FILE, cards);
    }

    public boolean checkCardNumber(String card_number)
    {
        Card card = cards.get(card_number);

        if (card == null)
        {
            System.out.println("Ошибка. Проверьте номер карты!");
            return false;
        }
        //Время блокировки еще не прошло (1 день)
        if (card.getBlockTime() != null && Duration.between(card.getBlockTime(), LocalDateTime.now()).toHours() < BLOCK_TIME_HOURS)
        {
            System.out.println("Ваша карта заблокирована. Разблокировка произойдет " + card.getBlockTime().plusHours(24));
            return false;
        }

        if (card.getPinAttempts() >= MAX_PIN_ATTEMPTS)
        {
            card.resetPinAttempts();
            card.resetBlockTime();
            cards_file.saveCardsToFile(CARDS_PATH_FILE, cards);
        }

        return true;
    }

    public int checkPin(String card_number, int pin)
    {
        Card card = cards.get(card_number);

        //ПИН-код правильный
        if (card.getPin() == pin)
        {
            System.out.println("ПИН-код верный.");
            card.resetPinAttempts();
            cards_file.saveCardsToFile(CARDS_PATH_FILE, cards);
            return 0;
        }
        //ПИН-код неправильный
        else
        {
            card.incPinAttempts();

            // Если пользователь использовал все попытки ввести ПИН-код (3 попытки), то блокируем карту
            if (card.getPinAttempts() >= MAX_PIN_ATTEMPTS)
            {
                System.out.println("Вы использовали 3/3 попыток ввода ПИН-кода, Ваша карта заблокирована на 24 часа.");
                card.setBlockTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                cards_file.saveCardsToFile(CARDS_PATH_FILE, cards);
                return 1;
            }
            else
            {
                System.out.println("Неверный ПИН-код. Оставшееся количество попыток: " + (MAX_PIN_ATTEMPTS - card.getPinAttempts()));
                cards_file.saveCardsToFile(CARDS_PATH_FILE, cards);
                return 2;
            }
        }
    }

    public boolean withdraw(String card_number, double amount)
    {
        Card card = cards.get(card_number);
        if (card == null)
            return false;

        double curr_balance = card.getBalance();
        if (curr_balance >= amount && atm_balance >= amount)
        {
            curr_balance -= amount;
            card.setBalance(curr_balance);
            atm_balance -= amount;
            cards_file.saveCardsToFile(CARDS_PATH_FILE, cards);
            return true;
        }
        return false;
    }

    public boolean deposit(String card_number, double amount)
    {
        Card card = cards.get(card_number);
        if (card == null)
            return false;

        if (amount <= MAX_AMOUNT_WITHDRAW)
        {
            double curr_balance = cards.get(card_number).getBalance();
            curr_balance += amount;
            cards.get(card_number).setBalance(curr_balance);
            atm_balance += amount;
            cards_file.saveCardsToFile(CARDS_PATH_FILE, cards);
            return true;
        }
        return false;
    }

    public double checkBalance(String card_number)
    {
        double curr_balance = cards.get(card_number).getBalance();
        return curr_balance;
    }
}
