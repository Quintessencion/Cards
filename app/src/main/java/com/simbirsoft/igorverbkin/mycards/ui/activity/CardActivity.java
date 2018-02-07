package com.simbirsoft.igorverbkin.mycards.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.simbirsoft.igorverbkin.mycards.R;
import com.simbirsoft.igorverbkin.mycards.ui.fragment.AllCardsFragment;
import com.simbirsoft.igorverbkin.mycards.ui.fragment.MyCardsFragment;
import com.simbirsoft.igorverbkin.mycards.ui.fragment.SectionsPagerAdapter;

public class CardActivity extends MvpAppCompatActivity {

    public static final String LOG_TAG = "HunterCards";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        ViewPager viewPager = findViewById(R.id.container);
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MyCardsFragment());
        adapter.addFragment(new AllCardsFragment());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }
}
