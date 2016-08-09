package com.cityconnect.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.cityconnect.R;
import com.cityconnect.adapter.ViewPagerAdapter;
import com.cityconnect.fragment.LocationFragment;
import com.cityconnect.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_action_home,
            R.drawable.ic_action_grid,
            R.drawable.ic_action_search,
            R.drawable.ic_action_notifications,
            R.drawable.ic_action_location,
            R.drawable.ic_action_home_selected,
            R.drawable.ic_action_grid_selected,
            R.drawable.ic_action_search_selected,
            R.drawable.ic_action_notifications_selected,
            R.drawable.ic_action_location_selected
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();
        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                            int numTab = tab.getPosition() + 5;

                            tab.setIcon(tabIcons[numTab]);

                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        int numTab = tab.getPosition();
                        tab.setIcon(tabIcons[numTab]);
                    }
                });

    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        tabLayout.getTabAt(4).setIcon(tabIcons[4]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),true);
        adapter.addFragment(new SearchFragment(), "Home");
        adapter.addFragment(new SearchFragment(), "Grid");
        adapter.addFragment(new SearchFragment(), "Search");
        adapter.addFragment(new SearchFragment(), "Notification");
        adapter.addFragment(new LocationFragment(), "Location");
        viewPager.setAdapter(adapter);
    }

   }
