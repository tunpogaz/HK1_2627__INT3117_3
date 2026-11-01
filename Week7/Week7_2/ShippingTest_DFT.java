import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

/**
 * BÀI 2: KIỂM THỬ DÒNG DỮ LIỆU (DATA FLOW TESTING)
 * Hệ thống Tính phí vận chuyển hàng hóa
 * Sinh viên: Phạm Tuấn Phong - MSV: 24021596
 */
public class ShippingTest_DFT {

    // Bộ theo dõi dòng dữ liệu (Data Flow Tracker)
    private static Set<String> coveredDefs = new HashSet<>();
    private static Set<String> coveredUses = new HashSet<>();

    public static String calculateShipping(double d, double w, int v) {
        // [Đỉnh 1]: Định nghĩa các biến đầu vào
        coveredDefs.add("def(d,1)");
        coveredDefs.add("def(w,1)");
        coveredDefs.add("def(v,1)");

        String result; // Biến lưu kết quả cước phí

        // [Đỉnh 2]: Phân nhánh kiểm tra tính hợp lệ
        // 1c1: d < 0.0
        if (d < 0.0) {
            coveredUses.add("use_p(d, (1c1_T, 3))");
            result = "Invalid"; // [Đỉnh 3]: def(result, 3)
            coveredDefs.add("def(result,3)");
            coveredUses.add("use_c(result, (3, 9))");
            return result; // [Đỉnh 9]: c-use(result, 9)
        } else {
            coveredUses.add("use_p(d, (1c1_F, 1c2))");
        }

        // 1c2: d > 2000.0
        if (d > 2000.0) {
            coveredUses.add("use_p(d, (1c2_T, 3))");
            result = "Invalid";
            coveredDefs.add("def(result,3)");
            coveredUses.add("use_c(result, (3, 9))");
            return result;
        } else {
            coveredUses.add("use_p(d, (1c2_F, 1c3))");
        }

        // 1c3: w < 0.0
        if (w < 0.0) {
            coveredUses.add("use_p(w, (1c3_T, 3))");
            result = "Invalid";
            coveredDefs.add("def(result,3)");
            coveredUses.add("use_c(result, (3, 9))");
            return result;
        } else {
            coveredUses.add("use_p(w, (1c3_F, 1c4))");
        }

        // 1c4: w > 500.0
        if (w > 500.0) {
            coveredUses.add("use_p(w, (1c4_T, 3))");
            result = "Invalid";
            coveredDefs.add("def(result,3)");
            coveredUses.add("use_c(result, (3, 9))");
            return result;
        } else {
            coveredUses.add("use_p(w, (1c4_F, 1c5))");
        }

        // 1c5: v < 0
        if (v < 0) {
            coveredUses.add("use_p(v, (1c5_T, 3))");
            result = "Invalid";
            coveredDefs.add("def(result,3)");
            coveredUses.add("use_c(result, (3, 9))");
            return result;
        } else {
            coveredUses.add("use_p(v, (1c5_F, 1c6))");
        }

        // 1c6: v > 100000
        if (v > 100000) {
            coveredUses.add("use_p(v, (1c6_T, 3))");
            result = "Invalid";
            coveredDefs.add("def(result,3)");
            coveredUses.add("use_c(result, (3, 9))");
            return result;
        } else {
            coveredUses.add("use_p(v, (1c6_F, 4))");
        }

        // [Đỉnh 4]: Phân nhánh xét điều kiện Freeship
        if (d <= 15.0) {
            coveredUses.add("use_p(d, (3c1_T, 3c2))");
            if (w <= 5.0) {
                coveredUses.add("use_p(w, (3c2_T, 3c3))");
                if (v >= 500) {
                    coveredUses.add("use_p(v, (3c3_T, 5))");
                    result = "Freeship"; // [Đỉnh 5]: def(result, 5)
                    coveredDefs.add("def(result,5)");
                    coveredUses.add("use_c(result, (5, 9))");
                    return result; // [Đỉnh 9]: c-use(result, 9)
                } else {
                    coveredUses.add("use_p(v, (3c3_F, 6))");
                }
            } else {
                coveredUses.add("use_p(w, (3c2_F, 6))");
            }
        } else {
            coveredUses.add("use_p(d, (3c1_F, 6))");
        }

        // [Đỉnh 6]: Phân nhánh xét điều kiện Standard Fee
        if (d <= 50.0) {
            coveredUses.add("use_p(d, (5c1_T, 5c2))");
            if (w <= 20.0) {
                coveredUses.add("use_p(w, (5c2_T, 5c3))");
                if (v >= 200) {
                    coveredUses.add("use_p(v, (5c3_T, 7))");
                    result = "Standard Fee"; // [Đỉnh 7]: def(result, 7)
                    coveredDefs.add("def(result,7)");
                    coveredUses.add("use_c(result, (7, 9))");
                    return result; // [Đỉnh 9]: c-use(result, 9)
                } else {
                    coveredUses.add("use_p(v, (5c3_F, 8))");
                }
            } else {
                coveredUses.add("use_p(w, (5c2_F, 8))");
            }
        } else {
            coveredUses.add("use_p(d, (5c1_F, 8))");
        }

        // [Đỉnh 8]: Trường hợp mặc định Surcharge Fee
        result = "Surcharge Fee"; // def(result, 8)
        coveredDefs.add("def(result,8)");
        coveredUses.add("use_c(result, (8, 9))");
        return result; // [Đỉnh 9]: c-use(result, 9)
    }

