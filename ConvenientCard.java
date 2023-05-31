import java.time.Year;

public class ConvenientCard implements Payment {
	// code here

	private String type;
	private IDCard idCard;
	private double balance;

	public ConvenientCard(IDCard idCard) throws CannotCreateCard {
		this.idCard = idCard;
		this.balance = 100;

		Year currentYear = Year.now();
		int yearValue = currentYear.getValue();
		int age = yearValue - getYear(idCard.getDob());
		if (age < 12)
			throw new CannotCreateCard("Not enough age");
		else if (age <= 18)
			this.type = "Student";
		else
			this.type = "Adult";

	}

	@Override
	public boolean pay(double amount) {
		return false;
	}

	@Override
	public double checkBalance() {
		return this.balance;
	}

	public void deposit(double amount) {
		this.balance += amount;
	}

	@Override
	public String toString() {
		return idCard.getCardNumber() + "," +
				idCard.getFullName() + "," +
				idCard.getGender() + "," +
				idCard.getDob() + "," +
				idCard.getAddress() + "," +
				idCard.getPhone() + "," +
				this.getType() + "," +
				this.checkBalance();
	}

	public String getType() {
		return this.type;
	}

	public int getYear(String dob) {
		return Integer.valueOf(dob.substring(dob.length() - 4));
	}

	public IDCard getIdCard() {
		return this.idCard;
	}
}
