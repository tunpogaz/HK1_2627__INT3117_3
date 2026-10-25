import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

public class ShippingTest_CFG {

    // Tập hợp theo dõi các nhánh điều kiện đã được thực thi
    private static Set<String> coveredBranches = new HashSet<>();
    private static Set<Integer> coveredLines = new HashSet<>();

    public static String calculateShipping(double d, double w, int v) {
        coveredLines.add(1);
        // 1c1
        if (d < 0.0) { coveredBranches.add("1c1_T"); coveredLines.add(2); return "Invalid"; } else { coveredBranches.add("1c1_F"); }
        // 1c2
        if (d > 2000.0) { coveredBranches.add("1c2_T"); coveredLines.add(2); return "Invalid"; } else { coveredBranches.add("1c2_F"); }
        // 1c3
        if (w < 0.0) { coveredBranches.add("1c3_T"); coveredLines.add(2); return "Invalid"; } else { coveredBranches.add("1c3_F"); }
        // 1c4
        if (w > 500.0) { coveredBranches.add("1c4_T"); coveredLines.add(2); return "Invalid"; } else { coveredBranches.add("1c4_F"); }
        // 1c5
        if (v < 0) { coveredBranches.add("1c5_T"); coveredLines.add(2); return "Invalid"; } else { coveredBranches.add("1c5_F"); }
        // 1c6
        if (v > 100000) { coveredBranches.add("1c6_T"); coveredLines.add(2); return "Invalid"; } else { coveredBranches.add("1c6_F"); }

        coveredLines.add(3);
        // 3c1..3c3
        boolean c3_1 = (d <= 15.0);
        if (c3_1) coveredBranches.add("3c1_T"); else coveredBranches.add("3c1_F");

        if (c3_1) {
            boolean c3_2 = (w <= 5.0);
            if (c3_2) coveredBranches.add("3c2_T"); else coveredBranches.add("3c2_F");
            if (c3_2) {
                boolean c3_3 = (v >= 500);
                if (c3_3) coveredBranches.add("3c3_T"); else coveredBranches.add("3c3_F");
                if (c3_3) {
                    coveredLines.add(4);
                    return "Freeship";
                }
            }
        }

        coveredLines.add(5);
        // 5c1..5c3
        boolean c5_1 = (d <= 50.0);
        if (c5_1) coveredBranches.add("5c1_T"); else coveredBranches.add("5c1_F");

        if (c5_1) {
            boolean c5_2 = (w <= 20.0);
            if (c5_2) coveredBranches.add("5c2_T"); else coveredBranches.add("5c2_F");
            if (c5_2) {
                boolean c5_3 = (v >= 200);
                if (c5_3) coveredBranches.add("5c3_T"); else coveredBranches.add("5c3_F");
                if (c5_3) {
                    coveredLines.add(6);
                    return "Standard Fee";
                }
            }
        }

        coveredLines.add(7);
        return "Surcharge Fee";
    }

    // Cấy lỗi thử nghiệm vào mã nguồn
    public static String calculateShippingMutated(double d, double w, int v) {
        // Lỗi 1: Nhầm 'd > 2000.0' thành 'd > 2500.0' ở điều kiện kiểm tra hợp lệ
        if (d < 0.0 || d > 2500.0 || w < 0.0 || w > 500.0 || v < 0 || v > 100000) {
            return "Invalid";
        }
        
        // Lỗi 2: Nhầm 'v >= 500' thành 'v >= 2000' ở điều kiện xét Freeship
        if (d <= 15.0 && w <= 5.0 && v >= 2000) {
            return "Freeship";
        }
        
        // Lỗi 3 & Lỗi 4: Nhầm 'd <= 50.0' thành 'd <= 30.0' và 'w <= 20.0' thành 'w <= 10.0' ở điều kiện xét Standard Fee
        if (d <= 30.0 && w <= 10.0 && v >= 200) {
            return "Standard Fee";
        }
        
        return "Surcharge Fee";
    }

    public static void main(String[] args) {
        boolean testCorrect = (args.length > 0 && (args[0].equalsIgnoreCase("--correct") || args[0].equalsIgnoreCase("--coverage")));
        String fileName = new File("testcase_shipping_cfg.txt").exists() ? "testcase_shipping_cfg.txt" : "testcase_shipping.txt";

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
                System.out.println("                  KẾT QUẢ CHẠY KIỂM THỬ SAU KHI CẤY LỖI VÀO MÃ NGUỒN (BÀI 2: VẬN CHUYỂN)                  ");
                System.out.println("==========================================================================================================");
            }
            
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
                if (verdict.equals("Failed")) failed++;

                System.out.printf("%-7s, Path: %-7s, Input: D=%-6.1f, W=%-5.1f, V=%-6d | Expected: %-13s | Output: %-13s | Verdict: %s%n",
                        testId, path, d, w, v, expectOut, actualOut, verdict);
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
        int totalLines = 7;
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
            "ShippingTest_CFG.java", totalLines, execLines, lineCoverPct, totalBranches, takenBranches, branchCoverPct);
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.printf("%-30s %8d %8d %7d%% %12d %8d %7d%%%n", 
            "TOTAL", totalLines, execLines, lineCoverPct, totalBranches, takenBranches, branchCoverPct);
        System.out.println("----------------------------------------------------------------------------------------");
    }
}
