import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class ShippingTest_CFG {

    public static String calculateShipping(double d, double w, int v) {
        // 1c1: d < 0.0, 1c2: d > 2000.0, 1c3: w < 0.0, 1c4: w > 500.0, 1c5: v < 0, 1c6: v > 100000
        if (d < 0.0 || d > 2000.0 || w < 0.0 || w > 500.0 || v < 0 || v > 100000) {
            return "Invalid";
        }
        // 3c1: d <= 15.0, 3c2: w <= 5.0, 3c3: v >= 500
        if (d <= 15.0 && w <= 5.0 && v >= 500) {
            return "Freeship";
        }
        // 5c1: d <= 50.0, 5c2: w <= 20.0, 5c3: v >= 200
        if (d <= 50.0 && w <= 20.0 && v >= 200) {
            return "Standard Fee";
        }
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
        boolean testCorrect = (args.length > 0 && args[0].equalsIgnoreCase("--correct"));
        String fileName = new File("testcase_shipping_cfg.txt").exists() ? "testcase_shipping_cfg.txt" : "testcase_shipping.txt";

        try {
            Scanner scanner = new Scanner(new File(fileName));
            scanner.useLocale(Locale.US);

            int total = 0;
            int failed = 0;

            if (testCorrect) {
                System.out.println("  KẾT QUẢ CHẠY KIỂM THỬ TRÊN MÃ NGUỒN CHUẨN HOÁ (ĐO ĐỘ PHỦ 100% NHÁNH)                                    ");
            } else {
                System.out.println("  KẾT QUẢ CHẠY KIỂM THỬ SAU KHI CẤY LỖI VÀO MÃ NGUỒN (BÀI 2: HỆ THỐNG TÍNH PHÍ VẬN CHUYỂN)                ");
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

            System.out.printf("Tổng số ca kiểm thử: %d | Passed: %d | Failed: %d%n", total, (total - failed), failed);
            
        } catch (FileNotFoundException ex) {
            System.out.println("Lỗi: Không tìm thấy file " + fileName);
        }
    }
}
