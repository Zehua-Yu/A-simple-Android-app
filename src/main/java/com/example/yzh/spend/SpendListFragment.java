package com.example.yzh.spend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SpendListFragment extends Fragment {

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private RecyclerView mSpendRecyclerView;
    private SpendAdapter mAdapter;
    private boolean mSubtitleVisible;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spend_list, container, false);

        mSpendRecyclerView = (RecyclerView) view
                .findViewById(R.id.spend_recycler_view);
        mSpendRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(savedInstanceState != null){
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_spend_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if(mSubtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.new_spend:
                Spend spend = new Spend();
                MoneyLab.get(getActivity()).addSpend(spend);
                Intent intent = SpendPagerActivity
                        .newIntent(getActivity(), spend.getId());
                startActivity(intent);
                return true;
            case R.id.show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
                default:
                    return super.onOptionsItemSelected(item);

        }
    }

    private void updateSubtitle(){
        MoneyLab moneyLab = MoneyLab.get(getActivity());
        int spendCount = moneyLab.getSpends().size();
        String subtitle = getString(R.string.subtitle_formate, spendCount);

        if(!mSubtitleVisible){
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void updateUI() {
        MoneyLab moneyLab = MoneyLab.get(getActivity());
        List<Spend> spends = moneyLab.getSpends();

        if(mAdapter == null) {
            mAdapter = new SpendAdapter(spends);
            mSpendRecyclerView.setAdapter(mAdapter);
        }else {
            mAdapter.setSpends(spends);
            mAdapter.notifyDataSetChanged();
        }


        updateSubtitle();
    }

    private class SpendHolder extends RecyclerView.ViewHolder
            implements  View.OnClickListener{

        private Spend mSpend;

        private TextView mTypeTextView;
        private TextView mSpendCost;
        private TextView mDateTextView;
        private TextView mSpendDetail;
        private TextView mAddress;
        //private TextView mDateTextViewList;

        public SpendHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_spend, parent,false));
            itemView.setOnClickListener(this);

            mTypeTextView = (TextView) itemView.findViewById(R.id.spend_type);
            //mDateTextView = (TextView) itemView.findViewById(R.id.date_show1);
            mSpendCost = (TextView) itemView.findViewById(R.id.spend_cost);
            mSpendDetail = (TextView) itemView.findViewById(R.id.spend_detail);
            mAddress = (TextView) itemView.findViewById(R.id.address_view);
        }



        public void bind(Spend spend){

            mSpend = spend;
            mTypeTextView.setText(mSpend.getType());
            //mDateTextView.setText(mSpend.getDate().toString());
            mSpendCost.setText(mSpend.getCost());
            mSpendDetail.setText(mSpend.getDetail());
            mAddress.setText(mSpend.getAddress());
        }

        @Override
        public void onClick(View v) {
            Intent intent = SpendPagerActivity.newIntent(getActivity(), mSpend.getId());
            startActivity(intent);
        }
    }



    private class SpendAdapter extends RecyclerView.Adapter<SpendHolder> {

      private List<Spend> mSpends;

      public SpendAdapter(List<Spend> spends){
          mSpends = spends;
      }

        @Override
        public SpendHolder onCreateViewHolder( ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new SpendHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder( SpendHolder holder, int position) {
          Spend spend = mSpends.get(position);
          holder.bind(spend);

        }

        @Override
        public int getItemCount() {
            return mSpends.size();
        }

        public void setSpends(List<Spend> spends){
          mSpends = spends;
        }
    }


}
