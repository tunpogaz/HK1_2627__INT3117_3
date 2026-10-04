import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class ShippingTest_DT {
    public static String calculateShippingMutated(double d, double w, int v) {
        // Lỗi 1: Nhầm d > 2000.0 thành d > 2500.0
        if (d < 0.0 || d > 2500.0 || w < 0.0 || w > 500.0 || v < 0 || v > 100000) {
            return "Invalid";
        }
        
        if (d >= 0.0 && d <= 15.0 && w >= 0.0 && w <= 5.0 && v >= 500 && v <= 100000) {
            return "Freeship";
        }
        
        // Lỗi 2: Nhầm d <= 50.0 thành d <= 20.0
        // Lỗi 3: Nhầm w <= 20.0 thành w <= 10.0
        if (d >= 0.0 && d <= 20.0 && w >= 0.0 && w <= 10.0 && v >= 200 && v <= 100000) {
            return "Standard Fee";
        }
        
        return "Surcharge Fee";
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("testcase_shipping_dt.txt"));
            scanner.useLocale(Locale.US);
            
            while (scanner.hasNext()) {
                String testId = scanner.next();
                String rule = scanner.next();
                double d = scanner.nextDouble();
                double w = scanner.nextDouble();
                int v = scanner.nextInt();
                String expectOut = scanner.nextLine().trim();

                String actualOut = calculateShippingMutated(d, w, v);
                String verdict = actualOut.equalsIgnoreCase(expectOut) ? "Passed" : "Failed";

                System.out.printf("%-7s, Rule: %-4s, Input: D=%-6.1f, W=%-5.1f, V=%-6d | Expected: %-13s | Output: %-13s | Verdict: %s%n",
                                testId, rule, d, w, v, expectOut, actualOut, verdict);
            }
            scanner.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Loi: Khong tim thay file testcase_shipping_dt.txt");
        }
    }
}