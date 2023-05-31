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

	@Override
	public String toString() {
		return this.phone + "," +
				+this.balance;

	}

	public int getPhone() {
		return this.phone;
	}
}
