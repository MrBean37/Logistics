package com.example.logistics;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoodsAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoodsAddFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Fragment myFragment;
    private EditText goodsAddGoodsID;
    private String goodsSts;


    public GoodsAddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GoodsAddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GoodsAddFragment newInstance(String param1, String param2) {
        GoodsAddFragment fragment = new GoodsAddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        myFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.goods_add_new, container, false);

        //Toast.makeText(getActivity(),"last fragment = " + MainActivity.lastActiveFragment + ". Current Fragment = " + MainActivity.curActiveFragment,Toast.LENGTH_SHORT).show();
        goodsAddGoodsID = view.findViewById(R.id.goods_add_goods_id);
        final EditText goodsAddType = view.findViewById(R.id.goods_add_type);
        final EditText goodsAddSenderName = view.findViewById(R.id.goods_add_sender_name);
        final EditText goodsAddSenderPhone = view.findViewById(R.id.goods_add_sender_phone);
        final EditText goodsAddSenderAddress = view.findViewById(R.id.goods_add_sender_address);
        final EditText goodsAddSenderDate = view.findViewById(R.id.goods_add_sender_date);
        final EditText goodsAddReceiveName = view.findViewById(R.id.goods_add_receiver_name);
        final EditText goodsAddReceivePhone = view.findViewById(R.id.goods_add_receiver_phone);
        final EditText goodsAddReceiveAddress = view.findViewById(R.id.goods_add_receiver_address);
        final EditText goodsAddReceiveDate = view.findViewById(R.id.goods_add_receiver_date);
        Spinner goodsAddSts = view.findViewById(R.id.goods_add_sts);
        final EditText goodsAddGoodsQuantity = view.findViewById(R.id.goods_add_goods_quantity);
        final EditText goodsAddMoney = view.findViewById(R.id.goods_add_money);
        final EditText goodsAddPrice = view.findViewById(R.id.goods_add_goods_price);
        final EditText goodsAddNote = view.findViewById(R.id.goods_add_goods_notes);
        Button goodsAddClearBt = view.findViewById(R.id.goods_add_clear_bt);
        Button goodsAddBt = view.findViewById(R.id.goods_add_bt);
        Button goodsAddCloseBt = view.findViewById(R.id.goods_add_close_bt);
        Button goodsAddScanBt = view.findViewById(R.id.goods_add_scan_bt);

        //parameter for spinner
        MainActivity.spinderGoodsStatus(getActivity(),goodsAddSts);
        // set selection value of spinner
        goodsAddSts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                switch (position) {
                    case 0:
                        goodsSts = "100";
                        break;
                    case 1:
                        goodsSts = "200";
                        break;
                    case 2:
                        goodsSts = "300";
                        break;
                    case 3:
                        goodsSts = "400";
                        break;
                    case 4:
                        goodsSts = "500";
                        break;
                    case 5:
                        goodsSts = "600";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                goodsSts = "100";
            }
        });

        goodsAddClearBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsAddGoodsID.setText("");
                goodsAddType.setText("");
                goodsAddSenderName.setText("");
                goodsAddSenderPhone.setText("");
                goodsAddSenderAddress.setText("");
                goodsAddSenderDate.setText("");
                goodsAddReceiveName.setText("");
                goodsAddReceivePhone.setText("");
                goodsAddReceiveAddress.setText("");
                goodsAddReceiveDate.setText("");
                goodsAddGoodsQuantity.setText("");
                goodsAddMoney.setText("");
                goodsAddPrice.setText("");
                goodsAddNote.setText("");
            }
        });


        goodsAddBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsLocalDatabase goodsLocalDatabase = new GoodsLocalDatabase(getActivity());
                String goodsCode = goodsAddGoodsID.getText().toString();
                String goodsType = goodsAddType.getText().toString();
                String goodsSendName = goodsAddSenderName.getText().toString();
                String goodsSendPhone = goodsAddSenderPhone.getText().toString();
                String goodsSendCity = goodsAddSenderAddress.getText().toString(); // this one need to clarify, currently it not correct
                String goodsSendDate = goodsAddSenderDate.getText().toString();
                String goodsReceiveName = goodsAddReceiveName.getText().toString();
                String goodsReceivePhone = goodsAddReceivePhone.getText().toString();
                String goodsReceiveCity = goodsAddReceiveAddress.getText().toString(); // this one need to clarify, currently it is not correct
                String goodsReceiveDate = goodsAddReceiveDate.getText().toString();
                String goodsQuantity = goodsAddGoodsQuantity.getText().toString();
                String goodsMoney = goodsAddMoney.getText().toString();
                //missing price
                String goodsNote = goodsAddNote.getText().toString();
                // have to add the object with null ID (Primary Key) and primary key have to auto AUTOINCREMENT to avoid dupplicate when add new one
                GoodsInformation goodsInformation = new GoodsInformation(goodsCode,"Good Name",goodsType,goodsSts,goodsQuantity,"goods unit","goods weight",goodsMoney,"goods date","goods location",
                        goodsNote,goodsSendName,"sender id",goodsSendPhone,goodsSendCity,"send district","send province","send called",goodsSendDate,"send note",goodsReceiveName,"receiver id",goodsReceivePhone,
                        goodsReceiveCity,"received district","receiver province","receiver called",goodsReceiveDate,"receiver note");
                goodsLocalDatabase.addGoods(goodsInformation);

                Toast.makeText(getActivity(),"Đã Hoàn Thành Thêm mới",Toast.LENGTH_SHORT).show();

            }
        });

        goodsAddCloseBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mainViewPager.setCurrentItem(MainActivity.lastActiveFragment);  // go back to last fragment
            }
        });

        goodsAddScanBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.scanBarcode(getActivity(),myFragment);
            }
        });
        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // clear content in listview

        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        MainActivity.scanIDResult = scanningResult.getContents();
        goodsAddGoodsID.setText(MainActivity.scanIDResult);
    }
}

