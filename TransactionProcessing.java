import java.util.*;
import java.io.*;

public class TransactionProcessing {
    private ArrayList<Payment> paymentObjects;
    private IDCardManagement idcm;

    public TransactionProcessing(String idCardPath, String paymentPath) {
        idcm = new IDCardManagement(idCardPath);
        readPaymentObject(paymentPath);

    }

    public ArrayList<Payment> getPaymentObject() {
        return this.paymentObjects;
    }

    // Requirement 3
    public boolean readPaymentObject(String path) {
        ArrayList<Payment> paymentObjects = new ArrayList<>();
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] arr = data.split(",");

                if (arr.length > 1) {
                    Payment ba = new BankAccount(Integer.parseInt(arr[0]), Double.parseDouble(arr[1]));
                    paymentObjects.add(ba);
                } else {
                    if (arr[0].length() == 7) {
                        Payment ew = new EWallet(Integer.parseInt(arr[0]));
                        paymentObjects.add(ew);
                    } else {
                        for (IDCard idc : idcm.getIDCards()) {
                            if (idc.getCardNumber() == Integer.parseInt(arr[0])) {
                                try {
                                    Payment cc = new ConvenientCard(idc);

                                    paymentObjects.add(cc);
                                } catch (CannotCreateCard e) {
                                    System.out.println(e);
                                }

                            }

                        }

                    }

                }

            }
            this.paymentObjects = paymentObjects;
            myReader.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }

    }

    // Requirement 4
    public ArrayList<ConvenientCard> getAdultConvenientCards() {
        ArrayList<ConvenientCard> adultConvenientCards = new ArrayList<ConvenientCard>();

        for (Payment p : paymentObjects) {
            if (p instanceof ConvenientCard) {
                ConvenientCard temp = (ConvenientCard) p;
                if (temp.getType().equals("Adult"))
                    adultConvenientCards.add(temp);
            }

        }
        return adultConvenientCards;
    }

    // Requirement 5
    public ArrayList<IDCard> getCustomersHaveBoth() {
        ArrayList<IDCard> customersHaveBoth = new ArrayList<IDCard>();

        for (IDCard card : idcm.getIDCards()) {
            int counter = 0;
            for (Payment p : paymentObjects) {
                if (p instanceof ConvenientCard) {
                    ConvenientCard temp = (ConvenientCard) p;
                    if (temp.getIdCard().getCardNumber() == card.getCardNumber())
                        counter++;
                }

                if (p instanceof EWallet) {
                    EWallet temp = (EWallet) p;
                    if (temp.getPhone() == card.getPhone())
                        counter++;
                }

                if (p instanceof BankAccount) {
                    BankAccount temp = (BankAccount) p;
                    if (temp.getNumber() == card.getCardNumber())
                        counter++;
                }

                if (counter == 3) {
                    customersHaveBoth.add(card);
                    counter = 0;
                }

            }
        }

        return customersHaveBoth;
    }

    // Requirement 6
    public void processTopUp(String path) {
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] arr = data.split(",");

                if (arr[0].equals("CC")) {
                    for (Payment p : paymentObjects) {

                        if (p instanceof ConvenientCard) {
                            ConvenientCard temp = (ConvenientCard) p;
                            if (temp.getIdCard().getCardNumber() == Integer.parseInt(arr[1])) {
                                temp.deposit(Integer.parseInt(arr[2]));
                                break;
                            }

                        }
                    }
                }

                else if (arr[0].equals("BA")) {
                    for (Payment p : paymentObjects) {
                        if (p instanceof BankAccount) {
                            BankAccount temp = (BankAccount) p;
                            if (temp.getNumber() == Integer.parseInt(arr[1])) {
                                temp.deposit(Double.parseDouble(arr[2]));
                                break;
                            }
                        }
                    }
                } else if (arr[0].equals("EW")) {
                    for (Payment p : paymentObjects) {
                        if (p instanceof EWallet) {
                            EWallet temp = (EWallet) p;
                            if (temp.getPhone() == Integer.parseInt(arr[1])) {
                                temp.deposit(Integer.parseInt(arr[2]));
                                break;
                            }
                        }
                    }
                }

            }

            myReader.close();

        } catch (FileNotFoundException e) {

        }
    }

    // Requirement 7
    public ArrayList<Bill> getUnsuccessfulTransactions(String path) {
        // code here
        return null;
    }

    // Requirement 8
    public ArrayList<BankAccount> getLargestPaymentByBA(String path) {
        // code here
        return null;
    }

    // Requirement 9
    public void processTransactionWithDiscount(String path) {
        // code here
    }
}
