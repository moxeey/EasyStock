package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ml.gomtricks.easystock.R;
import ml.gomtricks.easystock.Seller;

public class SellerAdapter extends RecyclerView.Adapter<SellerAdapter.ViewHolder> {
    ItemClicked activity;
    private ArrayList<Seller> sellers;

    public SellerAdapter(Context activity, ArrayList<Seller> sellers) {
        this.sellers = sellers;
        this.activity = (ItemClicked) activity;
    }

    @NonNull
    @Override
    public SellerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SellerAdapter.ViewHolder holder, int position) {
//        holder.itemView.setTag(sellers.get(position));
//        holder.tvName.setText(sellers.get(position).getName());
//        holder.tvPhone.setText(sellers.get(position).getPhone());
        //  holder.tvCB.setText(sellers.get(position).getCb());
//        holder.tvBF.setText(sellers.get(position).getBf());

    }

    @Override
    public int getItemCount() {
        return sellers.size();
//    return 3;
    }

    public interface ItemClicked {
        void onItemClicked(int index);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone, tvBF, tvCB;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
            tvBF = (TextView) itemView.findViewById(R.id.tv_bf);
            tvCB = (TextView) itemView.findViewById(R.id.tv_cb);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked((sellers.indexOf((Seller) v.getTag())));
                }
            });
        }
    }

}
