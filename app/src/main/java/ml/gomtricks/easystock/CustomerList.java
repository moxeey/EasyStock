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
import database.DatabaseHelper;


public class CustomerList extends Fragment {
    FloatingActionButton fab;
    String name;
    String phone;
    DatabaseHelper MyDb;
    ArrayList<Customer> mCustomers;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_customer, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mCustomers = new ArrayList<>();
        MyDb = new DatabaseHelper(this.getContext());

        final RecyclerView detailsRecycler = (RecyclerView) getView().findViewById(R.id.customer_recycler);
        final LinearLayoutManager listLayoutManager = new LinearLayoutManager(this.getContext());
        detailsRecycler.setLayoutManager(listLayoutManager);

        getCustomers();
        final ListAdapter listAdapter = new ListAdapter(this.getContext(), mCustomers, 0);
        detailsRecycler.setAdapter(listAdapter);

        fab = (FloatingActionButton) getView().findViewById(R.id.customer_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });
    }

    private void getCustomers() {
        mCustomers.removeAll(mCustomers);

        Cursor cursor;
        int id;
        String name;
        String phone;
        int bill;
        int bal = 0;
        int credit = 0;
        cursor = MyDb.getAllCustomer();
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
            name = cursor.getString(1);
            phone = cursor.getString(2);
            bill = cursor.getInt(3);
            int balance = cursor.getInt(4);
            if (balance < 0) {
                credit = cursor.getInt(4) * (-1);
            } else bal = cursor.getInt(4);
            mCustomers.add(new Customer(id, name, phone, bal, credit));
        }
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
                                boolean isInserted = MyDb.insertCustomer(name, phone, 0, 0);
                                if (isInserted == true) {
                                    Toast.makeText(getActivity(), "Customer Added", Toast.LENGTH_SHORT).show();
                                    getCustomers();
                                } else
                                    Toast.makeText(getActivity(), "Customer not Added", Toast.LENGTH_SHORT).show();
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
    public void onDestroyView() {
        super.onDestroyView();
        MyDb.close();
    }
}
