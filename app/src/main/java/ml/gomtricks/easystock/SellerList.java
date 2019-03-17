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

import adapter.ListAdapter;
import adapter.ListAdapter.ItemClicked;
import database.DatabaseHelper;

public class SellerList extends Fragment implements ItemClicked {
    FloatingActionButton fab;
    String name;
    String phone;
    DatabaseHelper MyDb;
    ArrayList<Seller> mSellers;

    public SellerList() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_seller, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSellers = new ArrayList<>();
        MyDb = new DatabaseHelper(this.getContext());

        final RecyclerView listRecycler = (RecyclerView) getView().findViewById(R.id.seller_recycler);
        final LinearLayoutManager listLayoutManager = new LinearLayoutManager(this.getContext());
        listRecycler.setLayoutManager(listLayoutManager);

        getSellers();
        final ListAdapter listAdapter = new ListAdapter(this.getContext(), mSellers, 1, 1);
        listRecycler.setAdapter(listAdapter);

        fab = (FloatingActionButton) getView().findViewById(R.id.seller_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });
    }

    private void getSellers() {
        Cursor cursor;
        int id;
        String name;
        String phone;
        int bill;
        int bal = 0;
        int credit = 0;
        cursor = MyDb.getAllSeller();
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
            name = cursor.getString(1);
            phone = cursor.getString(2);
            bill = cursor.getInt(3);
            int balance = cursor.getInt(4);
            if (balance < 0) {
                credit = cursor.getInt(4) * (-1);
            } else bal = cursor.getInt(4);
            mSellers.add(new Seller(id, name, phone, bal, credit));
        }
        cursor.close();
    }

    public void showAddDialog() {
        //get add_dialog
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View addView = layoutInflater.inflate(R.layout.new_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

        //set add_dialog to alert dialog builder
        alertDialogBuilder.setView(addView);

        final EditText etName = (EditText) addView.findViewById(R.id.et_name);
        final EditText etPhone = (EditText) addView.findViewById(R.id.et_phone);

        //set Dialog Message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("ADD",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                name = etName.getText().toString();
                                phone = etPhone.getText().toString();
                                boolean isInserted = MyDb.insertSeller(name, phone, 0, 0);
                                if (isInserted == true) {
                                    Toast.makeText(getActivity(), "Seller Added", Toast.LENGTH_SHORT).show();
                                } else
                                    Toast.makeText(getActivity(), "Seller not Added", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyDb.close();
    }


    @Override
    public void onItemClicked(String name) {

    }
}
