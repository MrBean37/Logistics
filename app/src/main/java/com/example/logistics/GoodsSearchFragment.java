package com.example.logistics;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoodsSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoodsSearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<GoodsInformation> goodsSearchResult = new ArrayList<>();

    public GoodsSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GoodsSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GoodsSearchFragment newInstance(String param1, String param2) {
        GoodsSearchFragment fragment = new GoodsSearchFragment();
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
        final View view = inflater.inflate(R.layout.goods_search_layout, container, false);

        Spinner goodsSearchTopic = view.findViewById(R.id.goods_search_topic);
        final EditText goodsSearchItem = view.findViewById(R.id.goods_search_item);
        ListView goodsSearchLv = view.findViewById(R.id.goods_search_lv);
        Button searchBt = view.findViewById(R.id.goods_search_bt);
        Button searchCloseBt = view.findViewById(R.id.goods_search_close_bt);

        String[] goodsSearchTopicList = new String[]{"Mã Hàng","Trạng Thái Hàng","Vị Trí Hàng","Tên Người Nhận","Số Điện Thoại Người Nhận",
        "Tên Người Gửi","Số Điện Thoại Người Gửi","Ngày Gửi"};

        ArrayAdapter<String> adapterSearchTopic = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, goodsSearchTopicList);
        goodsSearchTopic.setAdapter(adapterSearchTopic);

        // set selection value of spinner



        searchCloseBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getFragmentManager().popBackStackImmediate();
                Toast.makeText(getActivity(),"Mã Hàng",Toast.LENGTH_SHORT).show();

            }
        });

        // searching
        searchBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsSearchResult.clear();
                if (goodsSearchItem.getText().toString().matches("Mã Hàng")){
                    Toast.makeText(getActivity(),"Mã Hàng",Toast.LENGTH_SHORT).show();
                }

                if (goodsSearchItem.getText().toString().matches("Trạng Thái Hàng")){
                    Toast.makeText(getActivity(),"Trạng Thái Hàng",Toast.LENGTH_SHORT).show();
                }


            }
        });


        return view;
    }
}