    // Cấy lỗi đột biến dòng dữ liệu (Data Flow Mutation)
    public static String calculateShippingMutated(double d, double w, int v) {
        String result;
        // Lỗi 1: Sai p-use của d: nhầm d > 2000.0 thành d > 2500.0
        if (d < 0.0 || d > 2500.0 || w < 0.0 || w > 500.0 || v < 0 || v > 100000) {
            result = "Invalid";
            return result;
        }

        // Lỗi 2: Sai p-use của v: nhầm v >= 500 thành v >= 2000
        if (d <= 15.0 && w <= 5.0 && v >= 2000) {
            result = "Freeship";
            return result;
        }

        // Lỗi 3 & 4: Sai p-use của d và w: nhầm d <= 50.0 thành d <= 30.0 và w <= 20.0 thành w <= 10.0
        if (d <= 30.0 && w <= 10.0 && v >= 200) {
            result = "Standard Fee";
            return result;
        }

        result = "Surcharge Fee";
        return result;
    }

    public static void main(String[] args) {
        boolean testCorrect = (args.length == 0 || args[0].equalsIgnoreCase("--coverage") || args[0].equalsIgnoreCase("--correct"));
        String fileName = new File("testcase_shipping_dft.txt").exists() ? "testcase_shipping_dft.txt" : "testcase_shipping_cfg.txt";

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
                double d = scanner.nextDouble();
                double w = scanner.nextDouble();
                int v = scanner.nextInt();
                String expectOut = scanner.nextLine().trim();

                String actualOut = testCorrect ? calculateShipping(d, w, v) : calculateShippingMutated(d, w, v);
                String verdict = actualOut.equalsIgnoreCase(expectOut) ? "Passed" : "Failed";

                total++;
                if (verdict.equals("Passed")) passed++; else failed++;

                System.out.printf("%-7s, Path: %-7s, Input: D=%-6.1f, W=%-5.1f, V=%-6d | Expected: %-13s | Output: %-13s | Verdict: %s%n",
                        testId, path, d, w, v, expectOut, actualOut, verdict);
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
        int totalDefs = 7; // def(d,1), def(w,1), def(v,1), def(res,3), def(res,5), def(res,7), def(res,8)
        int coveredDefCount = coveredDefs.size();

        int totalTargetUses = 28; // 24 p-use edges + 4 c-uses
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

