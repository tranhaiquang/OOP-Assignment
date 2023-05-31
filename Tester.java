import java.io.*;
import java.util.*;

public class Tester {
    public static void main(String[] args) {
        try {
            File myObj = new File("data\\PaymentInformation.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] arr = data.split(",");
                if (arr.length > 1) {
                   System.out.println("Bank Account " + arr[0] + " " + arr[1]);
                } else {
                    if (arr[0].length() == 7) {
                        System.out.println("EWallet " + arr[0]);
                    } else {
                        System.out.println("CC " + arr[0]);

                        }

                    }

                
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public static int getYear(String dob) {
        return Integer.valueOf(dob.substring(dob.length() - 4));
    }
}
