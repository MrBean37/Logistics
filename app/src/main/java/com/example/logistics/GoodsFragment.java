package com.example.logistics;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoodsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoodsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private GoodsLocalDatabase goodsLocalDatabase;
    private List<GoodsInformation> goodsAll = new ArrayList<>();
    private List<GoodsInformation> goodsDone = new ArrayList<>();
    private List<GoodsInformation> goodsNotDone = new ArrayList<>();
    private TabLayout goodsMainTabLayout;
    private int itemIDSelectedFromListview;
    private GoodsInformation goodsSelectedFromLv = new GoodsInformation();
    private ListView goodsMainListview;


    public GoodsFragment() {
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
    public static GoodsFragment newInstance(String param1, String param2) {
        GoodsFragment fragment = new GoodsFragment();
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

        final View view = inflater.inflate(R.layout.goods_main_layout, container, false);
        goodsMainListview = view.findViewById(R.id.goods_main_listview);
        goodsMainTabLayout = view.findViewById(R.id.goods_main_tab);
        TabItem goodsMainTabItemAll = view.findViewById(R.id.goods_main_tabitem_all);
        TabItem goodsMainTabItemDone = view.findViewById(R.id.goods_main_tabitem_done);
        TabItem goodsMainTabItemNotDone = view.findViewById(R.id.goods_main_tabitem_notdone);
        TabItem goodsMainTabItemSearch = view.findViewById(R.id.goods_main_tabitem_search);
        TabItem goodsMainTabItemScan = view.findViewById(R.id.goods_main_tabitem_scan);
        goodsLocalDatabase =new GoodsLocalDatabase(getActivity());

        //get data all
        //final GoodsLocalDatabase goodsLocalDatabase =new GoodsLocalDatabase(getActivity());
        //init for listview
        goodsAll = goodsLocalDatabase.getAllGoods();
        goodsDone = goodsLocalDatabase.getGoodsBaseSts("600");
        goodsNotDone = goodsLocalDatabase.getGoodsBaseSts("500");

        try {
            goodsMainTabLayout.getTabAt(0).setText("Tất Cả \n" + goodsAll.size() );
        } catch (NullPointerException e) {
            goodsMainTabLayout.getTabAt(0).setText("Tất Cả \n0" );
        }

        try {
            goodsMainTabLayout.getTabAt(1).setText("Hoàn Thành \n" + goodsDone.size() );
        } catch (NullPointerException e) {
            goodsMainTabLayout.getTabAt(0).setText("Hoàn Thành \n0" );
        }

        try {
            goodsMainTabLayout.getTabAt(2).setText("Chưa Xong \n" + goodsNotDone.size() );
        } catch (NullPointerException e) {
            goodsMainTabLayout.getTabAt(0).setText("Chưa Xong \n0" );
        }

        // end of tablayout

        registerForContextMenu(goodsMainListview);
        // detect scroll direction of the listview and set visible or invisible for floating button
        MainActivity.visibleFloatButtonBaseScrollDirect(goodsMainListview);
        goodsMainListview.invalidateViews();  // refresh listview

        goodsMainListview.setAdapter(new GoodsFragmentListAdapter(getActivity(), goodsAll));

        goodsMainTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                goodsMainTabLayout.getTabAt(0).setText("Tất Cả \n10");
                goodsAll.clear();
                goodsAll = goodsLocalDatabase.getAllGoods();
                goodsDone.clear();
                goodsDone = goodsLocalDatabase.getGoodsBaseSts("600");
                goodsNotDone.clear();
                goodsNotDone = goodsLocalDatabase.getGoodsBaseSts("500");

                try {
                    goodsMainTabLayout.getTabAt(0).setText("Tất Cả \n" + goodsAll.size() );
                } catch (NullPointerException e) {
                    goodsMainTabLayout.getTabAt(0).setText("Tất Cả \n0" );
                }

                try {
                    goodsMainTabLayout.getTabAt(1).setText("Hoàn Thành \n" + goodsDone.size() );
                } catch (NullPointerException e) {
                    goodsMainTabLayout.getTabAt(0).setText("Hoàn Thành \n0" );
                }

                try {
                    goodsMainTabLayout.getTabAt(2).setText("Chưa Xong \n" + goodsNotDone.size() );
                } catch (NullPointerException e) {
                    goodsMainTabLayout.getTabAt(0).setText("Chưa Xong \n0" );
                }

                if (tab.getPosition() == 0) {
                    //goodsAll.clear();
                    //goodsAll = goodsLocalDatabase.getAllGoods();
                    goodsMainListview.setAdapter(new GoodsFragmentListAdapter(getActivity(), goodsAll));

                    // Toast.makeText(getActivity(),goodsAll.get(goodsAll.size()-1).getGoodsName(),Toast.LENGTH_SHORT).show();

                } else if (tab.getPosition() == 1) {

                    //goodsDone.clear();
                    //goodsDone = goodsLocalDatabase.getGoodsBaseSts("600");
                    goodsMainListview.setAdapter(new GoodsFragmentListAdapter(getActivity(), goodsDone));

                } else if (tab.getPosition() == 2) {
                    //goodsNotDone.clear();
                    //goodsNotDone = goodsLocalDatabase.getGoodsBaseSts("500");
                    goodsMainListview.setAdapter(new GoodsFragmentListAdapter(getActivity(), goodsNotDone));

                } else if (tab.getPosition() == 3) {
                    MainActivity.mainViewPager.setCurrentItem(2);
                    MainActivity.mainFab.setVisibility(View.VISIBLE);

                } else if (tab.getPosition() == 4) {
                    MainActivity.mainViewPager.setCurrentItem(3);
                    MainActivity.mainFab.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.goods_main_listview) {


            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            //AdapterView.AdapterContextMenuInfo acmi = getActivity().getMenuInflater().inflate(R.menu.frag);
            Object obj = (Object) lv.getItemAtPosition(acmi.position);
            //itemIDSelectedFromListview = lv.getAdapter().getItemId(acmi.position);
            itemIDSelectedFromListview = acmi.position;

            if (goodsMainTabLayout.getSelectedTabPosition() ==0){
                MainActivity.goodsSelectFromListview = goodsAll.get(itemIDSelectedFromListview);
            }else if(goodsMainTabLayout.getSelectedTabPosition() ==1) {
                MainActivity.goodsSelectFromListview = goodsDone.get(itemIDSelectedFromListview);
            }else if (goodsMainTabLayout.getSelectedTabPosition() ==2){
                MainActivity.goodsSelectFromListview = goodsNotDone.get(itemIDSelectedFromListview);
            }

            menu.add("Chi Tiết");
            menu.add("Gọi Người Gửi");
            menu.add("Gọi Người Nhận");
            menu.add("Cập Nhật");
            menu.add("Xóa");

        }
    }

}


