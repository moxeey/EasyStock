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

        fm.beginTransaction()
                .show(fm.findFragmentById(R.id.list_seller))
                .hide(fm.findFragmentById(R.id.details_seller))
                .commit();
    }

    @Override
    public void onItemClicked(int index) {
        fm.beginTransaction()
                .hide(fm.findFragmentById(R.id.list_seller))
                .show(fm.findFragmentById(R.id.details_seller))
                .addToBackStack(null)
                .commit();
    }
}
