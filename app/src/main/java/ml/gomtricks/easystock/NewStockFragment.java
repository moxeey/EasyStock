package ml.gomtricks.easystock;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
    EditText et_product;
    EditText et_qty;
    EditText et_rate;
    EditText et_cash;
    EditText et_transfer;
    Button btnAdd;
    Button btnView;
    Calendar mCalendar;
    DatabaseHelper MyDb;
    Spinner sellerSpinner;
    private ArrayList<String> seller;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_stock, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        seller = new ArrayList<>();
        MyDb = new DatabaseHelper(this.getContext());
        mCalendar = Calendar.getInstance();

        et_product = (EditText) this.getActivity().findViewById(R.id.product);
        et_qty = (EditText) this.getActivity().findViewById(R.id.qty);
        et_rate = (EditText) this.getActivity().findViewById(R.id.rate);
        et_cash = (EditText) this.getActivity().findViewById(R.id.cash);
        et_transfer = (EditText) this.getActivity().findViewById(R.id.transfer);
        btnAdd = (Button) this.getActivity().findViewById(R.id.btn_save);
        btnView = (Button) this.getActivity().findViewById(R.id.btn_view);
        sellerSpinner = (Spinner) this.getActivity().findViewById(R.id.seller_spinner);
        fillSpinner();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStock();
            }
        });
    }

    private void fillSpinner() {

        final ArrayAdapter sellerSpinAdapter;
        Cursor result;

        result = MyDb.getAllSeller();
        while (result.moveToNext()) {
            seller.add(result.getString(1));
        }
        result.close();

        sellerSpinAdapter = new ArrayAdapter<String>((this.getContext()), android.R.layout.simple_spinner_dropdown_item, seller);

        sellerSpinner.setAdapter(sellerSpinAdapter);

    }

    public void addStock() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product = et_product.getText().toString();
                int qty = Integer.parseInt(et_qty.getText().toString());
                String seller = sellerSpinner.getSelectedItem().toString();
                int rate = Integer.parseInt(et_rate.getText().toString());
                int cash = Integer.parseInt(et_cash.getText().toString());
                int transfer = Integer.parseInt(et_transfer.getText().toString());

                int paid = cash + transfer;
                int amount = rate * qty;
                int balance = amount - paid;

                boolean isInserted = MyDb.insertStock(product, qty, rate);
                if (isInserted == true) {
                    Toast.makeText(getActivity(), "Stock Added", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getActivity(), "Stock not Added", Toast.LENGTH_SHORT).show();
                isInserted = MyDb.insertSellerBill(getSellerBillNo(), seller, amount, cash, transfer, balance, getDate());
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

    public int getCustomerBillNo() {
        Cursor cursor;
        cursor = MyDb.getCustomerBillNo();
        return cursor.getInt(1) + 1;
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
        return num + 1;
    }

    public String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String date = format.format(mCalendar.getTime());
        return date;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MyDb.close();
    }
}
