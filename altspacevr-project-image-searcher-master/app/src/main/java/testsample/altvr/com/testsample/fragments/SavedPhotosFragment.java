package testsample.altvr.com.testsample.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;



import java.util.ArrayList;

import testsample.altvr.com.testsample.R;
import testsample.altvr.com.testsample.adapter.ItemsListAdapter;
import testsample.altvr.com.testsample.adapter.SavedListAdapter;
import testsample.altvr.com.testsample.events.ApiErrorEvent;
import testsample.altvr.com.testsample.events.PhotosEvent;
import testsample.altvr.com.testsample.service.ApiService;
import testsample.altvr.com.testsample.util.DatabaseUtil;
import testsample.altvr.com.testsample.util.LogUtil;
import testsample.altvr.com.testsample.vo.PhotoVo;

/**
 * Created by kiran on 9/13/2016.
 */
public class SavedPhotosFragment extends Fragment {

    private LogUtil log = new LogUtil(PhotosFragment.class);
    private LinearLayout fetchingItems;
    private RecyclerView itemsListRecyclerView;


    private ArrayList<String> mItemsData = new ArrayList<>();
    private SavedListAdapter mListAdapter;
    private DatabaseUtil mDatabaseUtil;

    public static SavedPhotosFragment newInstance() {
        return new SavedPhotosFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);
        LogUtil.log("HAVE UC ALLED --- ? ");
        initViews(view);
        return view;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mDatabaseUtil = new DatabaseUtil(getActivity());
        mItemsData = (ArrayList<String>) mDatabaseUtil.getImages();
        LogUtil.log("DID U COME HERE "+mDatabaseUtil.getImages());
        setupViews();
    }


    private void initViews(View view) {
        fetchingItems = (LinearLayout) view.findViewById(R.id.listEmptyView);
        itemsListRecyclerView = (RecyclerView) view.findViewById(R.id.photosListRecyclerView);
    }

    private void setupViews() {
        fetchingItems.setVisibility(View.GONE);
        setupItemsList();

    }

    private void setupItemsList() {
        LogUtil.log(" HELLO WORLD ");
        itemsListRecyclerView.setHasFixedSize(true);
        mListAdapter = new SavedListAdapter(mItemsData, getResources().getDisplayMetrics().widthPixels, getContext());
        mListAdapter.setHasStableIds(true);
        itemsListRecyclerView.setAdapter(mListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        itemsListRecyclerView.setLayoutManager(linearLayoutManager);
        itemsListRecyclerView.setVisibility(View.VISIBLE);
    }



    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mItemsData.clear();
        mListAdapter = null;
    }



}
