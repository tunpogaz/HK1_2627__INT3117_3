import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class CandidateTest {

    public static String evaluateCandidate(int e, double a, double s) {
        if (e < 0 || e > 40 || a < 0.0 || a > 100.0 || s < 0.0 || s > 100.0) {
            return "Invalid";
        }
        
        // Lỗi cấy vào: thay vì >= thì nhầm thành > ở điều kiện của a và s
        if (e >= 5 && e <= 40 && a > 85.0 && a <= 100.0 && s > 80.0 && s <= 100.0) {
            return "Senior";
        }
        
        // Lỗi cấy vào: thay vì >= thì nhầm thành > ở điều kiện của e
        if (e > 2 && e <= 40 && a >= 65.0 && a <= 100.0 && s >= 60.0 && s <= 100.0) {
            return "Mid-level";
        }
        
        return "Junior";
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("testcase_candidate.txt"));
            scanner.useLocale(Locale.US);
            int tests = 0;
            while (scanner.hasNextInt()) {
                int e = scanner.nextInt();
                double a = scanner.nextDouble();
                double s = scanner.nextDouble();
                String expectOut = scanner.nextLine().trim();
                tests++;

                String actualOut = evaluateCandidate(e, a, s);
                
                System.out.print("Test #" + tests + ": Input: E=" + e + ", A=" + a + ", S=" + s + 
                ", Expected: " + expectOut + ", Output: " + actualOut);
                
                if (actualOut.equals(expectOut)) {
                    System.out.println(", Result: success");
                } else {
                    System.out.println(", Result: fail");
                }
            }
            scanner.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Loi: Khong tim thay file testcase_candidate.txt");
        }
    }
}