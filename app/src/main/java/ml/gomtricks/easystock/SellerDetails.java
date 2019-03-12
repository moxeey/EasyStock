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
import android.widget.Toast;

import java.util.ArrayList;

import adapter.DetailsAdapter;
import database.DatabaseHelper;

public class SellerDetails extends Fragment {
    DatabaseHelper MyDb;
    ArrayList<SellerBill> mSellerBills;
    ArrayList<SellerProduct> mSellerProducts;
    String seller;

    public SellerDetails() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details_seller, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSellerBills = new ArrayList<>();
        mSellerProducts = new ArrayList<>();
        MyDb = new DatabaseHelper(this.getContext());

        final RecyclerView detailsRecycler = (RecyclerView) getView().findViewById(R.id.seller_details_recycler);
        final LinearLayoutManager listLayoutManager = new LinearLayoutManager(this.getContext());
        detailsRecycler.setLayoutManager(listLayoutManager);

        getSellerBills("muhd");
        Toast.makeText(this.getContext(), String.valueOf(mSellerBills.size()), Toast.LENGTH_SHORT).show();
        final DetailsAdapter detailsAdapter = new DetailsAdapter(this.getContext(), mSellerBills, mSellerProducts);
        detailsRecycler.setAdapter(detailsAdapter);
    }

    public void getSellerBills(String name) {
        Cursor cursor;
        int billNo;
        String date;
        int amount;
        int cash;
        int transfer;
        int bal;
        cursor = MyDb.getSellerBill(name);
        while (cursor.moveToNext()) {
            billNo = cursor.getInt(1);
            seller = cursor.getString(2);
            date = cursor.getString(3);
            amount = cursor.getInt(4);
            cash = cursor.getInt(5);
            transfer = cursor.getInt(6);
            bal = cursor.getInt(7);
            mSellerBills.add(new SellerBill(name, billNo, date, amount, cash, transfer, bal));
        }
        cursor.close();
        getSellerProducts(seller);
    }

    public void getSellerProducts(String seller) {
        Cursor cursor;
        int billNo;
        String product;
        int qty;
        int rate;
        cursor = MyDb.getProductIn(seller);
        while (cursor.moveToNext()) {
            billNo = cursor.getInt(1);
            product = cursor.getString(3);
            qty = cursor.getInt(4);
            rate = cursor.getInt(5);

            mSellerProducts.add(new SellerProduct(billNo, product, qty, rate));
        }
        cursor.close();
    }
}
