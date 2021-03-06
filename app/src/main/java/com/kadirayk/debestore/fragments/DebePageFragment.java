package com.kadirayk.debestore.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kadirayk.debestore.R;
import com.kadirayk.debestore.activities.DebeDetailPagerActivity;
import com.kadirayk.debestore.adapters.DebeListAdapter;
import com.kadirayk.debestore.application.AppController;
import com.kadirayk.debestore.database.DebeDataSource;
import com.kadirayk.debestore.model.DebeListItem;
import com.kadirayk.debestore.network.DebeParser;
import com.kadirayk.debestore.network.NetworkController.OnDebeListResponseRecievedListener;

import java.util.ArrayList;

/**
 * Created by Kadiray on 11.04.2015.
 */
public class DebePageFragment extends Fragment implements OnItemClickListener, OnDebeListResponseRecievedListener {

    private View mView;
    private ListView fragment_debe_page_listview;
    private TextView fragment_debe_page_date_textview;
    private String mDate;
    private DebeListAdapter mAdapter;
    private DebeDataSource mDebeDataSource;
    private ArrayList<DebeListItem> mDebeListItemList;
    private ArrayList<DebeListItem> groupedDebeList;

    public static Fragment newInstance(ArrayList<DebeListItem> mDebeList, int position){
        DebePageFragment mDebePageFragment = new DebePageFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putParcelableArrayList("DebeListItems", mDebeList);
        mDebePageFragment.setArguments(args);

        return mDebePageFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_debe_page, container, false);

        setUI();

        String currentDate = AppController.getSystemDate();

        int position = this.getArguments().getInt("position");

        mDebeDataSource = new DebeDataSource(getActivity());
        mDebeDataSource.open();

        if(AppController.getIfTodaysDebeListStored(getActivity(), currentDate)){
            mDebeListItemList = mDebeDataSource.getDebeListByGroup(position + 1);
            updateAdapter(mDebeListItemList);
        }else{
            AppController.storeIfTodaysDebeListStored(getActivity(), currentDate);
            DebeParser mDebeParser = new DebeParser(getActivity(), this);
            mDebeParser.callDebeListTask();

        }

        mDebeListItemList = this.getArguments().getParcelableArrayList("DebeListItems");
        groupedDebeList = new ArrayList<>();

        for(DebeListItem debeListItem : mDebeListItemList){
            if( debeListItem.getGroup( ) == ( position + 1 ) ){
                groupedDebeList.add(debeListItem);
            }
        }

        if(groupedDebeList.isEmpty()){
            mDate = "";
        }else{
            mDate = groupedDebeList.get(0).getDate();
        }

//        updateAdapter(mDebeListItemList);

        fragment_debe_page_date_textview.setText(" " + mDate + " ");

        return mView;
    }

    private void setUI(){
        fragment_debe_page_date_textview = (TextView) mView.findViewById(R.id.fragment_debe_page_date_textview);
        fragment_debe_page_listview = (ListView) mView.findViewById(R.id.fragment_debe_page_listview);
        fragment_debe_page_listview.setOnItemClickListener(this);
    }

    private void updateAdapter(ArrayList<DebeListItem> debeList){
        mAdapter = new DebeListAdapter(getActivity(), debeList);
        fragment_debe_page_listview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(getActivity(), DebeDetailPagerActivity.class);
        intent.putExtra("url", groupedDebeList.get(position-1).getUrl());
        intent.putExtra("place", groupedDebeList.get(position-1).getPlace());
        intent.putExtra("debeItemCount", groupedDebeList.size());
        intent.putParcelableArrayListExtra("debeList", groupedDebeList);
        startActivity(intent);

    }

    @Override
    public void OnDebeListResponseRecieved(ArrayList<DebeListItem> debeListItemList) {

        if(debeListItemList.size()!=0){
            Toast.makeText(getActivity(), debeListItemList.get(0).getTitle(), Toast.LENGTH_SHORT).show();
            fragment_debe_page_date_textview.setText(debeListItemList.get(0).getDate());
            updateAdapter(debeListItemList);
        }

        for(DebeListItem debeItem : debeListItemList){
            mDebeDataSource.createDebe(debeItem.getGroup(), debeItem.getPlace(), debeItem.getTitle(), debeItem.getAuthor(), debeItem.getUrl(), debeItem.getDate());
        }

        debeListItemList  = mDebeDataSource.getAllDebeListItems();


    }
}