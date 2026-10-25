import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class CandidateTest_CFG {

    public static String evaluateCandidate(int e, double a, double s) {
        // 1c1: e < 0, 1c2: e > 40, 1c3: a < 0.0, 1c4: a > 100.0, 1c5: s < 0.0, 1c6: s > 100.0
        if (e < 0 || e > 40 || a < 0.0 || a > 100.0 || s < 0.0 || s > 100.0) {
            return "Invalid";
        }
        // 3c1: e >= 5, 3c2: a >= 85.0, 3c3: s >= 80.0
        if (e >= 5 && a >= 85.0 && s >= 80.0) {
            return "Senior";
        }
        // 5c1: e >= 2, 5c2: a >= 65.0, 5c3: s >= 60.0
        if (e >= 2 && a >= 65.0 && s >= 60.0) {
            return "Mid-level";
        }
        return "Junior";
    }

    // Cấy lỗi thử nghiệm vào mã nguồn
    public static String evaluateCandidateMutated(int e, double a, double s) {
        // Lỗi 1: Nhầm 'e > 40' thành 'e > 50' ở điều kiện kiểm tra hợp lệ
        if (e < 0 || e > 50 || a < 0.0 || a > 100.0 || s < 0.0 || s > 100.0) {
            return "Invalid";
        }
        
        // Lỗi 2: Nhầm 'a >= 85.0' thành 'a >= 95.0' ở điều kiện xếp loại Senior
        if (e >= 5 && a >= 95.0 && s >= 80.0) {
            return "Senior";
        }
        
        // Lỗi 3 & Lỗi 4: Nhầm 'e >= 2' thành 'e >= 4' và 's >= 60.0' thành 's >= 75.0' ở điều kiện xếp loại Mid-level
        if (e >= 4 && a >= 65.0 && s >= 75.0) {
            return "Mid-level";
        }
        
        return "Junior";
    }

    public static void main(String[] args) {
        boolean testCorrect = (args.length > 0 && args[0].equalsIgnoreCase("--correct"));
        String fileName = new File("testcase_candidate_cfg.txt").exists() ? "testcase_candidate_cfg.txt" : "testcase_candidate.txt";

        try {
            Scanner scanner = new Scanner(new File(fileName));
            scanner.useLocale(Locale.US);

            int total = 0;
            int failed = 0;

            if (testCorrect) {
                System.out.println("  KẾT QUẢ CHẠY KIỂM THỬ TRÊN MÃ NGUỒN CHUẨN HOÁ (ĐO ĐỘ PHỦ 100% NHÁNH)                                  ");
            } else {
                System.out.println("  KẾT QUẢ CHẠY KIỂM THỬ SAU KHI CẤY LỖI VÀO MÃ NGUỒN (BÀI 1: ĐÁNH GIÁ ỨNG VIÊN TUYỂN DỤNG)              ");
            }

            while (scanner.hasNext()) {
                String testId = scanner.next();
                String path = scanner.next();
                int e = scanner.nextInt();
                double a = scanner.nextDouble();
                double s = scanner.nextDouble();
                String expectOut = scanner.nextLine().trim();

                String actualOut = testCorrect ? evaluateCandidate(e, a, s) : evaluateCandidateMutated(e, a, s);
                String verdict = actualOut.equalsIgnoreCase(expectOut) ? "Passed" : "Failed";

                total++;
                if (verdict.equals("Failed")) failed++;

                System.out.printf("%-6s, Path: %-7s, Input: E=%-2d, A=%-5.1f, S=%-5.1f | Expected: %-9s | Output: %-9s | Verdict: %s%n",
                        testId, path, e, a, s, expectOut, actualOut, verdict);
            }
            scanner.close();

            System.out.printf("Tổng số ca kiểm thử: %d | Passed: %d | Failed: %d%n", total, (total - failed), failed);

        } catch (FileNotFoundException ex) {
            System.out.println("Lỗi: Không tìm thấy file " + fileName);
        }
    }
}
