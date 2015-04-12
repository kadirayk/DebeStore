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
import com.kadirayk.debestore.fragments.DebePageFragment;
import com.kadirayk.debestore.models.DebeListItem;

import java.util.ArrayList;

/**
 * Created by Kadiray on 11.04.2015.
 */
public class DebePagerActivity extends ActionBarActivity {

    private static final int NUM_PAGES = 10;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(NUM_PAGES);
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
            ArrayList<DebeListItem> mDebeListItemArrayList = new ArrayList<DebeListItem>();
            DebeListItem mDebeListItem = new DebeListItem(1, position + "title", position + "author", "url", "date");
            //TODO get archived debelist from database
            mDebeListItemArrayList.add(mDebeListItem);
            return new DebePageFragment().newInstance(mDebeListItemArrayList, position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}