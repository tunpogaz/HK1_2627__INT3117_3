import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CandidateTest {

    public static String evaluateCandidate(int e, int a, int s) {
        if (e < 0 || e > 40 || a < 0 || a > 100 || s < 0 || s > 100) {
            return "Invalid";
        }
        
        // LỖI CẤY VÀO 1: Nhầm a >= 85 thành a > 85
        // LỖI CẤY VÀO 2: Nhầm s >= 80 thành s > 80
        if (e >= 5 && e <= 40 && a > 85 && a <= 100 && s > 80 && s <= 100) {
            return "Senior";
        }
        
        // LỖI CẤY VÀO 3: Nhầm e >= 2 thành e > 2
        if (e > 2 && e <= 40 && a >= 65 && a <= 100 && s >= 60 && s <= 100) {
            return "Mid-level";
        }
        
        return "Junior";
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("testcase_candidate.txt"));
            int tests = 0;
            while (scanner.hasNextInt()) {
                int e = scanner.nextInt();
                int a = scanner.nextInt();
                int s = scanner.nextInt();
                String expectOut = scanner.nextLine().trim();
                tests++;

                String actualOut = evaluateCandidate(e, a, s);
                
                System.out.print("Test #" + tests + ": Input: E=" + e + ", A=" + a + ", S=" + s + 
                ", Expected: " + expectOut + " Output: " + actualOut);
                
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