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

import java.util.ArrayList;

import database.DatabaseHelper;
import ml.gomtricks.easystock.R;
import ml.gomtricks.easystock.SellerBill;
import ml.gomtricks.easystock.SellerProduct;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {
    final private Context mContext;
    final private LayoutInflater mLayoutInflater;
    private ArrayList<SellerBill> mSellerBills;
    private ArrayList<SellerProduct> mSellerProducts;
    private DatabaseHelper MyDb;
    private String seller;


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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.itemView.setTag(mSellerBills.get(position));
        holder.tvDate.setText(mSellerBills.get(position).getDate());
        holder.tvTotal.setText("Total: " + String.valueOf(mSellerBills.get(position).getAmount()));
        holder.tvCash.setText("Cash: " + String.valueOf(mSellerBills.get(position).getCash()));
        holder.tvTransfer.setText("Transfer: " + String.valueOf(mSellerBills.get(position).getTransfer()));
        holder.tvBalance.setText("Balance: " + String.valueOf(mSellerBills.get(position).getBalance()));
    }

    @Override
    public int getItemCount() {
        return mSellerBills.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvTotal, tvCash, tvTransfer, tvBalance, tvBalHead, tvCredit;
        private TableLayout mTableLayout;
        private LinearLayout.LayoutParams mTableRowPrams;

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

            gerTableHead();
            getProductRow();
        }

        public void gerTableHead() {
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

        public void getProductRow() {
            TextView tvProduct, tvQty, tvPrice, tvAmount;
            ArrayList<Products> products = new ArrayList<>();
            for (int i = 0; i < mSellerProducts.size(); i++) {
                if (mSellerProducts.get(i).getBillNo() == mSellerBills.get(i).getBillNo()) {
                    products.add(new Products(mSellerProducts.get(i).getProduct(), mSellerProducts.get(i).getQty(),
                            mSellerProducts.get(i).getRate()));
                }
            }
            Integer count = 0;
            while (count < 3) {
                TableRow productRow = new TableRow(mContext);
                productRow.setId(100 + count);
                productRow.setPadding(5, 5, 5, 5);
                productRow.setLayoutParams(mTableRowPrams);

                tvProduct = new TextView(mContext);
                tvQty = new TextView(mContext);
                tvPrice = new TextView(mContext);
                tvAmount = new TextView(mContext);

                tvProduct.setText("Maltina");
                tvQty.setText("200");
                tvPrice.setText("20000");
                tvAmount.setText("300000");

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

                count++;
            }

        }
    }

    public class Products {
        String name;
        int qty;
        int price;

        public Products(String name, int qty, int price) {
            this.name = name;
            this.qty = qty;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public int getQty() {
            return qty;
        }

        public int getPrice() {
            return price;
        }
    }

}