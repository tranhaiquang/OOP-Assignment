public class IDCard {
    private int cardNumber;
    private String fullName;
    private String gender;
    private String dob;
    private String address;
    private int phone;

    public IDCard(int cardNumber, String fullName, String gender, String dob, String address, int phone) {
        this.cardNumber = cardNumber;
        this.fullName = fullName;
        this.gender = gender;
        this.dob = dob;
        this.address = address;
        this.phone = phone;
    }

    // Getter methods
    public int getCardNumber() {
        return cardNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public String getGender() {
        return gender;
    }

    public String getDob() {
        return dob;
    }

    public String getAddress() {
        return address;
    }

    public int getPhone() {
        return phone;
    }

    // Setter methods
    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    // toString method
    @Override
    public String toString() {
        return cardNumber + "," +
                fullName + "," +
                gender + "," +
                dob + "," +
                address + "," +
                phone;
    }
}
