package tree;

import model.Customer;

/**
 * Lớp đại diện cho một Node trên Cây nhị phân tìm kiếm (BST).
 * Mỗi node lưu trữ một đối tượng Customer và hai con trỏ left, right.
 */
public class Node {
    public Customer data;
    public Node left;
    public Node right;

    public Node(Customer data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}
