import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Scanner;

public class CardsFileActivities {
    public void loadCardsFromFile(String filepath, Map<String, Card> cards)
    {
        try
        {
            File file = new File(filepath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext())
            {
                String[] data = scanner.nextLine().split(" ");
                String cardNumber = data[0];
                int pin = Integer.parseInt(data[1]);
                double balance = Double.parseDouble(data[2]);
                int pin_attempts = Integer.parseInt(data[3]);

                LocalDateTime block_time = null;
                if (!data[4].equals("null"))
                    block_time = LocalDateTime.parse(data[4]);

                cards.put(cardNumber, new Card(cardNumber, pin, balance, pin_attempts, block_time));
            }

            scanner.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Ошибка. Файл не найден!");
        }
    }

    public void saveCardsToFile(String filepath, Map<String, Card> cards)
    {
        try
        {
            File file = new File(filepath);
            PrintWriter writer = new PrintWriter(file);

            for (Card card : cards.values())
            {
                writer.println(card.getCardNumber() + " " + card.getPin() + " " + card.getBalance() + " " + card.getPinAttempts() + " " + card.getBlockTime());
            }

            writer.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Ошибка. Файл не найден!");
        }
    }
}
