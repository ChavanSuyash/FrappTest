package com.frapp.test.home;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.frapp.test.R;
import com.frapp.test.data.DataModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeContract.View {

    HomeContract.UserActionsListener mActionsListener;

    private ListAdapter mListAdapter;

    public HomeFragment() {
        // Requires empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionsListener = new HomePresenter(getActivity(), this);
        mListAdapter = new ListAdapter(new ArrayList<>(0), mItemListener);
        mActionsListener.loadInternshipsAndMissions();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.internships_and_mission_list);
        recyclerView.setAdapter(mListAdapter);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        SwipeRefreshLayout swipeRefreshLayout =
                (SwipeRefreshLayout) view.findViewById(R.id.rl_list);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

        return view;
    }


    ListItemListener mItemListener = new ListItemListener() {
        @Override
        public void onItemClick(DataModel clickedItem) {

        }
    };


    @Override
    public void setProgressIndicator(boolean status) {
        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout srl =
                (SwipeRefreshLayout) getView().findViewById(R.id.rl_list);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(status);
            }
        });
    }

    @Override
    public void showInternshipsAndMissions(List<DataModel> dataModelList) {
        mListAdapter.replaceData(dataModelList);
    }


    private static class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

        List<DataModel> mList;
        ListItemListener mItemlistener;

        public ListAdapter(List<DataModel> list, ListItemListener listItemListener){
            this.mList = list;
            this.mItemlistener = listItemListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            Context context = viewGroup.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View noteView = inflater.inflate(R.layout.item_internships_missions_list, viewGroup, false);

            return new ViewHolder(noteView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
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

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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

                favourite.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                mList.remove(position);
                notifyItemRemoved(position);
            }
        }

    }

    public interface ListItemListener {

        void onItemClick(DataModel clickedItem);
    }
}
