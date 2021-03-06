package ml.gomtricks.easystock;

public class SellerProduct {
    private int billNo;
    private String seller;
    private String product;
    private int qty;
    private int rate;

    public SellerProduct(int billNo, String seller, String product, int qty, int rate) {
        this.billNo = billNo;
        this.seller = seller;
        this.product = product;
        this.qty = qty;
        this.rate = rate;
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

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}
