package ml.gomtricks.easystock;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import adapter.ListAdapter;

public class CustomerActivity extends AppCompatActivity implements ListAdapter.ItemClicked {

    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        fm = this.getSupportFragmentManager();

        CustomerList customerList = new CustomerList();
        fm.beginTransaction()
                .replace(R.id.customer_container, customerList)
                .commit();
    }



    @Override
    public void onItemClicked(String name) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setArguments(bundle);

        fm.beginTransaction()
                .replace(R.id.customer_container, customerDetails)
                .addToBackStack(null)
                .commit();
    }
}
