package com.example.one.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.one.R;
import com.example.one.adapters.MyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/1/7.
 */
public class MainFragment extends Fragment {

    private ViewPager viewPager;
    private List<Fragment> list = new ArrayList<Fragment>();
    private List<Integer> ids;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.vp_main);
        Bundle bundle = getArguments();
        if (bundle != null) {
            ids = bundle.getIntegerArrayList("ids");
            if (ids != null && ids.size() > 0) {
                for (Integer id : ids) {
                    Fragment fragment = new MainFragmentSub();
                    Bundle argument = new Bundle();
                    argument.putInt("id", id);
                    fragment.setArguments(argument);
                    list.add(fragment);
                }
            }
        }
        viewPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), list));
        return view;
    }
}
