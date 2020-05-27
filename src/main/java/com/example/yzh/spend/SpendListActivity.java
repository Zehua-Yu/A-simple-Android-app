package com.example.yzh.spend;

import android.support.v4.app.Fragment;


public class SpendListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new SpendListFragment();
    }

}
