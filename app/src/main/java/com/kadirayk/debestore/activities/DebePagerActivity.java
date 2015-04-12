package com.kadirayk.debestore.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.kadirayk.debestore.R;
import com.kadirayk.debestore.application.AppController;
import com.kadirayk.debestore.database.DebeDataSource;
import com.kadirayk.debestore.fragments.DebePageFragment;
import com.kadirayk.debestore.models.DebeListItem;

import java.util.ArrayList;

/**
 * Created by Kadiray on 11.04.2015.
 */
public class DebePagerActivity extends ActionBarActivity {

    private static final int NUM_PAGES = 10;
    private int debeListsCount;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private ArrayList<DebeListItem> debeListItems;
    private DebeDataSource mDebeDataSource;
    private String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);
        currentDate = AppController.getSystemDate();

        debeListsCount = AppController.getDebeListCount(this);
        if(AppController.getIfTodaysDebeListStored(this, currentDate)){

        }else{
            debeListsCount++;
            AppController.storeDebeListCount(this, debeListsCount);
        }

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(debeListsCount);
        mDebeDataSource = new DebeDataSource(this);
        mDebeDataSource.open();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.test_item){
            Toast.makeText(this, "hello test", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            DebeListItem mDebeListItem = new DebeListItem(1, "", "", "", currentDate);

            debeListItems = mDebeDataSource.getAllDebeListItems();
            if(debeListItems.size()<1){
                debeListItems.add(mDebeListItem);
                return new DebePageFragment().newInstance(debeListItems, position);
            }else{
                return new DebePageFragment().newInstance(debeListItems, position);
            }

        }

        @Override
        public int getCount() {
            return debeListsCount;
        }
    }
}