package tree;

import model.Customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Cây nhị phân tìm kiếm (Binary Search Tree - BST) quản lý danh sách Customer.
 * Khóa tìm kiếm và sắp xếp là customer_ID.
 * Sinh viên: Phạm Ngọc Lang | MSSV: 25765251 | Lớp: 21BVL
 */
public class BST {
    private Node root;

    public BST() {
        this.root = null;
    }

    public Node getRoot() {
        return root;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void clear() {
        this.root = null;
    }

    // =========================================================================
    // CAU 1: Chen khach hang vao cay BST theo nhan customer_ID
    // =========================================================================
    public boolean insert(Customer customer) {
        if (customer == null) {
            return false;
        }
        if (root == null) {
            root = new Node(customer);
            return true;
        }
        return insertRec(root, customer);
    }

    private boolean insertRec(Node current, Customer customer) {
        if (customer.getId() == current.data.getId()) {
            // Trùng ID - không chèn để đảm bảo tính duy nhất của khóa BST
            return false;
        }
        if (customer.getId() < current.data.getId()) {
            if (current.left == null) {
                current.left = new Node(customer);
                return true;
            } else {
                return insertRec(current.left, customer);
            }
        } else {
            if (current.right == null) {
                current.right = new Node(customer);
                return true;
            } else {
                return insertRec(current.right, customer);
            }
        }
    }

    // =========================================================================
    // CAU 2: Duyet In-order (LNR) de xuat danh sach theo dinh dang bang
    // =========================================================================
    public List<Customer> toListInOrder() {
        List<Customer> list = new ArrayList<>();
        inOrderRec(root, list);
        return list;
    }

    private void inOrderRec(Node node, List<Customer> list) {
        if (node != null) {
            inOrderRec(node.left, list);
            list.add(node.data);
            inOrderRec(node.right, list);
        }
    }

    public static void printTable(List<Customer> list, String title) {
        System.out.println("----------------------------------------------------------------------");
        System.out.println("  " + title);
        System.out.println("+-------+----------------------+----------+-------+----------+");
        System.out.println("| ID    | Ho va Ten            | GioiTinh | Tuoi  | Nhom Mau |");
        System.out.println("+-------+----------------------+----------+-------+----------+");
        if (list == null || list.isEmpty()) {
            System.out.println("|                      (Danh sach trong)                             |");
        } else {
            for (Customer c : list) {
                System.out.println(c.toTableRow());
            }
        }
        System.out.println("+-------+----------------------+----------+-------+----------+");
        System.out.printf("Tong so: %d khach hang\n", list != null ? list.size() : 0);
        System.out.println();
    }

    // =========================================================================
    // CAU 3: Tinh tuoi trung binh, tim khach hang tuoi lon nhat, nho nhat
    // =========================================================================
    public double calculateAverageAge() {
        List<Customer> list = toListInOrder();
        if (list.isEmpty()) {
            return 0.0;
        }
        int totalAge = 0;
        for (Customer c : list) {
            totalAge += c.getAge();
        }
        return (double) totalAge / list.size();
    }

    public Customer findMaxAgeCustomer() {
        List<Customer> list = toListInOrder();
        if (list.isEmpty()) {
            return null;
        }
        Customer maxCustomer = list.get(0);
        for (Customer c : list) {
            if (c.getAge() > maxCustomer.getAge()) {
                maxCustomer = c;
            }
        }
        return maxCustomer;
    }

    public Customer findMinAgeCustomer() {
        List<Customer> list = toListInOrder();
        if (list.isEmpty()) {
            return null;
        }
        Customer minCustomer = list.get(0);
        for (Customer c : list) {
            if (c.getAge() < minCustomer.getAge()) {
                minCustomer = c;
            }
        }
        return minCustomer;
    }

    // =========================================================================
    // CAU 4: Xuat danh sach theo gioi tinh (Nam, Nu) va theo nhom mau (A, B, O, AB)
    // =========================================================================
    public List<Customer> filterByGender(String gender) {
        List<Customer> all = toListInOrder();
        List<Customer> result = new ArrayList<>();
        if (gender == null) return result;
        for (Customer c : all) {
            if (c.getGender().equalsIgnoreCase(gender.trim())) {
                result.add(c);
            }
        }
        return result;
    }

    public List<Customer> filterByBlood(String blood) {
        List<Customer> all = toListInOrder();
        List<Customer> result = new ArrayList<>();
        if (blood == null) return result;
        for (Customer c : all) {
            if (c.getBlood().equalsIgnoreCase(blood.trim())) {
                result.add(c);
            }
        }
        return result;
    }

    // =========================================================================
    // CAU 5: Sap xep theo ten va xuat danh sach khach hang Nam
    // =========================================================================
    public List<Customer> getSortedMaleCustomersByName() {
        List<Customer> maleList = filterByGender("Nam");
        Collections.sort(maleList, new Comparator<Customer>() {
            @Override
            public int compare(Customer c1, Customer c2) {
                // Ưu tiên so sánh Tên chính
                int cmpName = c1.getFirstName().compareToIgnoreCase(c2.getFirstName());
                if (cmpName != 0) {
                    return cmpName;
                }
                // Nếu trùng tên chính, so sánh tiếp Họ và Tên đệm
                return c1.getLastNameAndMiddle().compareToIgnoreCase(c2.getLastNameAndMiddle());
            }
        });
        return maleList;
    }

    // =========================================================================
    // CAU 6: Tim khach hang Nam lon tuoi nhat va nho tuoi nhat
    // =========================================================================
    public Customer findOldestMaleCustomer() {
        List<Customer> maleList = filterByGender("Nam");
        if (maleList.isEmpty()) {
            return null;
        }
        Customer oldest = maleList.get(0);
        for (Customer c : maleList) {
            if (c.getAge() > oldest.getAge()) {
                oldest = c;
            }
        }
        return oldest;
    }

    public Customer findYoungestMaleCustomer() {
        List<Customer> maleList = filterByGender("Nam");
        if (maleList.isEmpty()) {
            return null;
        }
        Customer youngest = maleList.get(0);
        for (Customer c : maleList) {
            if (c.getAge() < youngest.getAge()) {
                youngest = c;
            }
        }
        return youngest;
    }

    // =========================================================================
    // CAU 7: Tim kiem khach hang theo ID tren cay BST (Do phuc tap O(log N))
    // =========================================================================
    public Customer search(int id) {
        Node current = root;
        while (current != null) {
            if (id == current.data.getId()) {
                return current.data;
            } else if (id < current.data.getId()) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    // =========================================================================
    // CAU 8: Xoa khach hang theo ID tren cay BST (3 truong hop chuan)
    // =========================================================================
    public boolean delete(int id) {
        if (search(id) == null) {
            return false;
        }
        root = deleteRec(root, id);
        return true;
    }

    private Node deleteRec(Node current, int id) {
        if (current == null) {
            return null;
        }

        if (id < current.data.getId()) {
            current.left = deleteRec(current.left, id);
        } else if (id > current.data.getId()) {
            current.right = deleteRec(current.right, id);
        } else {
            // Tim thay node can xoa: current.data.getId() == id

            // Truong hop 1: Node la (khong co con) -> Tra ve null
            // Truong hop 2: Node chi co 1 con -> Tra ve node con duy nhat
            if (current.left == null) {
                return current.right;
            } else if (current.right == null) {
                return current.left;
            }

            // Truong hop 3: Node co 2 con
            // Tim node the than: Node trai nhat cua cay con phai (In-order Successor)
            Node successor = getLeftmostNode(current.right);
            // Sao chep du lieu cua node the than vao node hien tai
            current.data = successor.data;
            // Xoa node the than o cay con phai
            current.right = deleteRec(current.right, successor.data.getId());
        }
        return current;
    }

    private Node getLeftmostNode(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }
}
