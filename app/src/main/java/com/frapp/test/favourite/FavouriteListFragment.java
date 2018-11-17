package com.frapp.test.favourite;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.frapp.test.R;
import com.frapp.test.data.DataModel;
import com.frapp.test.home.HomeContract;
import com.frapp.test.home.HomeFragment;
import com.frapp.test.util.Constants;
import com.frapp.test.util.Utils;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavouriteListFragment extends Fragment {

    ListAdapter mListAdapter;

    public FavouriteListFragment() {

    }

    public static FavouriteListFragment newInstance(List<DataModel> dataModelList) {
        FavouriteListFragment favouriteListFragment = new FavouriteListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.dataModelListTag, Utils.getGsonParser().toJson(dataModelList));
        favouriteListFragment.setArguments(bundle);
        return favouriteListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new ListAdapter(new ArrayList<>(0));
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.internships_and_mission_list);
        recyclerView.setAdapter(mListAdapter);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        showListData();
    }

    private void showListData() {
        String dataModelListString = getArguments().getString(Constants.dataModelListTag);
        List<DataModel> dataModelList = Utils.getGsonParser().fromJson(dataModelListString, new TypeToken<List<DataModel>>() {}.getType());

        mListAdapter.replaceData(dataModelList);
    }

    public void updateListData(List<DataModel> dataModelList){
        Bundle bundle = new Bundle();
        bundle.putString(Constants.dataModelListTag, Utils.getGsonParser().toJson(dataModelList));
        setArguments(bundle);

        mListAdapter.replaceData(dataModelList);
    }


    private static class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

        List<DataModel> mList;

        public ListAdapter(List<DataModel> list) {
            this.mList = list;
        }

        @NonNull
        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            Context context = viewGroup.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View noteView = inflater.inflate(R.layout.item_internships_missions_list, viewGroup, false);

            return new ListAdapter.ViewHolder(noteView);
        }

        @Override
        public void onBindViewHolder(@NonNull ListAdapter.ViewHolder viewHolder, int i) {
            DataModel dataModel = mList.get(i);
            viewHolder.title.setText(dataModel.getTitle());
            viewHolder.description.setText(dataModel.getDescription());
            viewHolder.views.setText(dataModel.getViews() + " Views");
            Picasso.get()
                    .load(dataModel.getLogo())
                    .into(viewHolder.logo);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public void replaceData(List<DataModel> dataModelList) {
            setList(dataModelList);
            notifyDataSetChanged();
        }

        private void setList(List<DataModel> dataModelList) {
            mList = dataModelList;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView title;

            public TextView description;

            public TextView views;

            public ImageView logo;

            public ImageView favourite;

            public ViewHolder(View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.tv_title);
                description = itemView.findViewById(R.id.tv_description);
                views = itemView.findViewById(R.id.tv_views);
                logo = itemView.findViewById(R.id.iv_logo);
                favourite = itemView.findViewById(R.id.iv_favourite);

                favourite.setVisibility(View.GONE);
            }
        }

    }

}
