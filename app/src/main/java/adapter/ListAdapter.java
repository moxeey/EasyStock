package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ml.gomtricks.easystock.Customer;
import ml.gomtricks.easystock.R;
import ml.gomtricks.easystock.Seller;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final int LIST_FRAGMENT;
    ArrayList<Customer> mCustomers;
    ArrayList<Seller> mSellers;
    private ItemClicked activity;

    public ListAdapter(Context context, ArrayList<Customer> customers, int list_fragment) {
        mContext = context;
        mCustomers = customers;
        LIST_FRAGMENT = list_fragment;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.activity = (ItemClicked) context;
    }

    public ListAdapter(Context context, ArrayList<Seller> sellers, int list_fragment, int a) {
        mContext = context;
        mSellers = sellers;
        LIST_FRAGMENT = list_fragment;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.activity = (ItemClicked) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.list_card, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        switch (LIST_FRAGMENT) {
            case 0: {
                holder.itemView.setTag(mCustomers.get(position));
                holder.tvName.setText(mCustomers.get(position).getName().toUpperCase());
                holder.tvPhone.setText(holder.tvPhone.getText() + mCustomers.get(position).getPhone());
                holder.tvBal.setText(holder.tvBal.getText() + String.valueOf(mCustomers.get(position).getBf()));
                holder.tvCredit.setText(holder.tvCredit.getText() + String.valueOf(mCustomers.get(position).getCb()));
                break;
            }
            case 1: {
                holder.itemView.setTag(mSellers.get(position));
                holder.tvName.setText(mSellers.get(position).getName().toUpperCase());
                holder.tvPhone.setText(holder.tvPhone.getText() + mSellers.get(position).getPhone());
                holder.tvBal.setText(holder.tvBal.getText() + String.valueOf(mSellers.get(position).getBf()));
                holder.tvCredit.setText(holder.tvCredit.getText() + String.valueOf(mSellers.get(position).getCb()));
                break;
            }
        }

    }

    @Override
    public int getItemCount() {
        int size = 0;
        switch (LIST_FRAGMENT) {
            case 0:
                size = mCustomers.size();
                break;
            case 1:
                size = mSellers.size();
                break;
        }
        return size;
    }

    public interface ItemClicked {
        void onItemClicked(String name);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvPhone, tvBal, tvCredit;

        public ViewHolder(final View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
            tvBal = (TextView) itemView.findViewById(R.id.tv_bf);
            tvCredit = (TextView) itemView.findViewById(R.id.tv_cb);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (LIST_FRAGMENT) {
                        case 0:
                            activity.onItemClicked(mCustomers.get(getAdapterPosition()).getName());
                            break;
                        case 1:
                            activity.onItemClicked(mSellers.get(getAdapterPosition()).getName());
                            break;
                    }

                }
            });
        }
    }

}
