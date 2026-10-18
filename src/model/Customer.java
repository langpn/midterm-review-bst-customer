package model;

/**
 * Lớp đại diện cho thực thể Khách hàng.
 * Sinh viên: Phạm Ngọc Lang | MSSV: 25765251 | Lớp: 21BVL
 */
public class Customer {
    private int id;
    private String name;
    private String gender;
    private int age;
    private String blood;

    public Customer() {
    }

    public Customer(int id, String name, String gender, int age, String blood) {
        this.id = id;
        this.name = name != null ? name.trim() : "";
        this.gender = gender != null ? gender.trim() : "";
        this.age = age;
        this.blood = blood != null ? blood.trim() : "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name != null ? name.trim() : "";
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender != null ? gender.trim() : "";
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood != null ? blood.trim() : "";
    }

    /**
     * Tách lấy tên chính (từ cuối cùng trong họ và tên) để phục vụ sắp xếp.
     * Ví dụ: "Nguyen Van An" -> "An"
     */
    public String getFirstName() {
        if (name == null || name.trim().isEmpty()) {
            return "";
        }
        String[] parts = name.trim().split("\\s+");
        return parts[parts.length - 1];
    }

    /**
     * Tách lấy phần họ và tên đệm để so sánh phụ khi trùng tên chính.
     * Ví dụ: "Nguyen Van An" -> "Nguyen Van"
     */
    public String getLastNameAndMiddle() {
        if (name == null || name.trim().isEmpty()) {
            return "";
        }
        String trimmed = name.trim();
        int lastSpaceIndex = trimmed.lastIndexOf(' ');
        if (lastSpaceIndex == -1) {
            return "";
        }
        return trimmed.substring(0, lastSpaceIndex);
    }

    /**
     * Định dạng hàng dữ liệu bảng ASCII chuẩn căn lề.
     */
    public String toTableRow() {
        return String.format("| %-5d | %-20s | %-8s | %-5d | %-8s |", id, name, gender, age, blood);
    }

    /**
     * Chuyển đổi thành dòng CSV để lưu trữ tập tin.
     */
    public String toCsvLine() {
        return id + "," + name + "," + gender + "," + age + "," + blood;
    }

    /**
     * Phân tích dòng CSV thành đối tượng Customer.
     */
    public static Customer fromCsvLine(String line) {
        if (line == null || line.trim().isEmpty() || line.trim().startsWith("#")) {
            return null;
        }
        String[] tokens = line.split(",");
        if (tokens.length < 5) {
            return null;
        }
        try {
            int id = Integer.parseInt(tokens[0].trim());
            String name = tokens[1].trim();
            String gender = tokens[2].trim();
            int age = Integer.parseInt(tokens[3].trim());
            String blood = tokens[4].trim();
            return new Customer(id, name, gender, age, blood);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format("Customer[ID=%d, Name='%s', Gender='%s', Age=%d, Blood='%s']",
                id, name, gender, age, blood);
    }
}
