package adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ml.gomtricks.easystock.R;
import ml.gomtricks.easystock.SellerBill;
import ml.gomtricks.easystock.SellerProduct;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {
    final private Context mContext;
    final private LayoutInflater mLayoutInflater;
    private ArrayList<SellerBill> mSellerBills;
    private ArrayList<SellerProduct> mSellerProducts;
    public TableLayout mTableLayout;
    public LinearLayout.LayoutParams mTableRowPrams;
    boolean firstBindBind = true;
    private int currentPosition;


    public DetailsAdapter(Context context, ArrayList<SellerBill> sellerBills, ArrayList<SellerProduct> sellerProducts) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mSellerBills = sellerBills;
        mSellerProducts = sellerProducts;
    }

    public DetailsAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.details_card, parent, false);
        return new ViewHolder(itemView);
    }

    private boolean firstBind = true;

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.itemView.setTag(mSellerBills.get(position));
        holder.tvDate.setText(mSellerBills.get(position).getDate());
        holder.tvTotal.setText("Total: " + String.valueOf(mSellerBills.get(position).getAmount()));
        holder.tvCash.setText("Cash: " + String.valueOf(mSellerBills.get(position).getCash()));
        holder.tvTransfer.setText("Transfer: " + String.valueOf(mSellerBills.get(position).getTransfer()));
        holder.tvBalance.setText("Balance: " + String.valueOf(mSellerBills.get(position).getBalance()));
        currentPosition = position;

    }

    @Override
    public int getItemCount() {
        return mSellerBills.size();
    }

    public void getTableHead() {
        TextView lblProduct, lblQty, lblPrice, lblAmount;

        // Row Header
        TableRow tr_head = new TableRow(mContext);
        tr_head.setBackgroundColor(Color.GRAY);
        tr_head.setLayoutParams(mTableRowPrams);

        lblProduct = new TextView(mContext);
        lblProduct.setText("Product");
        lblProduct.setPadding(5, 5, 5, 5);
        tr_head.addView(lblProduct);

        lblQty = new TextView(mContext);
        lblQty.setText("Quality");
        lblQty.setPadding(5, 5, 5, 5);
        tr_head.addView(lblQty);

        lblPrice = new TextView(mContext);
        lblPrice.setText("Price");
        lblPrice.setPadding(5, 5, 5, 5);
        tr_head.addView(lblPrice);

        lblAmount = new TextView(mContext);
        lblAmount.setText("Amount");
        lblAmount.setPadding(5, 5, 5, 5);
        tr_head.addView(lblAmount);

        mTableLayout.addView(tr_head, mTableRowPrams);

//            set Params for Cell Elements
        TableRow.LayoutParams cellParams = new TableRow.LayoutParams(0, mTableRowPrams.width);
        cellParams.weight = 1;
        lblProduct.setLayoutParams(cellParams);
        lblQty.setLayoutParams(cellParams);
        lblPrice.setLayoutParams(cellParams);
        lblAmount.setLayoutParams(cellParams);
    }

    public void getProductRow(int position) {


//        while(counter < mSellerBills.size()) {
        for (int j = 0; j < mSellerProducts.size(); j++) {
            if (mSellerProducts.get(j).getBillNo() == mSellerBills.get(position).getBillNo()) {
                Toast.makeText(mContext, String.valueOf(mSellerProducts.get(j).getBillNo()) + " item at: " +
                                String.valueOf(position),
                        Toast.LENGTH_SHORT).show();
                if (firstBind) {
                    j = -1;
                    firstBind = false;
                }
                TextView tvProduct, tvQty, tvPrice, tvAmount;

                TableRow productRow = new TableRow(mContext);
                productRow.setId(j);
                productRow.setPadding(5, 5, 5, 5);
                productRow.setLayoutParams(mTableRowPrams);

                tvProduct = new TextView(mContext);
                tvQty = new TextView(mContext);
                tvPrice = new TextView(mContext);
                tvAmount = new TextView(mContext);

                tvProduct.setText(mSellerProducts.get(j + 1).getProduct());
                tvQty.setText(String.valueOf(mSellerProducts.get(j + 1).getQty()));
                tvPrice.setText(String.valueOf(mSellerProducts.get(j + 1).getRate()));
                tvAmount.setText(String.valueOf(mSellerProducts.get(j + 1).getQty() * mSellerProducts.get(j + 1).getRate()));

                productRow.addView(tvProduct);
                productRow.addView(tvQty);
                productRow.addView(tvPrice);
                productRow.addView(tvAmount);

                mTableLayout.addView(productRow, mTableRowPrams);
                TableRow.LayoutParams cellParams = new TableRow.LayoutParams(0, mTableRowPrams.width);
                cellParams.weight = 1;
                tvProduct.setLayoutParams(cellParams);
                tvQty.setLayoutParams(cellParams);
                tvPrice.setLayoutParams(cellParams);
                tvAmount.setLayoutParams(cellParams);


            }

        }
//            counter ++;
//        }
        Toast.makeText(mContext, "rowProduct is called", Toast.LENGTH_SHORT).show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvTotal, tvCash, tvTransfer, tvBalance;

        @SuppressLint("ResourceAsColor")
        public ViewHolder(View itemView) {
            super(itemView);

            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvTotal = (TextView) itemView.findViewById(R.id.tv_total);
            tvCash = (TextView) itemView.findViewById(R.id.tv_cash);
            tvTransfer = (TextView) itemView.findViewById(R.id.tv_transfer);
            tvBalance = (TextView) itemView.findViewById(R.id.tv_balance);
            mTableLayout = (TableLayout) itemView.findViewById(R.id.details_table);
            mTableRowPrams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            getTableHead();
            getProductRow(currentPosition);
        }
    }


}