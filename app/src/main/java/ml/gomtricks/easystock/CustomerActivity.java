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

        fm.beginTransaction()
                .show(fm.findFragmentById(R.id.list_fragment))
                .hide(fm.findFragmentById(R.id.detail_fragment))
                .commit();
    }


    @Override
    public void onItemClicked(int index) {
        fm.beginTransaction()
                .hide(fm.findFragmentById(R.id.list_fragment))
                .show(fm.findFragmentById(R.id.detail_fragment))
                .addToBackStack(null)
                .commit();
    }
}
