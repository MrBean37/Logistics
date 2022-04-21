package com.example.logistics;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SummaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SummaryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SummaryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SummaryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SummaryFragment newInstance(String param1, String param2) {
        SummaryFragment fragment = new SummaryFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.summary_layout, container, false);
        TextView summaryPhoneID = view.findViewById(R.id.summary_phoneID);
        TextView summaryDate = view.findViewById(R.id.summary_date);
        TextView summarySts = view.findViewById(R.id.summary_sts);
        TextView summaryRoute = view.findViewById(R.id.summary_route);
        TextView summaryGoodsTotal = view.findViewById(R.id.summary_total_goods);
        TextView summaryGoodsDone = view.findViewById(R.id.summary_goods_done);
        TextView summaryGoodsNotDone = view.findViewById(R.id.summary_goods_notdone);

        GoodsLocalDatabase goodsLocalDatabase = new GoodsLocalDatabase(getActivity());

        // general information will be get at code =-1
        GoodsInformation goodsInformation = goodsLocalDatabase.getGoodsBaseID(-1);
        // status: Chua nhan : 100, dang o kho cho van chuyen :200,dang van chuyen: 300,dang o kho cho giao : 400, dang giao: 500, da xong: 600
        List<GoodsInformation> goodsDone = goodsLocalDatabase.getGoodsBaseSts("600");

        if (goodsInformation != null) {
            summaryPhoneID.setText(goodsInformation.getGoodsName());
            summaryDate.setText(goodsInformation.getGoodsDate());
            summarySts.setText(goodsInformation.getGoodsSts());
            summaryRoute.setText(goodsInformation.getGoodsNote());
            summaryGoodsTotal.setText(Integer.toString(goodsLocalDatabase.getGoodsCount()-1));

            if (goodsDone.size()>0) {
                summaryGoodsDone.setText(Integer.toString(goodsDone.size()));
                summaryGoodsNotDone.setText(Integer.toString(goodsLocalDatabase.getGoodsCount()-1 - goodsDone.size()));
             //null list can get error when get the size
            }else {
                summaryGoodsDone.setText("0");
                summaryGoodsNotDone.setText(Integer.toString(goodsLocalDatabase.getGoodsCount()-1 ));
            }
        } else {
            summaryPhoneID.setText("Không xác định");
            summaryDate.setText("Không xác định");
            summarySts.setText("Không xác định");
            summaryRoute.setText("Không xác định");
            summaryGoodsTotal.setText("Không xác định");
            summaryGoodsDone.setText("Không xác định");
            summaryGoodsNotDone.setText("Không xác định");

        }



        return view;
    }
}
