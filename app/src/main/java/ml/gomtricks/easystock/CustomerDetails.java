package ml.gomtricks.easystock;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import adapter.DetailsAdapter;
import database.DatabaseHelper;

public class CustomerDetails extends Fragment {
    DatabaseHelper MyDb;
    ArrayList<CustomerBill> mCustomerBills;
    ArrayList<CustomerProduct> mCustomerProducts;
    String customer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_customer, null);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mCustomerBills = new ArrayList<>();
        mCustomerProducts = new ArrayList<>();
        MyDb = new DatabaseHelper(this.getContext());

        final RecyclerView detailsRecycler = (RecyclerView) getView().findViewById(R.id.details_recycler);
        final LinearLayoutManager listLayoutManager = new LinearLayoutManager(this.getContext());
        detailsRecycler.setLayoutManager(listLayoutManager);

        getSCustomerBills("Musa");
        final DetailsAdapter detailsAdapter = new DetailsAdapter(this.getContext(), mCustomerBills,
                mCustomerProducts, 1, 1);
        detailsRecycler.setAdapter(detailsAdapter);
    }

    public void getSCustomerBills(String name) {
        Cursor cursor;
        int billNo;
        String date;
        int amount;
        int cash;
        int transfer;
        int bal;
        cursor = MyDb.getCustomerBill(name);
        while (cursor.moveToNext()) {
            billNo = cursor.getInt(1);
            customer = cursor.getString(2);
            date = cursor.getString(3);
            amount = cursor.getInt(4);
            cash = cursor.getInt(5);
            transfer = cursor.getInt(6);
            bal = cursor.getInt(7);
            mCustomerBills.add(new CustomerBill(name, billNo, date, amount, cash, transfer, bal));
        }
        cursor.close();
        getCustomerProducts(customer);
    }

    public void getCustomerProducts(String name) {
        Cursor cursor;
        int billNo;
        String product;
        int qty;
        int rate;
        cursor = MyDb.getProductOut(customer);
        while (cursor.moveToNext()) {
            billNo = cursor.getInt(1);
            product = cursor.getString(3);
            qty = cursor.getInt(4);
            rate = cursor.getInt(5);

            mCustomerProducts.add(new CustomerProduct(billNo, customer, product, qty, rate));
        }
        cursor.close();
    }

}
