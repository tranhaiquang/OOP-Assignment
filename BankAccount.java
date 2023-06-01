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
        double totalAmount = amount + amount * Transfer.transferFee;

        if (totalAmount + 50 <= this.balance) {
            if (to instanceof EWallet) {
                EWallet temp = (EWallet) to;
                temp.topUp(amount);
                this.balance -= totalAmount;
            } else if (to instanceof BankAccount) {
                BankAccount temp = (BankAccount) to;
                temp.topUp(amount);
                this.balance -= totalAmount;
            }
            
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
        if (this.balance >= amount) {
            this.balance -= amount;
            return true;
        }
        else
            return false;
    }

    public void topUp(double amount) {

        this.balance += amount;

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
