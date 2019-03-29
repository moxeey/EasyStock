package ml.gomtricks.easystock;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import adapter.ListAdapter;

public class SellerActivity extends AppCompatActivity implements ListAdapter.ItemClicked {
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);

        fm = this.getSupportFragmentManager();
        SellerList sellerList = new SellerList();
        fm.beginTransaction()
                .replace(R.id.container, sellerList)
                .commit();
    }

    @Override
    public void onItemClicked(String name) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        SellerDetails sellerDetails = new SellerDetails();
        sellerDetails.setArguments(bundle);

        fm.beginTransaction()
                .replace(R.id.container, sellerDetails)
                .addToBackStack(null)
                .commit();
    }
}
