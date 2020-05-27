package com.example.yzh.spend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

//import com.baidu.location.BDAbstractLocationListener;
//import com.baidu.location.BDLocation;

//mport service.LocationService;

public class MoneyFragment extends Fragment {




    private static final String ARG_SPEND_ID = "spend_id";
    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;

    private Spend mSpend;
    private EditText mTypeField;
    private EditText mDateField;
    private EditText mSpendCost;
    private Button mDateButton;
    private EditText mSpendDetail;
    private EditText mAddress;
    private Button mDeleteSpend;

    public static MoneyFragment newInstance(UUID spendId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_SPEND_ID, spendId);

        MoneyFragment fragment = new MoneyFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID spendId = (UUID) getArguments().getSerializable(ARG_SPEND_ID);
        mSpend = MoneyLab.get(getActivity()).getSpend(spendId);


    }



    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_money,container, false);

        mTypeField = (EditText)v.findViewById(R.id.spend_type);
        mTypeField.setText(mSpend.getType());
        mTypeField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //left bank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSpend.setType(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mSpendDetail = (EditText) v.findViewById(R.id.spend_detail);
        mSpendDetail.setText(mSpend.getDetail());
        mSpendDetail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mSpend.setDetail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mAddress = (EditText) v.findViewById(R.id.address_view);
        mAddress.setText(mSpend.getAddress());
        mAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mSpend.setAddress(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSpendCost = (EditText) v.findViewById(R.id.spend_cost);
        mSpendCost.setText(mSpend.getCost());
        mSpendCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mSpend.setCost(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

      /*  mDateButton = (Button) v.findViewById(R.id.delete_spend);
        mDeleteSpend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoneyLab.get(getActivity()).deleteSpend(mSpend);
                getActivity().finish();

            }
        });*/

        SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM-dd",new Locale("zh"));
        String sDate = shortDateFormat.format(mSpend.getDate());
        mDateField = (EditText)v.findViewById(R.id.spend_time);
        updateDate();
        mDateField.setText(sDate);
        mDateButton = (Button) v.findViewById(R.id.date_show1);
        mDateButton.setEnabled(true);
       // updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mSpend.getDate());
                dialog.setTargetFragment(MoneyFragment.this, REQUEST_DATE);
                dialog.show(manager,DIALOG_DATE);
            }
        });


        return v;

    }


    @Override
    public void onPause() {
        super.onPause();

        MoneyLab.get(getActivity())
                .updateSpend(mSpend);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mSpend.setDate(date);
            updateDate();
        }
    }



    private void updateDate() {
        //mDateButton.setText(mSpend.getDate().toString());
        SimpleDateFormat shortDateFormat = new SimpleDateFormat("yyyy-MM-dd",new Locale("zh"));
        String sDate = shortDateFormat.format(mSpend.getDate());
        //mDateField.setText(mSpend.getDate().toString());
        mDateField.setText(sDate);
        mTypeField.setText(mSpend.getType());
        mSpendDetail.setText(mSpend.getDetail());
        mSpendCost.setText(mSpend.getCost());
        mAddress.setText(mSpend.getAddress());

    }


}
