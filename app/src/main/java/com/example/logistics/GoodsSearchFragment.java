package com.example.logistics;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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
    private List<GoodsInformation> goodsSearchResult;
    //private GoodsLocalDatabase goodsLocalDatabase = new GoodsLocalDatabase(getActivity());
    private String goodsSearchName;
    private String goodsSearchTopicName;
    private int itemIDSelectedFromListview;
    public Fragment myFragment;

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
        myFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.goods_search_layout, container, false);

        Spinner goodsSearchTopic = view.findViewById(R.id.goods_search_topic);
        final EditText goodsSearchItem = view.findViewById(R.id.goods_search_item);
        final ListView goodsSearchLv = view.findViewById(R.id.goods_search_lv);
        Button searchBt = view.findViewById(R.id.goods_search_bt);
        Button searchCloseBt = view.findViewById(R.id.goods_search_close_bt);

        // set context menu for the listview
        registerForContextMenu(goodsSearchLv);

        // detect scroll direction of the listview and set visible or invisible for floating button
        MainActivity.visibleFloatButtonBaseScrollDirect(goodsSearchLv);

        final String[] goodsSearchTopicList = new String[]{"Mã Hàng", "Trạng Thái Hàng", "Vị Trí Hàng", "Tên Người Nhận", "Số Điện Thoại Người Nhận",
                "Tên Người Gửi", "Số Điện Thoại Người Gửi", "Ngày Gửi"};

        ArrayAdapter<String> adapterSearchTopic = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, goodsSearchTopicList);
        goodsSearchTopic.setAdapter(adapterSearchTopic);

        // set selection value of spinner
        goodsSearchTopic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                switch (position) {
                    case 0:
                        goodsSearchTopicName = "Mã Hàng";
                        break;
                    case 1:
                        goodsSearchTopicName = "Trạng Thái Hàng";
                        break;
                    case 2:
                        goodsSearchTopicName = "Vị Trí Hàng";
                        break;
                    case 3:
                        goodsSearchTopicName = "Tên Người Nhận";
                        break;
                    case 4:
                        goodsSearchTopicName = "Số Điện Thoại Người Nhận";
                        break;
                    case 5:
                        goodsSearchTopicName = "Tên Người Gửi";
                        break;
                    case 6:
                        goodsSearchTopicName = "Số Điện Thoại Người Gửi";
                        break;
                    case 7:
                        goodsSearchTopicName = "Ngày Gửi";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                goodsSearchTopicName = "";
            }
        });


        searchCloseBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.mainViewPager.setCurrentItem(MainActivity.lastActiveFragment);  // go back to last fragment

            }
        });

        // searching
        searchBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsSearchLv.setAdapter(null);
                GoodsLocalDatabase goodsLocalDatabase = new GoodsLocalDatabase(getActivity());
                goodsSearchName = goodsSearchItem.getText().toString();
                goodsSearchResult = new ArrayList<>();
                if (goodsSearchName.matches("") || goodsSearchTopicName.matches("")) {
                    Toast.makeText(getActivity(), "Hãy điền đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (goodsSearchTopicName.matches("Mã Hàng")) {
                        try {
                            goodsSearchResult = goodsLocalDatabase.getGoodsBaseCode(goodsSearchName);
                        } catch (NullPointerException e) {
                            Toast.makeText(getActivity(), "Không Tìm Thấy Kết Quả Nào", Toast.LENGTH_SHORT).show();
                        }
                    }

                    if (goodsSearchTopicName.matches("Trạng Thái Hàng")) {
                        try {
                            goodsSearchResult = goodsLocalDatabase.getGoodsBaseSts(goodsSearchName);
                        } catch (NullPointerException e) {
                            Toast.makeText(getActivity(), "Không Tìm Thấy Kết Quả Nào", Toast.LENGTH_SHORT).show();
                        }

                    }

                    if (goodsSearchTopicName.matches("Vị Trí Hàng")) {
                        try {
                            goodsSearchResult = goodsLocalDatabase.getGoodsBaseLocation(goodsSearchName);
                        } catch (NullPointerException e) {
                            Toast.makeText(getActivity(), "Không Tìm Thấy Kết Quả Nào", Toast.LENGTH_SHORT).show();
                        }
                    }

                    if (goodsSearchTopicName.matches("Tên Người Nhận")) {
                        try {
                            goodsSearchResult = goodsLocalDatabase.getGoodsBaseReceiveName(goodsSearchName);
                        } catch (NullPointerException e) {
                            Toast.makeText(getActivity(), "Không Tìm Thấy Kết Quả Nào", Toast.LENGTH_SHORT).show();
                        }
                    }

                    if (goodsSearchTopicName.matches("Số Điện Thoại Người Nhận")) {
                        try {
                            goodsSearchResult = goodsLocalDatabase.getGoodsBaseReceivePhone(goodsSearchName);
                        } catch (NullPointerException e) {
                            Toast.makeText(getActivity(), "Không Tìm Thấy Kết Quả Nào", Toast.LENGTH_SHORT).show();
                        }
                    }

                    if (goodsSearchTopicName.matches("Tên Người Gửi")) {
                        try {
                            goodsSearchResult = goodsLocalDatabase.getGoodsBaseSendName(goodsSearchName);
                        } catch (NullPointerException e) {
                            Toast.makeText(getActivity(), "Không Tìm Thấy Kết Quả Nào", Toast.LENGTH_SHORT).show();
                        }
                    }

                    if (goodsSearchTopicName.matches("Số Điện Thoại Người Gửi")) {
                        try {
                            goodsSearchResult = goodsLocalDatabase.getGoodsBaseSendPhone(goodsSearchName);
                        } catch (NullPointerException e) {
                            Toast.makeText(getActivity(), "Không Tìm Thấy Kết Quả Nào", Toast.LENGTH_SHORT).show();
                        }
                    }

                    if (goodsSearchResult != null && !goodsSearchResult.isEmpty()) {
                        goodsSearchLv.setAdapter(new GoodsFragmentListAdapter(getActivity(), goodsSearchResult));
                    } else {
                        Toast.makeText(getActivity(), "Không Tìm Thấy Kết Quả Nào", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });


        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.goods_search_lv) {

            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            //AdapterView.AdapterContextMenuInfo acmi = getActivity().getMenuInflater().inflate(R.menu.frag);
            Object obj = (Object) lv.getItemAtPosition(acmi.position);
            //itemIDSelectedFromListview = lv.getAdapter().getItemId(acmi.position);
            itemIDSelectedFromListview = acmi.position;
            MainActivity.goodsSelectFromListview = goodsSearchResult.get(itemIDSelectedFromListview);


            menu.add("Chi Tiết");
            menu.add("Gọi Người Gửi");
            menu.add("Gọi Người Nhận");
            menu.add("Cập Nhật");
            menu.add("Xóa");

        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // clear content in listview

        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        MainActivity.scanIDResult = scanningResult.getContents();
        Toast.makeText(getActivity(), MainActivity.scanIDResult, Toast.LENGTH_SHORT).show();
        //codeFormat = scanningResult.getFormatName();


    }
}
