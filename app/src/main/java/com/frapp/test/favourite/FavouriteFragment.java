package com.frapp.test.favourite;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frapp.test.R;
import com.frapp.test.data.DataModel;
import com.frapp.test.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FavouriteFragment extends Fragment implements FavouriteContract.View {

    private FavouriteContract.UserActionsListener mActionListener;

    private ViewPagerAdapter mViewPagerAdapter;

    Map<String, List<DataModel>> mFavouriteListMap;

    public FavouriteFragment() {

    }

    public static FavouriteFragment newInstance() {
        return new FavouriteFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionListener = new FavouritePresenter(getActivity(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        ViewPager vpFavourite = view.findViewById(R.id.vp_favourite_list);
        vpFavourite.setAdapter(mViewPagerAdapter);

        return view;
    }

    @Override
    public void onResume() {
        mActionListener.loadFavouriteList();
        super.onResume();
    }

    @Override
    public void showFavouriteList(Map<String, List<DataModel>> dataModelListMap) {
        mFavouriteListMap = dataModelListMap;

        List<Fragment> fragmentList = new ArrayList<>();
        List<String> pageTitleStringList = new ArrayList<>();

        for (Map.Entry<String, List<DataModel>> entry : dataModelListMap.entrySet()) {

            FavouriteListFragment favouriteListFragment = FavouriteListFragment.newInstance(dataModelListMap.get(entry.getKey()));
            fragmentList.add(favouriteListFragment);
            pageTitleStringList.add(entry.getKey());

        }

        mViewPagerAdapter.replaceFragmentList(fragmentList);
        mViewPagerAdapter.replacePageTitleList(pageTitleStringList);

        mViewPagerAdapter.notifyDataSetChanged();
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> mFragmentList = new ArrayList<>();
        private List<String> mFragmentPageTitle = new ArrayList<>();
        private FragmentManager mFragmentManager;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.mFragmentManager =fm;
        }

        @Override
        public Fragment getItem(int i) {
            return mFragmentList.get(i);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            if(position <= mFragmentManager.getFragments().size() - 1 ) {
                FavouriteListFragment favouriteListFragment;
                switch (position) {
                    case 0:
                        favouriteListFragment = (FavouriteListFragment) mFragmentManager.getFragments().get(position);
                        favouriteListFragment.updateListData(mFavouriteListMap.get(Constants.internships));
                        break;
                    case 1:
                        favouriteListFragment = (FavouriteListFragment) mFragmentManager.getFragments().get(position);
                        favouriteListFragment.updateListData(mFavouriteListMap.get(Constants.missions));
                        break;
                }
            }
            return super.instantiateItem(container, position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        private void replaceFragmentList(List<Fragment> fragmentList) {
            this.mFragmentList = fragmentList;
        }

        private void replacePageTitleList(List<String> pageTitleList) {
            this.mFragmentPageTitle = pageTitleList;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentPageTitle.get(position);
        }
    }


}


