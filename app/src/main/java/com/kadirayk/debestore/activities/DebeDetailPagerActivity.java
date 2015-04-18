package com.kadirayk.debestore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.kadirayk.debestore.R;
import com.kadirayk.debestore.fragments.DebeDetailPageFragment;
import com.kadirayk.debestore.model.DebeDetailItem;
import com.kadirayk.debestore.model.DebeListItem;

import java.util.ArrayList;

/**
 * Created by Kadiray on 13.04.2015.
 */
public class DebeDetailPagerActivity extends ActionBarActivity{

    private int debeListItemCount;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private DebeDetailItem debeDetailItem;
    private ArrayList<DebeListItem> mDebeListItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debe_detail_pager);

        Intent intent = getIntent();
        intent.getStringExtra("url");
        int currentItemPosition = intent.getIntExtra("place", 1);
        debeListItemCount = intent.getIntExtra("debeItemCount", 1);

        mDebeListItems = new ArrayList<>();
        mDebeListItems = intent.getParcelableArrayListExtra("debeList");

        mPager = (ViewPager) findViewById(R.id.debe_detail_pager);
        mPagerAdapter = new ScreenSlideDebeDetailPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        //TODO get current position from intent
        mPager.setCurrentItem(currentItemPosition);

    }


    private class ScreenSlideDebeDetailPagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlideDebeDetailPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            DebeDetailItem mDetailItem = new DebeDetailItem(position,1,"title","content","author", "content", "date");

            return new DebeDetailPageFragment().newInstance(mDetailItem, position, mDebeListItems);

        }

        @Override
        public int getCount() {
            return debeListItemCount;
        }
    }
}
