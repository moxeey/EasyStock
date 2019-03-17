package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import database.DatabaseContract.CustomerEntry;
import database.DatabaseContract.ProductIn;
import database.DatabaseContract.SellerEntry;
import database.DatabaseContract.StockEntry;
import database.DatabaseContract.productOut;

import static database.DatabaseContract.CustomerBillEntry;
import static database.DatabaseContract.SellerBillEntry;
import static database.DatabaseContract.profit;

public class DatabaseHelper extends SQLiteOpenHelper {
    public final static String DATABASE_NAME = "EasyStock.db";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(StockEntry.SQL_CREATE_TABLE);
        db.execSQL(CustomerEntry.SQL_CREATE_TABLE);
        db.execSQL(SellerEntry.SQL_CREATE_TABLE);
        db.execSQL(SellerBillEntry.SQL_CREATE_TABLE);
        db.execSQL(CustomerBillEntry.SQL_CREATE_TABLE);
        db.execSQL(ProductIn.SQL_CREATE_TABLE);
        db.execSQL(productOut.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS stock");
        onCreate(db);
    }

    public boolean insertStock(String product, int qty, int rate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(StockEntry.COL_STOCK_NAME, product);
        cv.put(StockEntry.COL_STOCK_QTY, qty);
        cv.put(StockEntry.COL_STOCK_RATE, rate);
        long result = db.insert(StockEntry.TABLE_NAME, null, cv);
        if (result == -1) return false;
        else return true;
    }

    public Cursor getStock(String product) throws SQLException {
        Cursor cursor;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + StockEntry.TABLE_NAME + " WHERE " + StockEntry.COL_STOCK_NAME + " = '" + product + "'";
        cursor = db.rawQuery(query, null);
        return cursor;

    }

    public Cursor getAllStock() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + StockEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public boolean updateStock(String product, int qty) {
        int cQty = 0;
        Cursor cursor = getStock(product);
        while (cursor.moveToNext()) {
            cQty = cursor.getInt(2);
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(StockEntry.COL_STOCK_QTY, cQty + qty);
        long result = db.update(StockEntry.TABLE_NAME, cv, "product=?", new String[]{product});
        if (result == -1) return false;
        else return true;
    }


    public boolean insertCustomer(String name, String phone, int bill, int balance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CustomerEntry.COL_CUSTOMER_NAME, name);
        cv.put(CustomerEntry.COL_CUSTOMER_PHONE, phone);
        cv.put(CustomerEntry.COL_CUSTOMER_BILL, bill);
        cv.put(CustomerEntry.COL_CUSTOMER_BALANCE, balance);
        long result = db.insert(CustomerEntry.TABLE_NAME, null, cv);
        if (result == -1) return false;
        else return true;
    }

    public Cursor getCustomer(String name) throws SQLException {
        Cursor cursor;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + CustomerEntry.TABLE_NAME + " WHERE " + CustomerEntry.COL_CUSTOMER_NAME + " = '" + name + "'";
        cursor = db.rawQuery(query, null);
        return cursor;

    }

    public Cursor getAllCustomer() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + CustomerEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor getCustomerBill(String customer) {
        Cursor cursor;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + CustomerBillEntry.TABLE_NAME + " WHERE " + CustomerBillEntry.COL_CUSTOMER_NAME + " = '" + customer + "'";
        cursor = db.rawQuery(query, null);
        return cursor;
    }

    public boolean updateCustomer(String name, int bill, int balance) {
        int cBill = 0;
        int cBal = 0;
        Cursor cursor = getCustomer(name);
        while (cursor.moveToNext()) {
            cBill = cursor.getInt(3);
            cBill = cursor.getInt(4);
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CustomerEntry.COL_CUSTOMER_BILL, cBill + bill);
        cv.put(CustomerEntry.COL_CUSTOMER_BILL, cBal + balance);
        long result = db.update(CustomerEntry.TABLE_NAME, cv, "_id=?", new String[]{getCustomerID(name)});
        if (result == -1) return false;
        else return true;
    }

    public String getCustomerID(String name) {
        Cursor cursor = getCustomer(name);
        String id = "1";
        while (cursor.moveToNext()) {
            id = cursor.getString(0);
        }

        return id;
    }


