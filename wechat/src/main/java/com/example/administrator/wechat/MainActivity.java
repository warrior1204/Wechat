package com.example.administrator.wechat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.example.administrator.wechat.fragment.Fragment_contacts;
import com.example.administrator.wechat.fragment.Fragment_find;
import com.example.administrator.wechat.fragment.Fragment_me;
import com.example.administrator.wechat.fragment.Fragment_msg;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<Fragment> list_fragments;
    private List<String> list_titles;
    private Fragment_contacts fragment_contacts;
    private Fragment_find fragment_find;
    private Fragment_me fragment_me;
    private Fragment_msg fragment_msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mTabLayout = (TabLayout) findViewById(R.id.id_tablayout);
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

        list_fragments = new ArrayList<Fragment>();
        list_titles = new ArrayList<String>();
        fragment_contacts = new Fragment_contacts();
        fragment_find = new Fragment_find();
        fragment_me = new Fragment_me();
        fragment_msg = new Fragment_msg();

        list_fragments.add(fragment_contacts);
        list_fragments.add(fragment_find);
        list_fragments.add(fragment_me);
        list_fragments.add(fragment_msg);
        list_titles.add("微信");
        list_titles.add("通讯录");
        list_titles.add("发现");
        list_titles.add("我");



        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list_fragments.get(position);
            }

            @Override
            public int getCount() {
                return list_titles.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return list_titles.get(position);
            }
        };
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
