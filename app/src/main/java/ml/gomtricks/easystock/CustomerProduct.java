package ml.gomtricks.easystock;

public class CustomerProduct {
    private int billNo;
    private String customer;
    private String product;
    private int qty;
    private int price;

    public CustomerProduct(int billNo, String customer, String product, int qty, int price) {
        this.billNo = billNo;
        this.customer = customer;
        this.product = product;
        this.qty = qty;
        this.price = price;
    }

    public int getBillNo() {
        return billNo;
    }

    public void setBillNo(int billNo) {
        this.billNo = billNo;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
