import java.time.LocalDateTime;

public class Card
{
    private final String card_number;
    private final int pin;
    private double balance;
    private int pin_attempts; // Количество попыток для ввода ПИН-код
    private LocalDateTime block_time; // Время блокировки карты

    Card(String card_number, int pin, double balance)
    {
        this.card_number = card_number;
        this.pin = pin;
        this.balance = balance;
        pin_attempts = 0;
        block_time = null;
    }

    Card(String card_number, int pin, double balance, int pin_attempts, LocalDateTime block_time)
    {
        this.card_number = card_number;
        this.pin = pin;
        this.balance = balance;
        this.pin_attempts = pin_attempts;
        this.block_time = block_time;
    }

    public String getCardNumber()
    {
        return card_number;
    }

    public int getPin()
    {
        return pin;
    }

    public double getBalance()
    {
        return balance;
    }

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public void incPinAttempts()
    {
        pin_attempts++;
    }

    public int getPinAttempts()
    {
        return pin_attempts;
    }

    public void resetPinAttempts()
    {
        pin_attempts = 0;
        block_time = null;
    }

    public LocalDateTime getBlockTime()
    {
        return block_time;
    }

    public void setBlockTime(LocalDateTime block_time)
    {
        this.block_time = block_time;
    }

    public void resetBlockTime()
    {
        block_time = null;
    }
}
