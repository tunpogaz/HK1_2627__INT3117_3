import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MovieTicket {
    public static String checkTicket(int age, double time) {
        if (age < 0 || age > 120 || time < 0.0 || time >= 24.0) {
            return "Invalid";
        }
        // Lỗi cấy vào: thay vì <= nhầm thành < ở điều kiện của age
        if (age >= 0 && age < 12) {
            return "Ve tre em";
        }
        // Lỗi cấy vào: thay vì <= nhầm thành < ở điều kiện của time
        if (age >= 13 && age <= 120 && time >= 0.0 && time < 17.0) {
            return "Ve tieu chuan ban ngay";
        }
        if (age >= 13 && age <= 120 && time > 17.0 && time < 24.0) {
            return "Ve tieu chuan ban dem";
        }
        return "Khong xac dinh";
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("testcase_ticket.txt"));
            int tests = 0;
            while (scanner.hasNextInt()) {
                int age = scanner.nextInt();
                double time = scanner.nextDouble();
                String expectOut = scanner.nextLine().trim();
                tests++;

                String actualOut = checkTicket(age, time);
                
                System.out.print("Test #" + tests + ": ");
                System.out.print("Input: Age = " + age + ", Time = " + time + ", Expected: " + expectOut + ", Output: " + actualOut);
                
                if (actualOut.equals(expectOut)) {
                    System.out.println(", Verdict: success");
                } else {
                    System.out.println(", Verdict: fail");
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Loi: Khong tim thay file testcase_ticket.txt");
        }
    }
}