    public boolean insertSeller(String name, String phone, int bill, int balance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SellerEntry.COL_SELLER_NAME, name);
        cv.put(SellerEntry.COL_SELLER_PHONE, phone);
        cv.put(SellerEntry.COL_SELLER_BALANCE, balance);
        cv.put(SellerEntry.COL_SELLER_BILL, bill);
        long result = db.insert(SellerEntry.TABLE_NAME, null, cv);
        if (result == -1) return false;
        else return true;
    }

    public boolean updateSeller(String name, int bill, int balance) {
        int cBill = 0;
        int cBal = 0;
        Cursor cursor = getSeller(name);
        while (cursor.moveToNext()) {
            cBill = cursor.getInt(3);
            cBill = cursor.getInt(4);
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SellerEntry.COL_SELLER_BILL, cBill + bill);
        cv.put(SellerEntry.COL_SELLER_BALANCE, cBal + balance);
        long result = db.update(SellerEntry.TABLE_NAME, cv, "_id=?", new String[]{getSellerID(name)});
        if (result == -1) return false;
        else return true;
    }

    public String getSellerID(String name) {
        Cursor cursor = getSeller(name);
        String id = "1";
        while (cursor.moveToNext()) {
            id = cursor.getString(0);
        }

        return id;
    }

    public Cursor getSeller(String name) throws SQLException {
        Cursor cursor;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + SellerEntry.TABLE_NAME + " WHERE " + SellerEntry.COL_SELLER_NAME + " = '" + name + "'";
        cursor = db.rawQuery(query, null);
        return cursor;

    }

    public Cursor getAllSeller() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + SellerEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor getSellerBill(String seller) {
        Cursor cursor;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + SellerBillEntry.TABLE_NAME + " WHERE " + SellerBillEntry.COL_SELLER_NAME + " = '" + seller + "'";
        cursor = db.rawQuery(query, null);
        return cursor;
    }


    public boolean insertProductIn(int billNo, String product, int qty, int rate, String seller) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ProductIn.COL_BILL_NO, billNo);
        cv.put(ProductIn.COL_PRODUCT_NAME, product);
        cv.put(ProductIn.COL_QTY, qty);
        cv.put(ProductIn.COL_RATE, rate);
        cv.put(ProductIn.COL_SELLER_NAME, seller);
        long result = db.insert(ProductIn.TABLE_NAME, null, cv);
        if (result == -1) return false;
        else return true;
    }

    public Cursor getProductIn(String seller) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + ProductIn.TABLE_NAME + " WHERE " +
                ProductIn.COL_SELLER_NAME + " = '" + seller + "'";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public boolean insertProductOut(int billNo, String product, int qty, int price, String customer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(productOut.COL_BILL_NO, billNo);
        cv.put(productOut.COL_PRODUCT_NAME, product);
        cv.put(productOut.COL_QTY, qty);
        cv.put(productOut.COL_PRICE, price);
        cv.put(productOut.COL_CUSTOMER_NAME, customer);
        long result = db.insert(productOut.TABLE_NAME, null, cv);
        if (result == -1) return false;
        else return true;
    }

    public Cursor getProductOut(String customer) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + productOut.TABLE_NAME + " WHERE " +
                DatabaseContract.productOut.COL_CUSTOMER_NAME + " = '" + customer + "'";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public boolean insertCustomerBill(int billNo, String customer, int amount, int cash, int transfer, int balance, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CustomerBillEntry.COL_BILL_NO, billNo);
        cv.put(CustomerBillEntry.COL_CUSTOMER_NAME, customer);
        cv.put(CustomerBillEntry.COL_BILL_AMOUNT, amount);
        cv.put(CustomerBillEntry.COL_BILL_CASH, cash);
        cv.put(CustomerBillEntry.COL_BILL_TRANSFER, transfer);
        cv.put(CustomerBillEntry.COL_BILL_BALANCE, balance);
        cv.put(CustomerBillEntry.COL_BILL_DATE, date);
        long result = db.insert(CustomerBillEntry.TABLE_NAME, null, cv);
        if (result == -1) return false;
        else return true;
    }

    public boolean insertSellerBill(int billNo, String customer, int amount, int cash, int transfer, int balance, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SellerBillEntry.COL_BILL_NO, billNo);
        cv.put(SellerBillEntry.COL_SELLER_NAME, customer);
        cv.put(SellerBillEntry.COL_BILL_AMOUNT, amount);
        cv.put(SellerBillEntry.COL_BILL_CASH, cash);
        cv.put(SellerBillEntry.COL_BILL_TRANSFER, transfer);
        cv.put(SellerBillEntry.COL_BILL_BALANCE, balance);
        cv.put(SellerBillEntry.COL_BILL_DATE, date);
        long result = db.insert(SellerBillEntry.TABLE_NAME, null, cv);
        if (result == -1) return false;
        else return true;
    }

    public boolean insertProfit(int amount, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(profit.COL_AMOUNT, amount);
        cv.put(profit.COL_DATE, date);
        long result = db.insert(profit.TABLE_NAME, null, cv);
        if (result == -1) return false;
        else return true;
    }

    public Cursor getProfit() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + profit.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor getCustomerBillNo() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT COUNT(*) FROM " + CustomerBillEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public Cursor getSellerBillNo() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT COUNT(*) FROM " + SellerBillEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
}
