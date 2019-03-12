package ml.gomtricks.easystock;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import adapter.DetailsAdapter;

public class CustomerDetails extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_customer, null);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final RecyclerView detailsRecycler = (RecyclerView) getView().findViewById(R.id.details_recycler);
        final LinearLayoutManager listLayoutManager = new LinearLayoutManager(this.getContext());
        detailsRecycler.setLayoutManager(listLayoutManager);

        final DetailsAdapter detailsAdapter = new DetailsAdapter(this.getContext());
        detailsRecycler.setAdapter(detailsAdapter);
    }

}
