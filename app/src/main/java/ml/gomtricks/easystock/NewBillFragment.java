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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import database.DatabaseHelper;

public class NewBillFragment extends Fragment {
    DatabaseHelper MyDb;
    List<String> products;
    List<String> customers;
    List<String> qty;
    private EditText etPrice, etQty;
    private TextView tvAvailable;
    private Spinner customerSpin, productSpinner;

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
        tvAvailable = (TextView) getActivity().findViewById(R.id.tv_available_stock);
        customerSpin = (Spinner) getActivity().findViewById(R.id.customer_spinner);
        productSpinner = (Spinner) getActivity().findViewById(R.id.product_spinner);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MyDb.close();
    }
}
