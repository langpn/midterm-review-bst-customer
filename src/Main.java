import io.FileHandler;
import model.Customer;
import tree.BST;

import java.util.List;
import java.util.Scanner;

/**
 * Lớp điều khiển chính: Cung cấp giao diện CLI tương tác và bộ chạy demo tự động 9 câu.
 * Sinh viên: Phạm Ngọc Lang | MSSV: 25765251 | Lớp: 21BVL
 * Giảng viên hướng dẫn: Thầy Đỗ Hà Phương (dohaphuong@iuh.edu.vn)
 */
public class Main {
    private static final String DATA_FILE = "data/customers.txt";
    private static final String OUTPUT_FILE = "data/customers_output.txt";
    private static final BST bst = new BST();

    public static void main(String[] args) {
        if (args.length > 0 && "--demo".equalsIgnoreCase(args[0])) {
            runAutomatedDemo();
            return;
        }

        // Tự động nạp dữ liệu ban đầu nếu file tồn tại
        List<Customer> initialList = FileHandler.readFromFile(DATA_FILE);
        if (!initialList.isEmpty()) {
            for (Customer c : initialList) {
                bst.insert(c);
            }
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.print("Chon chuc nang (0-10): ");
            String input = scanner.nextLine().trim();

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap so tu 0 den 10!\n");
                continue;
            }

            switch (choice) {
                case 1:
                    handleCau1();
                    break;
                case 2:
                    handleCau2();
                    break;
                case 3:
                    handleCau3();
                    break;
                case 4:
                    handleCau4();
                    break;
                case 5:
                    handleCau5();
                    break;
                case 6:
                    handleCau6();
                    break;
                case 7:
                    handleCau7(scanner);
                    break;
                case 8:
                    handleCau8(scanner);
                    break;
                case 9:
                    handleCau9();
                    break;
                case 10:
                    runAutomatedDemo();
                    break;
                case 0:
                    System.out.println("Ket thuc chuong trinh. Tam biet!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Lua chon khong hop le. Vui long chon lai!\n");
            }
        }
    }

    private static void printMenu() {
        System.out.println("======================================================================");
        System.out.println("  CHUONG TRINH ON TAP GIUA KY: CAY NHI PHAN TIM KIEM (BST) CUSTOMER  ");
        System.out.println("  Sinh vien: Pham Ngoc Lang | MSSV: 25765251 | Lop: 21BVL            ");
        System.out.println("  Giang vien huong dan: Thay Do Ha Phuong (dohaphuong@iuh.edu.vn)    ");
        System.out.println("======================================================================");
        System.out.println(" 1. Nhap danh sach khach hang vao cay BST theo customer_ID");
        System.out.println(" 2. Xuat danh sach khach hang theo dinh dang bang (In-order)");
        System.out.println(" 3. Tinh tuoi trung binh, tim khach hang tuoi lon nhat, nho nhat");
        System.out.println(" 4. Xuat danh sach theo gioi tinh va theo 4 nhom mau (A, B, O, AB)");
        System.out.println(" 5. Sap xep theo ten va xuat danh sach khach hang Nam");
        System.out.println(" 6. Tim khach hang Nam lon tuoi nhat, nho tuoi nhat");
        System.out.println(" 7. Tim kiem khach hang theo ID tren cay BST");
        System.out.println(" 8. Xoa khach hang theo ID tren cay BST (3 truong hop)");
        System.out.println(" 9. Luu danh sach khach hang ra file va doc file");
        System.out.println("10. Chay toan bo DEMO 9 cau lien tiep (chup anh bao cao)");
        System.out.println(" 0. Thoat chuong trinh");
        System.out.println("======================================================================");
    }

    private static void handleCau1() {
        System.out.println("\n--- CAU 1: NHAP DANH SACH KHACH HANG VAO CAY BST ---");
        List<Customer> list = FileHandler.readFromFile(DATA_FILE);
        if (list.isEmpty()) {
            System.out.println("Khong co du lieu trong " + DATA_FILE);
            return;
        }
        bst.clear();
        int count = 0;
        for (Customer c : list) {
            if (bst.insert(c)) {
                count++;
            }
        }
        System.out.printf("Da nhap thanh cong %d/%d khach hang vao cay BST theo customer_ID.\n\n", count, list.size());
    }

    private static void handleCau2() {
        System.out.println("\n--- CAU 2: XUAT DANH SACH KHACH HANG THEO DINH DANG BANG ---");
        BST.printTable(bst.toListInOrder(), "DANH SACH KHACH HANG (DUYET BST IN-ORDER)");
    }

    private static void handleCau3() {
        System.out.println("\n--- CAU 3: THONG KE TUOI KHACH HANG ---");
        if (bst.isEmpty()) {
            System.out.println("Cay dang trong!\n");
            return;
        }
        double avgAge = bst.calculateAverageAge();
        Customer oldest = bst.findMaxAgeCustomer();
        Customer youngest = bst.findMinAgeCustomer();

        System.out.printf("1. Tuoi trung binh cua khach hang: %.2f tuoi\n", avgAge);
        System.out.println("2. Khach hang lon tuoi nhat:");
        if (oldest != null) {
            System.out.println("   -> ID: " + oldest.getId() + " | Ho Ten: " + oldest.getName() + " | Tuoi: " + oldest.getAge());
        }
        System.out.println("3. Khach hang nho tuoi nhat:");
        if (youngest != null) {
            System.out.println("   -> ID: " + youngest.getId() + " | Ho Ten: " + youngest.getName() + " | Tuoi: " + youngest.getAge());
        }
        System.out.println();
    }

    private static void handleCau4() {
        System.out.println("\n--- CAU 4: PHAN LOAI THEO GIOI TINH VA NHOM MAU ---");
        BST.printTable(bst.filterByGender("Nam"), "DANH SACH KHACH HANG NAM");
        BST.printTable(bst.filterByGender("Nu"), "DANH SACH KHACH HANG NU");

        String[] bloodGroups = {"A", "B", "O", "AB"};
        for (String bg : bloodGroups) {
            BST.printTable(bst.filterByBlood(bg), "DANH SACH KHACH HANG NHOM MAU " + bg);
        }
    }

    private static void handleCau5() {
        System.out.println("\n--- CAU 5: SAP XEP THEO TEN VA XUAT KHACH HANG NAM ---");
        List<Customer> sortedMales = bst.getSortedMaleCustomersByName();
        BST.printTable(sortedMales, "DANH SACH KHACH HANG NAM SAP XEP THEO TEN (COMPARATOR)");
    }

    private static void handleCau6() {
        System.out.println("\n--- CAU 6: KHACH HANG NAM LON TUOI NHAT VA NHO TUOI NHAT ---");
        Customer oldestMale = bst.findOldestMaleCustomer();
        Customer youngestMale = bst.findYoungestMaleCustomer();

        System.out.println("1. Khach hang Nam lon tuoi nhat:");
        if (oldestMale != null) {
            System.out.println("   -> ID: " + oldestMale.getId() + " | Ho Ten: " + oldestMale.getName() + " | Tuoi: " + oldestMale.getAge());
        } else {
            System.out.println("   -> (Khong co khach hang Nam)");
        }

        System.out.println("2. Khach hang Nam nho tuoi nhat:");
        if (youngestMale != null) {
            System.out.println("   -> ID: " + youngestMale.getId() + " | Ho Ten: " + youngestMale.getName() + " | Tuoi: " + youngestMale.getAge());
        } else {
            System.out.println("   -> (Khong co khach hang Nam)");
        }
        System.out.println();
    }

    private static void handleCau7(Scanner scanner) {
        System.out.println("\n--- CAU 7: TIM KIEM KHACH HANG THEO ID TREN CAY BST ---");
        System.out.print("Nhap ID khach hang can tim: ");
        try {
            int searchId = Integer.parseInt(scanner.nextLine().trim());
            Customer found = bst.search(searchId);
            if (found != null) {
                System.out.println("[Tim thay tren BST]:");
                System.out.println("   ID       : " + found.getId());
                System.out.println("   Ho Ten   : " + found.getName());
                System.out.println("   Gioi Tinh: " + found.getGender());
                System.out.println("   Tuoi     : " + found.getAge());
                System.out.println("   Nhom Mau : " + found.getBlood());
            } else {
                System.out.println("[Khong tim thay]: Khong ton tai khach hang co ID = " + searchId + " tren cay.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID phai la so nguyen!");
        }
        System.out.println();
    }

    private static void handleCau8(Scanner scanner) {
        System.out.println("\n--- CAU 8: XOA KHACH HANG THEO ID TREN CAY BST ---");
        System.out.print("Nhap ID khach hang can xoa: ");
        try {
            int delId = Integer.parseInt(scanner.nextLine().trim());
            boolean success = bst.delete(delId);
            if (success) {
                System.out.println("Xoa thanh cong khach hang ID = " + delId + " khoi cay BST.");
                BST.printTable(bst.toListInOrder(), "DANH SACH SAU KHI XOA ID " + delId);
            } else {
                System.out.println("Khong the xoa: Khong ton tai khach hang co ID = " + delId);
            }
        } catch (NumberFormatException e) {
            System.out.println("ID phai la so nguyen!");
        }
        System.out.println();
    }

    private static void handleCau9() {
        System.out.println("\n--- CAU 9: LUU VA DOC DU LIEU TAP TIN ---");
        List<Customer> currentList = bst.toListInOrder();
        boolean saved = FileHandler.saveToFile(OUTPUT_FILE, currentList);
        if (saved) {
            System.out.println("1. Da luu thanh cong " + currentList.size() + " khach hang vao tap tin: " + OUTPUT_FILE);
        } else {
            System.out.println("1. Ghi file that bai!");
        }

        System.out.println("2. Doc thu lai du lieu tu tap tin " + OUTPUT_FILE + " vao cay moi:");
        BST testBst = new BST();
        List<Customer> loadedList = FileHandler.readFromFile(OUTPUT_FILE);
        for (Customer c : loadedList) {
            testBst.insert(c);
        }
        BST.printTable(testBst.toListInOrder(), "DANH SACH DOC LAI TU TAP TIN");
    }

    /**
     * Chạy tuần tự và tự động toàn bộ 9 câu của bài tập lớn để sinh output mẫu phục vụ chụp ảnh báo cáo.
     */
    public static void runAutomatedDemo() {
        System.out.println("######################################################################");
        System.out.println("  BAT DAU CHAY DEMO TU DONG TOAN BO 9 CAU ON TAP GIUA KY BST");
        System.out.println("  Sinh vien: Pham Ngoc Lang | MSSV: 25765251 | Lop: 21BVL");
        System.out.println("  Giang vien: Thay Do Ha Phuong (dohaphuong@iuh.edu.vn)");
        System.out.println("######################################################################\n");

        BST demoBst = new BST();

        // ---------------------------------------------------------------------
        // CAU 1
        // ---------------------------------------------------------------------
        System.out.println("======================================================================");
        System.out.println("  [CAU 1] NHAP DANH SACH KHACH HANG VAO CAY BST THEO CUSTOMER_ID");
        System.out.println("======================================================================");
        List<Customer> sampleData = FileHandler.readFromFile(DATA_FILE);
        System.out.println("Dang doc file du lieu mau: " + DATA_FILE);
        int insertedCount = 0;
        for (Customer c : sampleData) {
            if (demoBst.insert(c)) {
                insertedCount++;
                System.out.printf("  + Chen thanh cong: [ID=%-2d] %-20s (%s, %d tuoi, Nhom mau %s)\n",
                        c.getId(), c.getName(), c.getGender(), c.getAge(), c.getBlood());
            }
        }
        System.out.printf("Ket qua: Da nap %d/%d nut vao cay BST lay nhan khoa theo customer_ID.\n\n",
                insertedCount, sampleData.size());

        // ---------------------------------------------------------------------
        // CAU 2
        // ---------------------------------------------------------------------
        System.out.println("======================================================================");
        System.out.println("  [CAU 2] XUAT DANH SACH KHACH HANG THEO DINH DANG BANG (IN-ORDER)");
        System.out.println("======================================================================");
        System.out.println("Duyet cay theo thu tu In-order (LNR): Danh sach tu dong sap xep tang dan theo ID:");
        BST.printTable(demoBst.toListInOrder(), "BANG DANH SACH KHACH HANG (DUYET BST IN-ORDER)");

        // ---------------------------------------------------------------------
        // CAU 3
        // ---------------------------------------------------------------------
        System.out.println("======================================================================");
        System.out.println("  [CAU 3] TINH TUOI TRUNG BINH, TIM KHACH HANG TUOI LON NHAT, NHO NHAT");
        System.out.println("======================================================================");
        double avg = demoBst.calculateAverageAge();
        Customer maxAge = demoBst.findMaxAgeCustomer();
        Customer minAge = demoBst.findMinAgeCustomer();

        System.out.printf("1. Tuoi trung binh cua toan bo khach hang: %.2f tuoi\n", avg);
        System.out.println("2. Khach hang co tuoi lon nhat:");
        if (maxAge != null) {
            System.out.printf("   -> ID: %d | Ho Ten: %s | Gioi Tinh: %s | Tuoi: %d | Nhom Mau: %s\n",
                    maxAge.getId(), maxAge.getName(), maxAge.getGender(), maxAge.getAge(), maxAge.getBlood());
        }
        System.out.println("3. Khach hang co tuoi nho nhat:");
        if (minAge != null) {
            System.out.printf("   -> ID: %d | Ho Ten: %s | Gioi Tinh: %s | Tuoi: %d | Nhom Mau: %s\n",
                    minAge.getId(), minAge.getName(), minAge.getGender(), minAge.getAge(), minAge.getBlood());
        }
        System.out.println();

        // ---------------------------------------------------------------------
        // CAU 4
        // ---------------------------------------------------------------------
        System.out.println("======================================================================");
        System.out.println("  [CAU 4] DANH SACH THEO GIOI TINH VA THEO 4 NHOM MAU (A, B, O, AB)");
        System.out.println("======================================================================");
        BST.printTable(demoBst.filterByGender("Nam"), "DANH SACH KHACH HANG NAM");
        BST.printTable(demoBst.filterByGender("Nu"), "DANH SACH KHACH HANG NU");

        String[] groups = {"A", "B", "O", "AB"};
        for (String bg : groups) {
            BST.printTable(demoBst.filterByBlood(bg), "DANH SACH KHACH HANG NHOM MAU " + bg);
        }

        // ---------------------------------------------------------------------
        // CAU 5
        // ---------------------------------------------------------------------
        System.out.println("======================================================================");
        System.out.println("  [CAU 5] SAP XEP THEO TEN VA XUAT DANH SACH KHACH HANG NAM");
        System.out.println("======================================================================");
        System.out.println("Su dung Comparator sap xep theo Ten chinh (neu trung ten so sanh tiep Ho dem):");
        List<Customer> sortedMales = demoBst.getSortedMaleCustomersByName();
        BST.printTable(sortedMales, "DANH SACH NAM SAU KHI SAP XEP THEO TEN (COMPARATOR)");

        // ---------------------------------------------------------------------
        // CAU 6
        // ---------------------------------------------------------------------
        System.out.println("======================================================================");
        System.out.println("  [CAU 6] TIM KHACH HANG NAM LON TUOI NHAT VA NHO TUOI NHAT");
        System.out.println("======================================================================");
        Customer oldestM = demoBst.findOldestMaleCustomer();
        Customer youngestM = demoBst.findYoungestMaleCustomer();

        System.out.println("1. Khach hang Nam lon tuoi nhat:");
        if (oldestM != null) {
            System.out.printf("   -> ID: %d | Ho Ten: %s | Tuoi: %d | Nhom Mau: %s\n",
                    oldestM.getId(), oldestM.getName(), oldestM.getAge(), oldestM.getBlood());
        }
        System.out.println("2. Khach hang Nam nho tuoi nhat:");
        if (youngestM != null) {
            System.out.printf("   -> ID: %d | Ho Ten: %s | Tuoi: %d | Nhom Mau: %s\n",
                    youngestM.getId(), youngestM.getName(), youngestM.getAge(), youngestM.getBlood());
        }
        System.out.println();

        // ---------------------------------------------------------------------
        // CAU 7
        // ---------------------------------------------------------------------
        System.out.println("======================================================================");
        System.out.println("  [CAU 7] TIM KIEM KHACH HANG THEO ID TREN CAY BST (O(log N))");
        System.out.println("======================================================================");
        System.out.println("Test case 1: Tim ID ton tai (ID = 40):");
        Customer f1 = demoBst.search(40);
        if (f1 != null) {
            System.out.println("  [Ket qua]: Tim thay khach hang: " + f1);
        } else {
            System.out.println("  [Ket qua]: Khong tim thay!");
        }

        System.out.println("Test case 2: Tim ID khong ton tai (ID = 99):");
        Customer f2 = demoBst.search(99);
        if (f2 != null) {
            System.out.println("  [Ket qua]: Tim thay khach hang: " + f2);
        } else {
            System.out.println("  [Ket qua]: Khong tim thay khach hang co ID = 99 tren cay BST.");
        }
        System.out.println();

        // ---------------------------------------------------------------------
        // CAU 8
        // ---------------------------------------------------------------------
        System.out.println("======================================================================");
        System.out.println("  [CAU 8] XOA KHACH HANG THEO ID TREN CAY BST (3 TRUONG HOP)");
        System.out.println("======================================================================");
        System.out.println("Minh chung thuat toan xoa cay qua day du 3 truong hop:\n");

        System.out.println("--- TRUONG HOP 1: XOA NODE LA (LEAF NODE - ID 20) ---");
        System.out.println("Node ID 20 la con trai cua node 30, khong co con nao.");
        demoBst.delete(20);
        System.out.println("Da xoa thanh cong ID 20.");
        BST.printTable(demoBst.toListInOrder(), "DANH SACH SAU KHI XOA NODE LA (ID 20)");

        System.out.println("--- TRUONG HOP 2: XOA NODE CO 1 CON (SINGLE CHILD - ID 40) ---");
        System.out.println("Node ID 40 chi co 1 con trai la node 35.");
        demoBst.delete(40);
        System.out.println("Da xoa thanh cong ID 40 (Node 35 duoc day len thay the).");
        BST.printTable(demoBst.toListInOrder(), "DANH SACH SAU KHI XOA NODE 1 CON (ID 40)");

        System.out.println("--- TRUONG HOP 3: XOA NODE CO 2 CON (TWO CHILDREN - ID 70) ---");
        System.out.println("Node ID 70 co 2 con la 60 va 80.");
        System.out.println("Node the than duoc chon la node trai nhat cua cay con phai (In-order Successor).");
        demoBst.delete(70);
        System.out.println("Da xoa thanh cong ID 70.");
        BST.printTable(demoBst.toListInOrder(), "DANH SACH SAU KHI XOA NODE 2 CON (ID 70)");

        // ---------------------------------------------------------------------
        // CAU 9
        // ---------------------------------------------------------------------
        System.out.println("======================================================================");
        System.out.println("  [CAU 9] LUU DANH SACH RA FILE VA DOC LAI MOI KHI CHAY");
        System.out.println("======================================================================");
        List<Customer> remainingList = demoBst.toListInOrder();
        System.out.println("1. Ghi danh sach hien tai ra file: " + OUTPUT_FILE);
        boolean savedOk = FileHandler.saveToFile(OUTPUT_FILE, remainingList);
        System.out.println("   Ket qua ghi file: " + (savedOk ? "THANH CONG" : "THAT BAI"));

        System.out.println("2. Khoi tao cay BST moi hoan toan va doc du lieu tu file " + OUTPUT_FILE + " vao cay:");
        BST loadedBst = new BST();
        List<Customer> readBack = FileHandler.readFromFile(OUTPUT_FILE);
        for (Customer c : readBack) {
            loadedBst.insert(c);
        }
        System.out.printf("   Da nap lai %d khach hang tu file vao cay BST moi.\n", readBack.size());
        BST.printTable(loadedBst.toListInOrder(), "DANH SACH KHACH HANG TRONG CAY BST MOI (DOC TU FILE)");

        System.out.println("######################################################################");
        System.out.println("  KET THUC DEMO TOAN BO 9 CAU THANH CONG 100%");
        System.out.println("######################################################################\n");
    }
}
