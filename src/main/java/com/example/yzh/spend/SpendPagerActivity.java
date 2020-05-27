package com.example.yzh.spend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

public class SpendPagerActivity extends AppCompatActivity {

    private static final String EXTRA_SPEND_ID = "com.example.yzh.moneyt1.spend_id";

    private ViewPager mViewPager;
    private List<Spend> mSpends;

    public static Intent newIntent(Context packageContext, UUID spendId){
        Intent intent = new Intent(packageContext, SpendPagerActivity.class);
        intent.putExtra(EXTRA_SPEND_ID, spendId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spend_pager);

        UUID spendId = (UUID) getIntent().getSerializableExtra(EXTRA_SPEND_ID);

        mViewPager = (ViewPager) findViewById(R.id.spend_view_pager);

        mSpends = MoneyLab.get(this).getSpends();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Spend spend = mSpends.get(position);
                return MoneyFragment.newInstance(spend.getId());
            }

            @Override
            public int getCount() {
                return mSpends.size();
            }
        });

        for(int i = 0; i<mSpends.size(); i++){
            if(mSpends.get(i).getId().equals(spendId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
