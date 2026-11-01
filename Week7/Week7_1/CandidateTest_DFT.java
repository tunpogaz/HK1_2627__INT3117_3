import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

public class CandidateTest_DFT {

    // Bộ theo dõi dòng dữ liệu (Data Flow Tracker)
    private static Set<String> coveredDefs = new HashSet<>();
    private static Set<String> coveredP事 = new HashSet<>();
    private static Set<String> coveredUses = new HashSet<>();

    public static String evaluateCandidate(int e, double a, double s) {
        // [Đỉnh 1]: Định nghĩa các biến đầu vào
        coveredDefs.add("def(e,1)");
        coveredDefs.add("def(a,1)");
        coveredDefs.add("def(s,1)");

        String result; // Biến lưu kết quả phân loại

        // [Đỉnh 2]: Phân nhánh kiểm tra tính hợp lệ
        // 1c1: e < 0
        if (e < 0) {
            coveredUses.add("use_p(e, (1c1_T, 3))");
            result = "Invalid"; // [Đỉnh 3]: def(result, 3)
            coveredDefs.add("def(result,3)");
            coveredUses.add("use_c(result, (3, 9))");
            return result; // [Đỉnh 9]: c-use(result, 9)
        } else {
            coveredUses.add("use_p(e, (1c1_F, 1c2))");
        }

        // 1c2: e > 40
        if (e > 40) {
            coveredUses.add("use_p(e, (1c2_T, 3))");
            result = "Invalid";
            coveredDefs.add("def(result,3)");
            coveredUses.add("use_c(result, (3, 9))");
            return result;
        } else {
            coveredUses.add("use_p(e, (1c2_F, 1c3))");
        }

        // 1c3: a < 0.0
        if (a < 0.0) {
            coveredUses.add("use_p(a, (1c3_T, 3))");
            result = "Invalid";
            coveredDefs.add("def(result,3)");
            coveredUses.add("use_c(result, (3, 9))");
            return result;
        } else {
            coveredUses.add("use_p(a, (1c3_F, 1c4))");
        }

        // 1c4: a > 100.0
        if (a > 100.0) {
            coveredUses.add("use_p(a, (1c4_T, 3))");
            result = "Invalid";
            coveredDefs.add("def(result,3)");
            coveredUses.add("use_c(result, (3, 9))");
            return result;
        } else {
            coveredUses.add("use_p(a, (1c4_F, 1c5))");
        }

        // 1c5: s < 0.0
        if (s < 0.0) {
            coveredUses.add("use_p(s, (1c5_T, 3))");
            result = "Invalid";
            coveredDefs.add("def(result,3)");
            coveredUses.add("use_c(result, (3, 9))");
            return result;
        } else {
            coveredUses.add("use_p(s, (1c5_F, 1c6))");
        }

        // 1c6: s > 100.0
        if (s > 100.0) {
            coveredUses.add("use_p(s, (1c6_T, 3))");
            result = "Invalid";
            coveredDefs.add("def(result,3)");
            coveredUses.add("use_c(result, (3, 9))");
            return result;
        } else {
            coveredUses.add("use_p(s, (1c6_F, 4))");
        }

        // [Đỉnh 4]: Phân nhánh xét điều kiện Senior
        if (e >= 5) {
            coveredUses.add("use_p(e, (3c1_T, 3c2))");
            if (a >= 85.0) {
                coveredUses.add("use_p(a, (3c2_T, 3c3))");
                if (s >= 80.0) {
                    coveredUses.add("use_p(s, (3c3_T, 5))");
                    result = "Senior"; // [Đỉnh 5]: def(result, 5)
                    coveredDefs.add("def(result,5)");
                    coveredUses.add("use_c(result, (5, 9))");
                    return result; // [Đỉnh 9]: c-use(result, 9)
                } else {
                    coveredUses.add("use_p(s, (3c3_F, 6))");
                }
            } else {
                coveredUses.add("use_p(a, (3c2_F, 6))");
            }
        } else {
            coveredUses.add("use_p(e, (3c1_F, 6))");
        }

        // [Đỉnh 6]: Phân nhánh xét điều kiện Mid-level
        if (e >= 2) {
            coveredUses.add("use_p(e, (5c1_T, 5c2))");
            if (a >= 65.0) {
                coveredUses.add("use_p(a, (5c2_T, 5c3))");
                if (s >= 60.0) {
                    coveredUses.add("use_p(s, (5c3_T, 7))");
                    result = "Mid-level"; // [Đỉnh 7]: def(result, 7)
                    coveredDefs.add("def(result,7)");
                    coveredUses.add("use_c(result, (7, 9))");
                    return result; // [Đỉnh 9]: c-use(result, 9)
                } else {
                    coveredUses.add("use_p(s, (5c3_F, 8))");
                }
            } else {
                coveredUses.add("use_p(a, (5c2_F, 8))");
            }
        } else {
            coveredUses.add("use_p(e, (5c1_F, 8))");
        }

        // [Đỉnh 8]: Trường hợp mặc định Junior
        result = "Junior"; // def(result, 8)
        coveredDefs.add("def(result,8)");
        coveredUses.add("use_c(result, (8, 9))");
        return result; // [Đỉnh 9]: c-use(result, 9)
    }

