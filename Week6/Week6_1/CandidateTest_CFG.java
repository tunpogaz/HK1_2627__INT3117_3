import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

public class CandidateTest_CFG {

    // Tập hợp theo dõi các nhánh điều kiện đã được thực thi
    private static Set<String> coveredBranches = new HashSet<>();
    private static Set<Integer> coveredLines = new HashSet<>();

    public static String evaluateCandidate(int e, double a, double s) {
        coveredLines.add(1);
        // 1c1
        if (e < 0) { coveredBranches.add("1c1_T"); coveredLines.add(2); return "Invalid"; } else { coveredBranches.add("1c1_F"); }
        // 1c2
        if (e > 40) { coveredBranches.add("1c2_T"); coveredLines.add(2); return "Invalid"; } else { coveredBranches.add("1c2_F"); }
        // 1c3
        if (a < 0.0) { coveredBranches.add("1c3_T"); coveredLines.add(2); return "Invalid"; } else { coveredBranches.add("1c3_F"); }
        // 1c4
        if (a > 100.0) { coveredBranches.add("1c4_T"); coveredLines.add(2); return "Invalid"; } else { coveredBranches.add("1c4_F"); }
        // 1c5
        if (s < 0.0) { coveredBranches.add("1c5_T"); coveredLines.add(2); return "Invalid"; } else { coveredBranches.add("1c5_F"); }
        // 1c6
        if (s > 100.0) { coveredBranches.add("1c6_T"); coveredLines.add(2); return "Invalid"; } else { coveredBranches.add("1c6_F"); }

        coveredLines.add(3);
        // 3c1..3c3
        boolean c3_1 = (e >= 5);
        if (c3_1) coveredBranches.add("3c1_T"); else coveredBranches.add("3c1_F");

        if (c3_1) {
            boolean c3_2 = (a >= 85.0);
            if (c3_2) coveredBranches.add("3c2_T"); else coveredBranches.add("3c2_F");
            if (c3_2) {
                boolean c3_3 = (s >= 80.0);
                if (c3_3) coveredBranches.add("3c3_T"); else coveredBranches.add("3c3_F");
                if (c3_3) {
                    coveredLines.add(4);
                    return "Senior";
                }
            }
        }

        coveredLines.add(5);
        // 5c1..5c3
        boolean c5_1 = (e >= 2);
        if (c5_1) coveredBranches.add("5c1_T"); else coveredBranches.add("5c1_F");

        if (c5_1) {
            boolean c5_2 = (a >= 65.0);
            if (c5_2) coveredBranches.add("5c2_T"); else coveredBranches.add("5c2_F");
            if (c5_2) {
                boolean c5_3 = (s >= 60.0);
                if (c5_3) coveredBranches.add("5c3_T"); else coveredBranches.add("5c3_F");
                if (c5_3) {
                    coveredLines.add(6);
                    return "Mid-level";
                }
            }
        }

        coveredLines.add(7);
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
        boolean testCorrect = (args.length > 0 && (args[0].equalsIgnoreCase("--correct") || args[0].equalsIgnoreCase("--coverage")));
        String fileName = new File("testcase_candidate_cfg.txt").exists() ? "testcase_candidate_cfg.txt" : "testcase_candidate.txt";

        try {
            Scanner scanner = new Scanner(new File(fileName));
            scanner.useLocale(Locale.US);

            int total = 0;
            int failed = 0;

            if (testCorrect) {
                System.out.println("==========================================================================================================");
                System.out.println("                  KẾT QUẢ CHẠY KIỂM THỬ TRÊN MÃ NGUỒN CHUẨN HOÁ (ĐO ĐỘ PHỦ 100% NHÁNH)                    ");
                System.out.println("==========================================================================================================");
            } else {
                System.out.println("==========================================================================================================");
                System.out.println("                  KẾT QUẢ CHẠY KIỂM THỬ SAU KHI CẤY LỖI VÀO MÃ NGUỒN (BÀI 1: ỨNG VIÊN)                   ");
                System.out.println("==========================================================================================================");
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

            System.out.println("----------------------------------------------------------------------------------------------------------");
            System.out.printf("Tổng số ca kiểm thử: %d | Passed: %d | Failed: %d%n", total, (total - failed), failed);
            System.out.println("----------------------------------------------------------------------------------------------------------");

            if (testCorrect) {
                printCoverageReport();
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Lỗi: Không tìm thấy file " + fileName);
        }
    }

    private static void printCoverageReport() {
        int totalLines = 7; // Dòng 1, 2, 3, 4, 5, 6, 7
        int execLines = coveredLines.size();
        int lineCoverPct = (execLines * 100) / totalLines;

        int totalBranches = 24; // 12 điều kiện con * 2 (True/False)
        int takenBranches = coveredBranches.size();
        int branchCoverPct = (takenBranches * 100) / totalBranches;

        System.out.println();
        System.out.println("Đo độ phủ:");
        System.out.println("Sử dụng công cụ theo dõi dòng điều khiển, đo được độ phủ của code:");
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("                                Java Code Coverage Report                               ");
        System.out.println("Directory: .");
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.printf("%-30s %8s %8s %8s %12s %8s %8s%n", "File", "Lines", "Exec", "Cover", "Branches", "Taken", "Cover");
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.printf("%-30s %8d %8d %7d%% %12d %8d %7d%%%n", 
            "CandidateTest_CFG.java", totalLines, execLines, lineCoverPct, totalBranches, takenBranches, branchCoverPct);
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.printf("%-30s %8d %8d %7d%% %12d %8d %7d%%%n", 
            "TOTAL", totalLines, execLines, lineCoverPct, totalBranches, takenBranches, branchCoverPct);
        System.out.println("----------------------------------------------------------------------------------------");
    }
}
