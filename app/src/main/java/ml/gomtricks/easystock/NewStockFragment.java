package ml.gomtricks.easystock;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import database.DatabaseHelper;

public class NewStockFragment extends Fragment {
    EditText et_qty;
    EditText et_rate;
    EditText et_cash;
    EditText et_transfer;
    Button btnAdd;
    Button btnView;
    Button btnCreate;
    Calendar mCalendar;
    DatabaseHelper MyDb;
    Spinner sellerSpinner, productSpinner;
    private ArrayList<String> productsList;
    private ArrayList<String> sellerList;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_stock, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sellerList = new ArrayList<>();
        productsList = new ArrayList<>();
        MyDb = new DatabaseHelper(this.getContext());
        mCalendar = Calendar.getInstance();

        et_qty = (EditText) this.getActivity().findViewById(R.id.qty);
        et_rate = (EditText) this.getActivity().findViewById(R.id.rate);
        et_cash = (EditText) this.getActivity().findViewById(R.id.cash);
        et_transfer = (EditText) this.getActivity().findViewById(R.id.transfer);
        btnAdd = (Button) this.getActivity().findViewById(R.id.btn_save);
        btnView = (Button) this.getActivity().findViewById(R.id.btn_view);
        btnCreate = (Button) this.getActivity().findViewById(R.id.btn_stock_create);
        sellerSpinner = (Spinner) this.getActivity().findViewById(R.id.seller_spinner);
        productSpinner = (Spinner) this.getActivity().findViewById(R.id.stock_spinner);

        fillSpinner();
        addStock();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });
    }

    public void addStock() {
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product = productSpinner.getSelectedItem().toString();
                int qty = Integer.parseInt(et_qty.getText().toString());
                String seller = sellerSpinner.getSelectedItem().toString();
                int rate = Integer.parseInt(et_rate.getText().toString());
                int cash = Integer.parseInt(et_cash.getText().toString());
                int transfer = Integer.parseInt(et_transfer.getText().toString());

                int paid = cash + transfer;
                int amount = rate * qty;
                int balance = amount - paid;

                boolean isInserted = MyDb.updateStock(product, qty);
                if (isInserted == true) {
                    Toast.makeText(getActivity(), "Stock Added", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getActivity(), "Stock not Added", Toast.LENGTH_SHORT).show();
                isInserted = MyDb.insertSellerBill(getSellerBillNo() + 1, seller, amount, cash, transfer, balance, getDate());
                if (isInserted == true) {
                    Toast.makeText(getActivity(), "SellerBill Created", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getActivity(), "SellerBill not Created", Toast.LENGTH_SHORT).show();
                isInserted = MyDb.insertProductIn(getSellerBillNo(), product, qty, rate, seller);
                if (isInserted == true) {
                    Toast.makeText(getActivity(), "product added", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getActivity(), "product not added", Toast.LENGTH_SHORT).show();
                isInserted = MyDb.updateSeller(seller, amount, balance);
                if (isInserted == true) {
                    Toast.makeText(getActivity(), "seller updated", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getActivity(), "seller not updated", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public int getSellerBillNo() {
        Cursor cursor;
        int num = 0;
        try {
            cursor = MyDb.getSellerBillNo();
            while (cursor.moveToNext()) {
                num = cursor.getInt(0);
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return num;
    }
    public String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String date = format.format(mCalendar.getTime());
        return date;
    }

    public void showAddDialog() {
        //get add_dialog
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View addView = layoutInflater.inflate(R.layout.new_product_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

        //set add_dialog to alert dialog builder
        alertDialogBuilder.setView(addView);

        final EditText etProduct = (EditText) addView.findViewById(R.id.et_product);

        //set Dialog Message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("ADD",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String product = etProduct.getText().toString();
                                boolean isInserted = MyDb.insertStock(product, 0, 0);
                                if (isInserted == true) {
                                    Toast.makeText(getActivity(), "Product Added", Toast.LENGTH_SHORT).show();
                                } else
                                    Toast.makeText(getActivity(), "Product not Added", Toast.LENGTH_SHORT).show();
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

    public void fillSpinner() {
        final ArrayAdapter<String> sellerSpinAdapter;
        final ArrayAdapter<String> ProductSpinAdapter;
        Cursor result;

        result = MyDb.getAllStock();
        while (result.moveToNext()) {
            productsList.add(result.getString(1));
        }
        result = MyDb.getAllSeller();
        while (result.moveToNext()) {
            sellerList.add(result.getString(1));
        }
        result.close();

        sellerSpinAdapter = new ArrayAdapter<String>((this.getContext()), android.R.layout.simple_spinner_dropdown_item, sellerList);
        ProductSpinAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, productsList);

        productSpinner.setAdapter(ProductSpinAdapter);
        sellerSpinner.setAdapter(sellerSpinAdapter);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MyDb.close();
    }
}
