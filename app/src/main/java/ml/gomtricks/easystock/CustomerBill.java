package ml.gomtricks.easystock;

public class CustomerBill {
    private String name;
    private int billNo;
    private String date;
    private int amount;
    private int cash;
    private int transfer;
    private int balance;

    public CustomerBill(String name, int billNo, String date, int amount, int cash, int transfer, int balance) {
        this.setName(name);
        this.setBillNo(billNo);
        this.setDate(date);
        this.setAmount(amount);
        this.setCash(cash);
        this.setTransfer(transfer);
        this.setBalance(balance);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBillNo() {
        return billNo;
    }

    public void setBillNo(int billNo) {
        this.billNo = billNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getTransfer() {
        return transfer;
    }

    public void setTransfer(int transfer) {
        this.transfer = transfer;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
