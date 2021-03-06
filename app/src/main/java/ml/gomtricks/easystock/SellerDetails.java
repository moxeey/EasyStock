package ml.gomtricks.easystock;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import adapter.DetailsAdapter;
import database.DatabaseHelper;

public class SellerDetails extends Fragment {
    DatabaseHelper MyDb;
    ArrayList<SellerBill> mSellerBills;
    ArrayList<SellerProduct> mSellerProducts;
    String seller;
    FloatingActionButton addFab, viewFab;
    private int amount;

    public SellerDetails() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null)
            seller = bundle.getString("name");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details_seller, null);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        addFab = (FloatingActionButton) getView().findViewById(R.id.seller_cash_fab);
        viewFab = (FloatingActionButton) getView().findViewById(R.id.seller_dep_fab);
        mSellerBills = new ArrayList<>();
        mSellerProducts = new ArrayList<>();
        MyDb = new DatabaseHelper(this.getContext());

        final RecyclerView detailsRecycler = (RecyclerView) getView().findViewById(R.id.seller_details_recycler);
        final LinearLayoutManager listLayoutManager = new LinearLayoutManager(this.getContext());
        detailsRecycler.setLayoutManager(listLayoutManager);

        getSellerBills(seller);
        final DetailsAdapter detailsAdapter = new DetailsAdapter(this.getContext(), mSellerBills, mSellerProducts, 0);
        detailsRecycler.setAdapter(detailsAdapter);

        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCashDialog();
            }
        });
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

            mSellerProducts.add(new SellerProduct(billNo, seller, product, qty, rate));
        }
        cursor.close();
    }

    public void showAddCashDialog() {
        //get add_dialog
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View addView = layoutInflater.inflate(R.layout.add_cash_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

        //set add_dialog to alert dialog builder
        alertDialogBuilder.setView(addView);

        final EditText amt = (EditText) addView.findViewById(R.id.et_amount);

        //set Dialog Message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("ADD",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                amount = Integer.parseInt(amt.getText().toString());
                                boolean isInserted = MyDb.sellerAddCash(seller, amount);
                                if (isInserted == true) {
                                    Toast.makeText(getActivity(), "Cash Added", Toast.LENGTH_SHORT).show();
//                                    getSellerBills(seller);
                                } else
                                    Toast.makeText(getActivity(), "Cash not Added", Toast.LENGTH_SHORT).show();
                            }
                        })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
