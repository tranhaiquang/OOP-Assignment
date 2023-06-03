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
                                temp.topUp(Integer.parseInt(arr[2]));
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
                                temp.topUp(Double.parseDouble(arr[2]));
                                break;
                            }
                        }
                    }
                } else if (arr[0].equals("EW")) {
                    for (Payment p : paymentObjects) {
                        if (p instanceof EWallet) {
                            EWallet temp = (EWallet) p;
                            if (temp.getPhone() == Integer.parseInt(arr[1])) {
                                temp.topUp(Integer.parseInt(arr[2]));
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
        ArrayList<Bill> unsuccessfulBill = new ArrayList<Bill>();
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] arr = data.split(",");

                for (Payment p : paymentObjects) {
                    switch (arr[3]) {
                        case "BA":
                            if (p instanceof BankAccount) {
                                BankAccount tempBA = (BankAccount) p;
                                if (tempBA.getNumber() == Integer.parseInt(arr[4])) {
                                    if (!tempBA.pay(Double.parseDouble(arr[1]))) {
                                        Bill bill = new Bill(Integer.parseInt(arr[0]), Double.parseDouble(arr[1]),
                                                arr[2]);
                                        unsuccessfulBill.add(bill);
                                    }
                                }

                            }
                            break;
                        case "EW":
                            if (p instanceof EWallet) {
                                EWallet tempEW = (EWallet) p;
                                if (tempEW.getPhone() == Integer.parseInt(arr[4])) {
                                    if (!tempEW.pay(Double.parseDouble(arr[1]))) {
                                        Bill bill = new Bill(Integer.parseInt(arr[0]), Double.parseDouble(arr[1]),
                                                arr[2]);
                                        unsuccessfulBill.add(bill);
                                    }
                                }
                            }
                            break;
                        case "CC":
                            if (p instanceof ConvenientCard) {
                                ConvenientCard tempCC = (ConvenientCard) p;
                                if (tempCC.getIdCard().getCardNumber() == Integer.parseInt(arr[4])) {
                                    if (!tempCC.pay(Double.parseDouble(arr[1]))) {
                                        Bill bill = new Bill(Integer.parseInt(arr[0]), Double.parseDouble(arr[1]),
                                                arr[2]);
                                        unsuccessfulBill.add(bill);
                                    }
                                }

                            }
                            break;
                    }

                }

            }

            myReader.close();

        } catch (FileNotFoundException e) {

        }
        return unsuccessfulBill;
    }

    // Requirement 8
    public ArrayList<BankAccount> getLargestPaymentByBA(String path) {
        ArrayList<BankAccount> largestPayments = new ArrayList<BankAccount>();
        Hashtable<String, Double> baPayments = new Hashtable<String, Double>();
        ArrayList<Bill> unsuccessfulBill = getUnsuccessfulTransactions(path);
        double maxAmount = 0;
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] arr = data.split(",");
                Bill bill = new Bill(Integer.parseInt(arr[0]), Double.parseDouble(arr[1]), arr[2]);
                if (!unsuccessfulBill.contains(bill)) {
                    if (arr[3].equals("BA")) {
                        if (baPayments.get(arr[4]) == null)
                            baPayments.put(arr[4], Double.parseDouble(arr[1]));
                        else
                            baPayments.put(arr[4], baPayments.get(arr[4]) + Double.parseDouble(arr[1]));

                    }
                }

            }

            myReader.close();

        } catch (FileNotFoundException e) {

        }
        Set<String> keys = baPayments.keySet();
        ArrayList<String> baNumber = new ArrayList<String>();

        for (String key : keys) {
            if (baPayments.get(key) > maxAmount)
                maxAmount = baPayments.get(key);
        }

        for (String key : keys) {
            if (baPayments.get(key) == maxAmount) {
                baNumber.add(key);
            }
        }

        for (String ba : baNumber) {
            for (Payment p : paymentObjects) {
                if (p instanceof BankAccount) {
                    BankAccount temp = (BankAccount) p;
                    if (temp.getNumber() == Integer.parseInt(ba)) {
                        largestPayments.add(temp);
                        break;
                    }

                }
            }
        }

        return largestPayments;
    }

    // Requirement 9
    public void processTransactionWithDiscount(String path) {
        // code here
    }
}
