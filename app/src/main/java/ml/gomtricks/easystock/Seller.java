package ml.gomtricks.easystock;

public class Seller {
    private String name;
    private String phone;
    private int bf;
    private int cb;
    private int id;

    public Seller(int id, String name, String phone, int bf, int cb) {
        this.name = name;
        this.phone = phone;
        this.bf = bf;
        this.cb = cb;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getBf() {
        return bf;
    }

    public void setBf(int bf) {
        this.bf = bf;
    }

    public int getCb() {
        return cb;
    }

    public void setCb(int cb) {
        this.cb = cb;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
