package database;

import android.provider.BaseColumns;


public final class DatabaseContract {
    private DatabaseContract() {

    }

    public static final class StockEntry implements BaseColumns {
        public static final String TABLE_NAME = "stock";
        public static final String COL_STOCK_NAME = "product";
        public static final String COL_STOCK_QTY = "qty";
        public static final String COL_STOCK_RATE = "rate";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + "(" + _ID + " INTEGER PRIMARY KEY,"
                        + COL_STOCK_NAME + " TEXT NOT NULL,"
                        + COL_STOCK_QTY + " INTEGER NOT NULL,"
                        + COL_STOCK_RATE + " INTEGER NOT NULL)";
    }

    public static final class CustomerEntry implements BaseColumns {
        public static final String TABLE_NAME = "customer";
        public static final String COL_CUSTOMER_NAME = "name";
        public static final String COL_CUSTOMER_PHONE = "phone";
        public static final String COL_CUSTOMER_BILL = "bill";
        public static final String COL_CUSTOMER_BALANCE = "balance";


        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + "(" + _ID + " INTEGER PRIMARY KEY,"
                        + COL_CUSTOMER_NAME + " TEXT NOT NULL,"
                        + COL_CUSTOMER_PHONE + " TEXT,"
                        + COL_CUSTOMER_BILL + " INTEGER,"
                        + COL_CUSTOMER_BALANCE + " INTEGER)";
    }

    public static final class SellerEntry implements BaseColumns {
        public static final String TABLE_NAME = "seller";
        public static final String COL_SELLER_NAME = "name";
        public static final String COL_SELLER_PHONE = "phone";
        public static final String COL_SELLER_BILL = "bill";
        public static final String COL_SELLER_BALANCE = "balance";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + "(" + _ID + " INTEGER PRIMARY KEY,"
                        + COL_SELLER_NAME + " TEXT NOT NULL,"
                        + COL_SELLER_PHONE + " TEXT,"
                        + COL_SELLER_BILL + " INTEGER,"
                        + COL_SELLER_BALANCE + " INTEGER)";
    }

    public static final class CustomerBillEntry implements BaseColumns {
        public static final String TABLE_NAME = "customer_bill";
        public static final String COL_BILL_NO = "bill_no";
        public static final String COL_CUSTOMER_NAME = "customer";
        public static final String COL_BILL_DATE = "date";
        public static final String COL_BILL_AMOUNT = "amount";
        public static final String COL_BILL_CASH = "cash";
        public static final String COL_BILL_TRANSFER = "transfer";
        public static final String COL_BILL_BALANCE = "balance";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + "(" + _ID + " INTEGER PRIMARY KEY,"
                        + COL_BILL_NO + " INTEGER NOT NULL, "
                        + COL_CUSTOMER_NAME + " TEXT NOT NULL,"
                        + COL_BILL_DATE + " TEXT NOT NULL,"
                        + COL_BILL_AMOUNT + " INTEGER NOT NULL, "
                        + COL_BILL_CASH + " INTEGER, "
                        + COL_BILL_TRANSFER + " INTEGER, "
                        + COL_BILL_BALANCE + " INTEGER NOT NULL)";
    }

    public static final class SellerBillEntry implements BaseColumns {
        public static final String TABLE_NAME = "seller_bill";
        public static final String COL_BILL_NO = "bill_no";
        public static final String COL_SELLER_NAME = "name";
        public static final String COL_BILL_DATE = "date";
        public static final String COL_BILL_AMOUNT = "amount";
        public static final String COL_BILL_CASH = "cash";
        public static final String COL_BILL_TRANSFER = "transfer";
        public static final String COL_BILL_BALANCE = "balance";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + "(" + _ID + " INTEGER PRIMARY KEY,"
                        + COL_BILL_NO + " INTEGER NOT NULL, "
                        + COL_SELLER_NAME + " TEXT NOT NULL,"
                        + COL_BILL_DATE + " TEXT NOT NULL,"
                        + COL_BILL_AMOUNT + " INTEGER NOT NULL, "
                        + COL_BILL_CASH + " INTEGER, "
                        + COL_BILL_TRANSFER + " INTEGER, "
                        + COL_BILL_BALANCE + " INTEGER NOT NULL)";
    }

    public static final class ProductIn implements BaseColumns {
        public static final String TABLE_NAME = "product_in";
        public static final String COL_BILL_NO = "bill_no";
        public static final String COL_SELLER_NAME = "seller";
        public static final String COL_PRODUCT_NAME = "product";
        public static final String COL_QTY = "qty";
        public static final String COL_RATE = "rate";


        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + "(" + _ID + " INTEGER PRIMARY KEY,"
                        + COL_BILL_NO + " INTEGER NOT NULL,"
                        + COL_SELLER_NAME + " TEXT NOT NULL,"
                        + COL_PRODUCT_NAME + " TEXT NOT NULL, "
                        + COL_QTY + " INTEGER NOT NULL, "
                        + COL_RATE + " INTEGER NOT NULL) ";
    }

    public static final class productOut implements BaseColumns {
        public static final String TABLE_NAME = "product_out";
        public static final String COL_BILL_NO = "bill_no";
        public static final String COL_CUSTOMER_NAME = "customer";
        public static final String COL_PRODUCT_NAME = "product";
        public static final String COL_QTY = "qty";
        public static final String COL_PRICE = "price";


        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + "(" + _ID + " INTEGER PRIMARY KEY,"
                        + COL_BILL_NO + " INTEGER NOT NULL, "
                        + COL_CUSTOMER_NAME + " TEXT NOT NULL,"
                        + COL_PRODUCT_NAME + " TEXT NOT NULL, "
                        + COL_QTY + " INTEGER NOT NULL, "
                        + COL_PRICE + " INTEGER NOT NULL) ";
    }

    public static final class profit implements BaseColumns {
        public static final String TABLE_NAME = "profit";
        public static final String COL_AMOUNT = "amount";
        public static final String COL_DATE = "date";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY, " + COL_AMOUNT + ", " +
                        COL_DATE + " TEXT NOT NULL)";
    }
}
