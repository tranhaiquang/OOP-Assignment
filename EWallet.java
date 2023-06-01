public class EWallet implements Payment, Transfer {
	private int phone;
	private double balance;

	public EWallet(int phone) {
		this.phone = phone;
		balance = 0;
	}

	@Override
	public boolean transfer(double amount, Transfer to) {
		double totalAmount = amount + Transfer.transferFee * amount;

		if (totalAmount <= this.balance) {
			if (to instanceof EWallet) {
				EWallet temp = (EWallet) to;
				temp.topUp(amount);
				this.balance -= totalAmount;
			} 
			else if (to instanceof BankAccount) {
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
		} else
			return false;
	}

	@Override
	public String toString() {
		return this.phone + "," +
				+this.balance;

	}

	public void topUp(double amount) {
		this.balance += amount;
	}
	public int getPhone() {
		return this.phone;
	}
}
