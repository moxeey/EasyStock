package ml.gomtricks.easystock;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Calendar;

import database.DatabaseHelper;

public class ReportActivity extends AppCompatActivity {
    Button btnChk;
    TextView tvFrom, tvTo, tvPurchased, tvSales, tvProfit;
    DatePickerDialog mDatePickerDialog;
    int year, month, dayOfMonth;
    Calendar mCalendar;
    DatabaseHelper MyDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        MyDb = new DatabaseHelper(this);

        btnChk = (Button) findViewById(R.id.btn_chk);
        tvFrom = (TextView) findViewById(R.id.tv_from);
        tvTo = (TextView) findViewById(R.id.tv_to);
        tvPurchased = (TextView) findViewById(R.id.tv_purchased);
        tvSales = (TextView) findViewById(R.id.sales);
        tvProfit = (TextView) findViewById(R.id.tv_profit);

        tvProfit.setVisibility(View.GONE);

        tvFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendar = Calendar.getInstance();
                year = mCalendar.get(Calendar.YEAR);
                month = mCalendar.get(Calendar.MONTH);
                dayOfMonth = mCalendar.get(Calendar.DAY_OF_WEEK);

                mDatePickerDialog = new DatePickerDialog(ReportActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String cDay = "", cMonth = "";
                        if (dayOfMonth < 10) {
                            cDay = ("0" + dayOfMonth);
                        } else {
                            cDay = String.valueOf(dayOfMonth);
                        }
                        if (month < 10) {
                            cMonth = "0" + (month + 1);
                        } else {
                            cMonth = String.valueOf(month);
                        }
                        tvFrom.setText(cDay + "-" + (cMonth) + "-" + year);
                    }
                }, year, month, dayOfMonth);
                mDatePickerDialog.show();
            }
        });
        tvTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendar = Calendar.getInstance();
                year = mCalendar.get(Calendar.YEAR);
                month = mCalendar.get(Calendar.MONTH);
                dayOfMonth = mCalendar.get(Calendar.DAY_OF_WEEK);

                mDatePickerDialog = new DatePickerDialog(ReportActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String cDay = "", cMonth = "";
                        if (dayOfMonth < 10) {
                            cDay = ("0" + dayOfMonth);
                        } else {
                            cDay = String.valueOf(dayOfMonth);
                        }
                        if (month < 10) {
                            cMonth = "0" + (month + 1);
                        } else {
                            cMonth = String.valueOf(month);
                        }
                        tvTo.setText(cDay + "-" + (cMonth) + "-" + year);
                    }
                }, year, month, dayOfMonth);
                mDatePickerDialog.show();
            }
        });

        btnChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getReport(tvFrom.getText().toString(), tvTo.getText().toString());
//                getReport("14-03-2019", "25-03-2019");
            }
        });

    }

    public void getReport(String from, String to) {
        Cursor cursor = null;
        int purchased = 0, sales = 0, profit = 0;
        try {
            cursor = MyDb.getSellerReport(from, to);
            while (cursor.moveToNext()) {
                purchased += cursor.getInt(4);
            }

        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        try {
            cursor = MyDb.getCustomerReport(from, to);
            while (cursor.moveToNext()) {
                sales += cursor.getInt(4);
            }

        } catch (SQLException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        tvPurchased.setText("Purchased: " + toNaira(purchased, "N "));
        tvSales.setText("Sales: " + toNaira(sales, "N "));
        cursor.close();
    }

    public String toNaira(int value, String symbol) {
        String naira = new DecimalFormat(value % 1 == 0 ? symbol + "###,###.##" : "###.00").format(value);
        return naira;
    }
}
