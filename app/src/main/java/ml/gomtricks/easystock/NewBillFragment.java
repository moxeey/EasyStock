package ml.gomtricks.easystock;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import database.DatabaseHelper;

public class NewBillFragment extends Fragment {
    DatabaseHelper MyDb;
    List<String> products;
    List<String> customers;
    List<String> qty;
    Calendar mCalendar;
    private TextView tvAvailable;
    private Spinner customerSpin, productSpinner;
    Button btnCreate;
    private EditText etPrice, etQty, etCash, etTransfer;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_bill, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MyDb = new DatabaseHelper(this.getContext());

        etPrice = (EditText) getActivity().findViewById(R.id.et_price);
        etQty = (EditText) getActivity().findViewById(R.id.et_qty);
        etPrice = (EditText) getActivity().findViewById(R.id.et_price);
        etCash = (EditText) getActivity().findViewById(R.id.et_cash);
        etTransfer = (EditText) getActivity().findViewById(R.id.et_transfer);
        tvAvailable = (TextView) getActivity().findViewById(R.id.tv_available_stock);
        customerSpin = (Spinner) getActivity().findViewById(R.id.customer_spinner);
        productSpinner = (Spinner) getActivity().findViewById(R.id.product_spinner);
        btnCreate = (Button) getActivity().findViewById(R.id.btn_create);

        mCalendar = Calendar.getInstance();
        products = new ArrayList<>();
        customers = new ArrayList<>();
        qty = new ArrayList<>();

        fillSpinner();

        productSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String product = productSpinner.getSelectedItem().toString();
                tvAvailable.setText("Available: " + qty.get(products.indexOf(product)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        addBill();
    }

    public void fillSpinner() {
        final ArrayAdapter<String> customerSpinAdapter;
        final ArrayAdapter<String> ProductSpinAdapter;
        Cursor result;

        result = MyDb.getAllStock();
        while (result.moveToNext()) {
            products.add(result.getString(1));
            qty.add(result.getString(2));
        }
        result = MyDb.getAllCustomer();
        while (result.moveToNext()) {
            customers.add(result.getString(1));
        }
        result.close();

        customerSpinAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, customers);
        ProductSpinAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, products);

        productSpinner.setAdapter(ProductSpinAdapter);
        customerSpin.setAdapter(customerSpinAdapter);

    }

    public void addBill() {
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product = productSpinner.getSelectedItem().toString();
                int qty = Integer.parseInt(etQty.getText().toString());
                String customer = customerSpin.getSelectedItem().toString();
                int price = Integer.parseInt(etPrice.getText().toString());
                int cash = Integer.parseInt(etCash.getText().toString());
                int transfer = Integer.parseInt(etTransfer.getText().toString());

                int paid = cash + transfer;
                int amount = price * qty;
                int balance = amount - paid;

                boolean isInserted = MyDb.updateStock(product, -qty);
                if (isInserted == true) {
                    Toast.makeText(getActivity(), "Stock Added", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getActivity(), "Stock not Added", Toast.LENGTH_SHORT).show();
                isInserted = MyDb.insertCustomerBill(getCustomerBillNo() + 1, customer, amount, cash, transfer, balance, getDate());
                if (isInserted == true) {
                    Toast.makeText(getActivity(), "CustomerBill Created", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getActivity(), "CustomerBill not Created", Toast.LENGTH_SHORT).show();
                isInserted = MyDb.insertProductOut(getCustomerBillNo(), product, qty, price, customer);
                if (isInserted == true) {
                    Toast.makeText(getActivity(), "product added", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getActivity(), "product not added", Toast.LENGTH_SHORT).show();
                isInserted = MyDb.updateCustomer(customer, amount, balance);
                if (isInserted == true) {
                    Toast.makeText(getActivity(), "customer updated", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getActivity(), "customer not updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int getCustomerBillNo() {
        Cursor cursor;
        int num = 0;
        try {
            cursor = MyDb.getCustomerBillNo();
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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MyDb.close();
    }
}
