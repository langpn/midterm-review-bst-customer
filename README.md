# BÀI TẬP TỔNG ÔN THI GIỮA KỲ: CÂY NHỊ PHÂN TÌM KIẾM (BST) CUSTOMER
**Môn học:** Lập trình trong CNTT với Java  
**Giảng viên:** Thầy Đỗ Hà Phương (dohaphuong@iuh.edu.vn)  
**Sinh viên thực hiện:** Phạm Ngọc Lang  
**MSSV:** 25765251 | **Lớp:** 21BVL  
**Trường:** Đại học Công nghiệp TP.HCM (IUH)

---

## 1. Giới Thiệu Đề Bài
Hiện thực cấu trúc Cây nhị phân tìm kiếm (Binary Search Tree - BST) để quản lý danh sách khách hàng `Customer(ID, Name, Gender, Age, Blood)`. Nhãn các node được định danh theo `customer_ID`.

Chương trình giải quyết trọn vẹn 9 yêu cầu nghiệp vụ:
1. Nhập danh sách khách hàng vào cây BST lấy nhãn các node theo `customer_ID`.
2. Xuất danh sách khách hàng theo định dạng bảng chuẩn ASCII (duyệt In-order LNR).
3. Tính tuổi trung bình khách hàng, tìm khách hàng có tuổi lớn nhất và nhỏ nhất.
4. Xuất danh sách khách hàng Nam, Nữ và danh sách theo 4 nhóm máu (A, B, O, AB).
5. Sắp xếp theo tên chính (và họ đệm khi trùng tên) bằng `Comparator` và xuất danh sách khách hàng Nam.
6. Tìm khách hàng Nam lớn tuổi nhất và khách hàng Nam nhỏ tuổi nhất.
7. Tìm kiếm khách hàng theo ID trên cây BST với độ phức tạp O(log N).
8. Xóa khách hàng theo ID trên cây BST xử lý đầy đủ 3 trường hợp:
   - Trường hợp 1: Xóa node lá (Leaf node).
   - Trường hợp 2: Xóa node có 1 con (Single child).
   - Trường hợp 3: Xóa node có 2 con (Two children - dùng node thế thân là node trái nhất của cây con phải).
9. Lưu danh sách khách hàng ra file và đọc file lấy danh sách mỗi khi chạy chương trình.

---

## 2. Cấu Trúc Mã Nguồn
```text
midterm-review-bst-customer/
├── src/
│   ├── model/
│   │   └── Customer.java       # Entity Customer (ID, Name, Gender, Age, Blood)
│   ├── tree/
│   │   ├── Node.java           # Node của cây BST
│   │   └── BST.java            # Cây BST và các thuật toán nghiệp vụ 9 câu
│   ├── io/
│   │   └── FileHandler.java    # Đọc/ghi tập tin UTF-8
│   └── Main.java               # Menu CLI tương tác và bộ Automated Demo Runner
├── data/
│   ├── customers.txt           # Dữ liệu 10 khách hàng mẫu ban đầu
│   └── customers_output.txt    # Dữ liệu xuất ra file (Câu 9)
├── scripts/
│   ├── compile.sh              # Biên dịch javac mã nguồn
│   ├── run.sh                  # Chạy giao diện tương tác CLI
│   ├── demo.sh                 # Chạy tự động 9 câu phục vụ chụp ảnh báo cáo
│   └── package.sh              # Đóng gói zip nộp bài cho Giảng viên
├── screenshots/                # Thư mục chứa ảnh chụp code và kết quả từng câu
├── BAO_CAO.md                  # Báo cáo chi tiết nộp kèm hình ảnh
└── README.md                   # Hướng dẫn này
```

---

## 3. Hướng Dẫn Biên Dịch Và Thực Thi

### A. Biên dịch mã nguồn:
```bash
bash scripts/compile.sh
```

### B. Chạy chế độ Demo tự động (để cuộn xem kết quả và chụp ảnh màn hình):
```bash
bash scripts/demo.sh
```

### C. Chạy giao diện Menu tương tác CLI:
```bash
bash scripts/run.sh
```

### D. Đóng gói mã nguồn nộp qua email:
```bash
bash scripts/package.sh
```
File đóng gói sẽ được tạo tại:
`25765251_PhamNgocLang_BTTL_Java_BST_Customer.zip`