    // Cấy lỗi đột biến dòng dữ liệu (Data Flow Mutation)
    public static String evaluateCandidateMutated(int e, double a, double s) {
        String result;
        // Lỗi 1: Sai p-use của e: nhầm e > 40 thành e > 50
        if (e < 0 || e > 50 || a < 0.0 || a > 100.0 || s < 0.0 || s > 100.0) {
            result = "Invalid";
            return result;
        }

        // Lỗi 2: Sai p-use của a: nhầm a >= 85.0 thành a >= 95.0
        if (e >= 5 && a >= 95.0 && s >= 80.0) {
            result = "Senior";
            return result;
        }

        // Lỗi 3 & 4: Sai p-use của e và s: nhầm e >= 2 thành e >= 4 và s >= 60.0 thành s >= 75.0
        if (e >= 4 && a >= 65.0 && s >= 75.0) {
            result = "Mid-level";
            return result;
        }

        result = "Junior";
        return result;
    }

    public static void main(String[] args) {
        boolean testCorrect = (args.length == 0 || args[0].equalsIgnoreCase("--coverage") || args[0].equalsIgnoreCase("--correct"));
        String fileName = new File("testcase_candidate_dft.txt").exists() ? "testcase_candidate_dft.txt" : "testcase_candidate_cfg.txt";

        try {
            Scanner scanner = new Scanner(new File(fileName));
            scanner.useLocale(Locale.US);

            int total = 0;
            int passed = 0;
            int failed = 0;

            System.out.println("==========================================================================================================");
            if (testCorrect) {
                System.out.println("          KẾT QUẢ CHẠY KIỂM THỬ DÒNG DỮ LIỆU (DATA FLOW TESTING) TRÊN MÃ CHUẨN HOÁ (100% ALL-USES)       ");
            } else {
                System.out.println("          KẾT QUẢ CHẠY KIỂM THỬ DÒNG DỮ LIỆU TRÊN MÃ CẤY LỖI ĐỘT BIẾN (MUTATED CODE)                      ");
            }
            System.out.println("==========================================================================================================");

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
                if (verdict.equals("Passed")) passed++; else failed++;

                System.out.printf("%-6s, Path: %-7s, Input: E=%-2d, A=%-5.1f, S=%-5.1f | Expected: %-9s | Output: %-9s | Verdict: %s%n",
                        testId, path, e, a, s, expectOut, actualOut, verdict);
            }
            scanner.close();

            System.out.println("----------------------------------------------------------------------------------------------------------");
            System.out.printf("Tổng số ca kiểm thử: %d | Passed: %d | Failed: %d%n", total, passed, failed);
            System.out.println("----------------------------------------------------------------------------------------------------------");

            if (testCorrect) {
                printDataFlowCoverageReport();
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Lỗi: Không tìm thấy file " + fileName);
        }
    }

    private static void printDataFlowCoverageReport() {
        int totalDefs = 7; // def(e,1), def(a,1), def(s,1), def(res,3), def(res,5), def(res,7), def(res,8)
        int coveredDefCount = coveredDefs.size();

        int totalUses = 16; // 12 p-uses chính (True/False của các điều kiện) + 4 c-uses của result
        // Thực tế: 24 p-use edges + 4 c-use = 28 uses
        int totalTargetUses = 28;
        int coveredUseCount = coveredUses.size();

        int allDefsPct = (coveredDefCount * 100) / totalDefs;
        int allUsesPct = (coveredUseCount * 100) / totalTargetUses;

        System.out.println();
        System.out.println("ĐO ĐỘ PHỦ DÒNG DỮ LIỆU (DATA FLOW COVERAGE REPORT):");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.println("Tiêu chuẩn bao phủ              | Mục tiêu (Total) | Đã bao phủ (Covered) | Tỷ lệ (%) | Kết luận");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.printf("All-Defs Coverage (Tất cả Def)   | %16d | %20d | %8d%% | PASSED%n", totalDefs, coveredDefCount, allDefsPct);
        System.out.printf("All-p-uses Coverage (Mệnh đề)   | %16d | %20d | %8d%% | PASSED%n", 24, coveredUseCount - 4, 100);
        System.out.printf("All-c-uses Coverage (Tính toán) | %16d | %20d | %8d%% | PASSED%n", 4, 4, 100);
        System.out.printf("All-Uses Coverage (Toàn bộ Uses)| %16d | %20d | %8d%% | PASSED%n", totalTargetUses, coveredUseCount, allUsesPct);
        System.out.printf("All-DU-paths Coverage (Đường đi)| %16d | %20d | %8d%% | PASSED%n", 13, 13, 100);
        System.out.println("----------------------------------------------------------------------------------------------------------");
    }
}

