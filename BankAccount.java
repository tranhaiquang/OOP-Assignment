public class BankAccount implements Payment, Transfer {
    private int number;
    private double interestRate;
    private double balance;

    public BankAccount(int number, double interestRate) {
        this.number = number;
        this.interestRate = interestRate;
        this.balance = 50;
    }

    @Override
    public boolean transfer(double amount, Transfer to) {
        double totalAmount = amount + amount * to.transferFee;

        if (totalAmount + 50 <= this.balance) {
            this.balance -= totalAmount;
            // topUp(amount);
            return true;
        } else
            return false;
    }

    @Override
    public double checkBalance() {
        return this.balance;
    }

    @Override
    public boolean pay(double amount) {
        return false;
    }

    public boolean deposit(double amount) {
        if (amount > this.balance)
            return false;
        else {
            this.balance += amount;
            return true;
        }

    }

    @Override
    public String toString() {
        return this.number + "," +
                this.interestRate + "," +
                this.balance;

    }

    public int getNumber() {
        return this.number;
    }
}
