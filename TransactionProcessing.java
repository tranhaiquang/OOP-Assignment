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
        // code here
        return null;
    }

    // Requirement 5
    public ArrayList<IDCard> getCustomersHaveBoth() {
        // code here
        return null;
    }

    // Requirement 6
    public void processTopUp(String path) {
        // code here
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
