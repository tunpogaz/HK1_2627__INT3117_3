import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CreditCard {
    public static String checkCreditCard(int c, double i) {
        // Lỗi cấy vào: thay vì < và > thì nhầm thành <= và >= ở điều kiện của i
        if (c < 300 || c > 850 || i <= 0.0 || i >= 500.0) {
            return "Invalid";
        }
        
        // Lỗi cấy vào: thay vì >= thì nhầm thành > ở điều kiện của i
        if (c >= 700 && c <= 850 && i > 20.0 && i <= 500.0) {
            return "Platinum";
        }
        
        // Lỗi cấy vào: thay vì <= thì nhầm thành < ở điều kiện của c
        if (c >= 600 && c < 699 && i >= 10.0 && i <= 500.0) {
            return "Gold";
        }
        
        return "Standard";
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("testcase_credit.txt"));
            int tests = 0;
            while (scanner.hasNextInt()) {
                int c = scanner.nextInt();
                double i = scanner.nextDouble();
                String expectOut = scanner.nextLine().trim();
                tests++;
                String actualOut = checkCreditCard(c, i);
                System.out.print("Test #" + tests + ": ");
                System.out.print("Input: C = " + c + ", I = " + i + ", Expected: " + expectOut + ", Output: " + actualOut);
                
                if (actualOut.equals(expectOut)) {
                    System.out.println(", Verdict: success");
                } else {
                    System.out.println(", Verdict: fail");
                }
            }
            scanner.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Loi: Khong tim thay file testcase_credit.txt");
        }
    }
}