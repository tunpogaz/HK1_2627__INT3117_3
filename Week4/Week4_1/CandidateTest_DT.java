import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class CandidateTest_DT {

    public static String evaluateCandidateMutated(int e, double a, double s) {
        // Lỗi 1: Nhầm e > 40 thành e > 50
        if (e < 0 || e > 50 || a < 0.0 || a > 100.0 || s < 0.0 || s > 100.0) {
            return "Invalid";
        }
        
        // Lỗi 2: Nhầm điều kiện kinh nghiệm e >= 5 thành e >= 15
        if (e >= 15 && e <= 40 && a >= 85.0 && a <= 100.0 && s >= 80.0 && s <= 100.0) {
            return "Senior";
        }
        
        // Lỗi 3: Nhầm điều kiện thuật toán a >= 65.0 thành a >= 80.0
        if (e >= 2 && e <= 40 && a >= 80.0 && a <= 100.0 && s >= 60.0 && s <= 100.0) {
            return "Mid-level";
        }
        
        return "Junior";
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("testcase_candidate_dt.txt"));
            scanner.useLocale(Locale.US);
            
            while (scanner.hasNext()) {
                String testId = scanner.next();
                String rule = scanner.next();
                int e = scanner.nextInt();
                double a = scanner.nextDouble();
                double s = scanner.nextDouble();
                String expectOut = scanner.nextLine().trim();

                String actualOut = evaluateCandidateMutated(e, a, s);
                String verdict = actualOut.equalsIgnoreCase(expectOut) ? "Passed" : "Failed";

                System.out.printf("%-7s, Rule: %-4s, Input: E=%-2d, A=%-5.1f, S=%-5.1f | Expected: %-9s | Output: %-9s | Verdict: %s%n",
                                testId, rule, e, a, s, expectOut, actualOut, verdict);
            }
            scanner.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Loi: Khong tim thay file testcase_candidate_dt.txt");
        }
    }
}