package com.kadirayk.debestore.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.kadirayk.debestore.R;
import com.kadirayk.debestore.adapters.DebeListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kadiray on 11.04.2015.
 */
public class DebePageFragment extends Fragment implements OnItemClickListener {

    private View mView;
    private ListView fragment_debe_page_listview;
    private TextView fragment_debe_page_date_textview;
    private int mDate;
    private DebeListAdapter mAdapter;


    public static Fragment newInstance(Context context, int date){
        DebePageFragment mDebePageFragment = new DebePageFragment();
        Bundle args = new Bundle();
        args.putInt("date", date);
        mDebePageFragment.setArguments(args);
        return mDebePageFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_debe_page, container, false);

        setUI();
        List<String> mDebeList = new ArrayList<String>();
        mDebeList.add("1. entry");
        mDebeList.add("2. entry");
        mDebeList.add("3. entry");

        List<String> mDebeList2 = new ArrayList<String>();
        mDebeList2.add("1. dünya savaşı");
        mDebeList2.add("2. dünya savaşı");
        mDebeList2.add("3. dünya savaşı");

       // updateAdapter(mDebeList);

        mDate = this.getArguments().getInt("date");

        if(mDate == 1){
            updateAdapter(mDebeList);
        }else{
            updateAdapter(mDebeList2);
        }

        fragment_debe_page_date_textview.setText("date : " + mDate);

        return mView;
    }

    private void setUI(){
        fragment_debe_page_date_textview = (TextView) mView.findViewById(R.id.fragment_debe_page_date_textview);
        fragment_debe_page_listview = (ListView) mView.findViewById(R.id.fragment_debe_page_listview);
        fragment_debe_page_listview.setOnItemClickListener(this);
    }

    private void updateAdapter(List<String> debeList){
        mAdapter = new DebeListAdapter(getActivity(), debeList);
        fragment_debe_page_listview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}