package io;

import model.Customer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Xử lý đọc và ghi tập tin danh sách khách hàng.
 * Định dạng lưu trữ: CSV chuẩn UTF-8 (ID,Name,Gender,Age,Blood).
 * Sinh viên: Phạm Ngọc Lang | MSSV: 25765251 | Lớp: 21BVL
 */
public class FileHandler {

    /**
     * Đọc danh sách khách hàng từ file text.
     */
    public static List<Customer> readFromFile(String filePath) {
        List<Customer> list = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("[Loi I/O] Tap tin khong ton tai: " + filePath);
            return list;
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                Customer customer = Customer.fromCsvLine(line);
                if (customer != null) {
                    list.add(customer);
                }
            }
        } catch (IOException e) {
            System.err.println("[Loi I/O] Khong the doc tap tin " + filePath + ": " + e.getMessage());
        }
        return list;
    }

    /**
     * Ghi danh sách khách hàng ra file text.
     */
    public static boolean saveToFile(String filePath, List<Customer> list) {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            // Ghi dòng chú thích tiêu đề cột
            writer.write("# ID,Name,Gender,Age,Blood");
            writer.newLine();

            if (list != null) {
                for (Customer c : list) {
                    writer.write(c.toCsvLine());
                    writer.newLine();
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("[Loi I/O] Khong the ghi tap tin " + filePath + ": " + e.getMessage());
            return false;
        }
    }
}
