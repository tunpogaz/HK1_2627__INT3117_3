import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class ShippingTest {

    public static String calculateShippingMutated(double d, double w, int v) {
        if (d < 0.0 || d > 2000.0 || w < 0.0 || w > 500.0 || v < 0 || v > 100000) {
            return "Invalid";
        }
        if (d >= 0.0 && d <= 15.0 && w >= 0.0 && w <= 5.0 && v >= 500 && v <= 100000) {
            return "Freeship";
        }
        // LỖI CẤY VÀO: Nhầm khoảng cách d <= 50.0 thành d <= 20.0
        if (d >= 0.0 && d <= 20.0 && w >= 0.0 && w <= 20.0 && v >= 200 && v <= 100000) {
            return "Standard Fee";
        }
        return "Surcharge Fee";
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("testcase_shipping.txt"));
            scanner.useLocale(Locale.US);
            int tests = 0;
            
            while (scanner.hasNextDouble()) {
                double d = scanner.nextDouble();
                double w = scanner.nextDouble();
                int v = scanner.nextInt();
                String expectOut = scanner.nextLine().trim();
                tests++;

                String actualOut = calculateShippingMutated(d, w, v);
                
                System.out.print("Test T" + tests + " (P" + tests + "): Input: D=" + d + ", W=" + w + ", V=" + v +
                                ", Expected: " + expectOut + ", Output: " + actualOut);
                
                if (actualOut.equals(expectOut)) {
                    System.out.println(", Result: Passed");
                } else {
                    System.out.println(", Result: Failed");
                }
            }
            scanner.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Loi: Khong tim thay file testcase_shipping.txt");
        }
    }
}