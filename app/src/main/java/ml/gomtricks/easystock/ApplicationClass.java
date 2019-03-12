package ml.gomtricks.easystock;

import android.app.Application;

import java.util.ArrayList;

public class ApplicationClass extends Application {

    public static ArrayList<Customer> customers;

    @Override
    public void onCreate() {
        super.onCreate();

        customers = new ArrayList<Customer>();
//        customers.add(new Customer("Muhammad", "08033720982", 2000, 0));
//        customers.add(new Customer("Muhammad", "08033720982", 2000, 0));
//        customers.add(new Customer("Muhammad", "08033720982", 2000, 0));
    }
}
