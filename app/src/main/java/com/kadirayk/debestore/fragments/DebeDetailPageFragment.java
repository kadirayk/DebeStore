package com.kadirayk.debestore.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kadirayk.debestore.R;
import com.kadirayk.debestore.model.DebeDetailItem;
import com.kadirayk.debestore.model.DebeListItem;
import com.kadirayk.debestore.network.DebeParser;
import com.kadirayk.debestore.network.NetworkController;

import java.util.ArrayList;

/**
 * Created by Kadiray on 13.04.2015.
 */
public class DebeDetailPageFragment extends Fragment implements NetworkController.OnDebeDetailResponseRecievedListener{


    public static Fragment newInstance(DebeDetailItem debeDetailItem, int position, ArrayList<DebeListItem> debeListItems){
        DebeDetailPageFragment mDebeDetailPageFragment = new DebeDetailPageFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putParcelable("DebeDetailItem", debeDetailItem);
        args.putParcelableArrayList("DebeList", debeListItems);

        mDebeDetailPageFragment.setArguments(args);

        return mDebeDetailPageFragment;
    }

    private View mView;
    private TextView fragment_debe_detail_page_title_textview;
    private TextView fragment_debe_detail_page_content_textview;
    private TextView fragment_debe_detail_page_author_textview;
    private TextView fragment_debe_detail_page_date_textview;
    private ArrayList<DebeListItem> debeListItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_debe_detail_page, container, false);

        setUI();

        String mUrl; //= getActivity().getIntent().getStringExtra("url");
        int position = getActivity().getIntent().getIntExtra("place", 1);

        debeListItems = new ArrayList<>();

        debeListItems = getActivity().getIntent().getParcelableArrayListExtra("debeList");
//        debeListItems = this.getArguments().getParcelableArrayList("debeList");
        position = this.getArguments().getInt("position") - 1;

        if(position<0){
            position = 0;
        }

        mUrl = debeListItems.get(position).getUrl();

        DebeParser mDebeParser = new DebeParser(getActivity(), this);

        mDebeParser.callDebeDetailTask("https://www.eksisozluk.com" + mUrl);

        return mView;
    }

    private void setUI(){
        fragment_debe_detail_page_title_textview = (TextView) mView.findViewById(R.id.fragment_debe_detail_page_title_textview);
        fragment_debe_detail_page_content_textview = (TextView) mView.findViewById(R.id.fragment_debe_detail_page_content_textview);
        fragment_debe_detail_page_author_textview = (TextView) mView.findViewById(R.id.fragment_debe_detail_page_author_textview);
        fragment_debe_detail_page_date_textview = (TextView) mView.findViewById(R.id.fragment_debe_detail_page_date_textview);
    }


    @Override
    public void OnDebeDetailResponseRecieved(DebeDetailItem debeDetailItem) {
            if(debeDetailItem != null){
                fragment_debe_detail_page_title_textview.setText(debeDetailItem.getTitle());
                fragment_debe_detail_page_content_textview.setText(debeDetailItem.getContent());
                fragment_debe_detail_page_author_textview.setText(debeDetailItem.getAuthor());
                fragment_debe_detail_page_date_textview.setText(debeDetailItem.getDate());
            }

            //TODO write to data source


    }
}


