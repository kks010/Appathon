package com.kks.kunal.appathon;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TabLayout mTabLayout = (TabLayout)findViewById(R.id.tabs);
        mTabLayout.addTab(mTabLayout.newTab().setText("Random Facts"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Quest Facts"));
        mTabLayout.setTabGravity(mTabLayout.GRAVITY_FILL);

        //connecting adapter
        PageAdapter adapter = new PageAdapter(getSupportFragmentManager());
        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setScrollPosition(position,0,true);
                mTabLayout.setSelected(true);
            }
        });


        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private class PageAdapter extends FragmentPagerAdapter {

        public PageAdapter(FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public int getCount()
        {
            return 2;
        }
        @Override
        public Fragment getItem(int position)
        {
            switch(position)
            {
                case 0: return new RandomFactsFragment();
                case 1: return new QuestFactsFragment();
                default : return new RandomFactsFragment();
            }
        }
    }
}
