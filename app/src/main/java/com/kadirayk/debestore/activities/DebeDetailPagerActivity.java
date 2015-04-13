package com.kadirayk.debestore.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.kadirayk.debestore.R;
import com.kadirayk.debestore.fragments.DebeDetailPageFragment;
import com.kadirayk.debestore.models.DebeDetailItem;

import java.util.ArrayList;

/**
 * Created by Kadiray on 13.04.2015.
 */
public class DebeDetailPagerActivity extends ActionBarActivity{

    private int debeListItemCount = 10;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private DebeDetailItem debeDetailItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debe_detail_pager);

        mPager = (ViewPager) findViewById(R.id.debe_detail_pager);

    }


    private class ScreenSlideDebeDetailPagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlideDebeDetailPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            DebeDetailItem mDetailItem = new DebeDetailItem(0,0,"title","content","author","date");

            return new DebeDetailPageFragment().newInstance(mDetailItem, position);

        }

        @Override
        public int getCount() {
            return debeListItemCount;
        }
    }
}
