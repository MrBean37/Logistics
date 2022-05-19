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
        goodsMainListview.invalidateViews();  // refresh listview

        goodsMainListview.setAdapter(new GoodsFragmentListAdapter(getActivity(), goodsAll));

        // detect scrolling direction

        goodsMainListview.setOnScrollListener(new AbsListView.OnScrollListener() {
                                                  private int lastFirstVisibleItem;

                                                  @Override
                                                  public void onScrollStateChanged(AbsListView view, int scrollState) {
                                                  }

                                                  @Override
                                                  public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                                                      if (lastFirstVisibleItem < firstVisibleItem) {
                                                          // scroll down -> hide the floating buttons
                                                          MainActivity.mainFab.setVisibility(View.GONE);
                                                          MainActivity.summaryFab.setVisibility(View.GONE);
                                                          MainActivity.goodsFab.setVisibility(View.GONE);
                                                          MainActivity.scanFab.setVisibility(View.GONE);
                                                          MainActivity.searchFab.setVisibility(View.GONE);
                                                          MainActivity.uploadFab.setVisibility(View.GONE);
                                                          MainActivity.downloadFab.setVisibility(View.GONE);
                                                      }
                                                      if (lastFirstVisibleItem > firstVisibleItem) {
                                                          // scroll up -> visible the floating buttons
                                                          MainActivity.mainFab.setVisibility(View.VISIBLE);
                                                      }
                                                      lastFirstVisibleItem = firstVisibleItem;
                                                  }
                                              });


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

            menu.add("Chi Tiết");
            menu.add("Gọi Người Gửi");
            menu.add("Gọi Người Nhận");
            menu.add("Cập Nhật");
            menu.add("Xóa");

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (goodsMainTabLayout.getSelectedTabPosition() ==0){
            goodsSelectedFromLv = goodsAll.get(itemIDSelectedFromListview);
        }else if(goodsMainTabLayout.getSelectedTabPosition() ==1) {
            goodsSelectedFromLv = goodsDone.get(itemIDSelectedFromListview);
        }else if (goodsMainTabLayout.getSelectedTabPosition() ==2){
            goodsSelectedFromLv = goodsNotDone.get(itemIDSelectedFromListview);
        }

        if (item.getTitle() == "Chi Tiết") {

            goodsAllInforPopup(getActivity().getWindow().getDecorView().getRootView());


        } else if(item.getTitle() == "Gọi Người Gửi") {

            makeCall(goodsSelectedFromLv.getGoodsSendPhone());

        } else if(item.getTitle() == "Gọi Người Nhận") {
            makeCall(goodsSelectedFromLv.getGoodsReceivePhone());

        }else if(item.getTitle() == "Cập Nhật") {
            goodsUpdateInforPopup(getActivity().getWindow().getDecorView().getRootView());
            goodsAll.clear();
            goodsAll = goodsLocalDatabase.getAllGoods();
            goodsDone.clear();
            goodsDone = goodsLocalDatabase.getGoodsBaseSts("600");
            goodsNotDone.clear();
            goodsNotDone = goodsLocalDatabase.getGoodsBaseSts("500");
            goodsMainListview.invalidateViews();

        }else if(item.getTitle() == "Xóa") {
            goodsLocalDatabase.deleteGoods(goodsSelectedFromLv);
            goodsAll = goodsLocalDatabase.getAllGoods();
            goodsDone.clear();
            goodsDone = goodsLocalDatabase.getGoodsBaseSts("600");
            goodsNotDone.clear();
            goodsNotDone = goodsLocalDatabase.getGoodsBaseSts("500");
            goodsMainListview.invalidateViews();

        }else {return false;

        }

        return true;

    }


    public void goodsAllInforPopup(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        //View popupView = inflater.inflate(R.layout.goods_main_all_infor, null);
        View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.goods_main_all_infor, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        //final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
                {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });

        TextView goodsMainGoodsID = popupView.findViewById(R.id.goods_main_goods_id);
        TextView goodsMainType = popupView.findViewById(R.id.goods_main_type);
        TextView goodsMainSenderName = popupView.findViewById(R.id.goods_main_sender_name);
        TextView goodsMainSenderPhone = popupView.findViewById(R.id.goods_main_sender_phone);
        TextView goodsMainSenderAddress = popupView.findViewById(R.id.goods_main_sender_address);
        TextView goodsMainSenderDate = popupView.findViewById(R.id.goods_main_sender_date);
        TextView goodsMainReceiveName = popupView.findViewById(R.id.goods_main_receiver_name);
        TextView goodsMainReceivePhone = popupView.findViewById(R.id.goods_main_receiver_phone);
        TextView goodsMainReceiveAddress = popupView.findViewById(R.id.goods_main_receiver_address);
        TextView goodsMainReceiveDate = popupView.findViewById(R.id.goods_main_receiver_date);
        TextView goodsMainSts = popupView.findViewById(R.id.goods_main_goods_sts);
        TextView goodsMainGoodsQuantity = popupView.findViewById(R.id.goods_main_goods_quantity);
        TextView goodsMainMoney = popupView.findViewById(R.id.goods_main_money);
        TextView goodsMainPrice = popupView.findViewById(R.id.goods_main_goods_price);
        TextView goodsMainNote = popupView.findViewById(R.id.goods_main_goods_notes);
        Button goodsMainSenderCallBt = popupView.findViewById(R.id.goods_main_call_sender_bt);
        Button goodsMainReceiveCallBt = popupView.findViewById(R.id.goods_main_call_receiver_bt);
        Button goodsMainUpdateBt = popupView.findViewById(R.id.goods_main_update_bt);
        Button goodsMainCloseBt = popupView.findViewById(R.id.goods_main_close_bt);





        goodsMainGoodsID.setText(goodsSelectedFromLv.getGoodsCode());
        goodsMainType.setText(goodsSelectedFromLv.getGoodsType());
        goodsMainSenderName.setText(goodsSelectedFromLv.getGoodsSendName());
        goodsMainSenderPhone.setText(goodsSelectedFromLv.getGoodsSendPhone());
        goodsMainSenderAddress.setText(goodsSelectedFromLv.getGoodsSendProvince()+","+ goodsSelectedFromLv.getGoodsSendDistrict() +","+goodsSelectedFromLv.getGoodsSendCity());
        goodsMainSenderDate.setText(goodsSelectedFromLv.getGoodsSendDate());
        goodsMainReceiveName.setText(goodsSelectedFromLv.getGoodsReceiveName());
        goodsMainReceivePhone.setText(goodsSelectedFromLv.getGoodsReceivePhone());
        goodsMainReceiveAddress.setText(goodsSelectedFromLv.getGoodsReceiveProvince()+","+ goodsSelectedFromLv.getGoodsReceiveDistrict() +","+goodsSelectedFromLv.getGoodsReceiveCity());
        goodsMainReceiveDate.setText(goodsSelectedFromLv.getGoodsReceiveDate());
        //Set status

        if(goodsSelectedFromLv.getGoodsSts().matches("100")){
            goodsMainSts.setText("Chưa Nhận");
        }else if(goodsSelectedFromLv.getGoodsSts().matches("200")) {
            goodsMainSts.setText("Đang Ở Văn Phòng");
        }else if(goodsSelectedFromLv.getGoodsSts().matches("300")) {
            goodsMainSts.setText("Đang Vận Chuyển");
        }else if(goodsSelectedFromLv.getGoodsSts().matches("400")) {
            goodsMainSts.setText("Đang Ở Kho");
        }else if(goodsSelectedFromLv.getGoodsSts().matches("500")) {
            goodsMainSts.setText("Đang Giao");
        }else if(goodsSelectedFromLv.getGoodsSts().matches("600")) {
            goodsMainSts.setText("Đã Hoàn Thành");
        }else{
            goodsMainSts.setText("Không Có Thông Tin");
        }
        goodsMainGoodsQuantity.setText(goodsSelectedFromLv.getGoodsQuantity());
        goodsMainMoney.setText(goodsSelectedFromLv.getGoodsMoney());
        goodsMainPrice.setText("chua co data base");
        goodsMainNote.setText(goodsSelectedFromLv.getGoodsNote());



        goodsMainSenderCallBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall(goodsSelectedFromLv.getGoodsSendPhone());

            }
        });

        goodsMainReceiveCallBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall(goodsSelectedFromLv.getGoodsReceivePhone());

            }
        });

        goodsMainUpdateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });

        goodsMainCloseBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });




    }


    public void goodsUpdateInforPopup(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        //View popupView = inflater.inflate(R.layout.goods_update_all_infor, null);
        View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.goods_main_all_add_update, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        //final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);


        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
                {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });

        final EditText goodsUpdateGoodsID = popupView.findViewById(R.id.goods_update_goods_id);
        final EditText goodsUpdateType = popupView.findViewById(R.id.goods_update_type);
        final EditText goodsUpdateSenderName = popupView.findViewById(R.id.goods_update_sender_name);
        final EditText goodsUpdateSenderPhone = popupView.findViewById(R.id.goods_update_sender_phone);
        final EditText goodsUpdateSenderAddress = popupView.findViewById(R.id.goods_update_sender_address);
        final EditText goodsUpdateSenderDate = popupView.findViewById(R.id.goods_update_sender_date);
        final EditText goodsUpdateReceiveName = popupView.findViewById(R.id.goods_update_receiver_name);
        final EditText goodsUpdateReceivePhone = popupView.findViewById(R.id.goods_update_receiver_phone);
        final EditText goodsUpdateReceiveAddress = popupView.findViewById(R.id.goods_update_receiver_address);
        final EditText goodsUpdateReceiveDate = popupView.findViewById(R.id.goods_update_receiver_date);
        Spinner goodsMainSts = popupView.findViewById(R.id.goods_update_sts);
        final EditText goodsUpdateGoodsQuantity = popupView.findViewById(R.id.goods_update_goods_quantity);
        final EditText goodsUpdateMoney = popupView.findViewById(R.id.goods_update_money);
        EditText goodsUpdatePrice = popupView.findViewById(R.id.goods_update_goods_price);
        final EditText goodsUpdateNote = popupView.findViewById(R.id.goods_update_goods_notes);
        Button goodsUpdateClearBt = popupView.findViewById(R.id.goods_update_clear_bt);
        Button goodsUpdateReceiveCallBt = popupView.findViewById(R.id.goods_update_call_receiver_bt);
        Button goodsUpdateBt = popupView.findViewById(R.id.goods_update_bt);
        Button goodsUpdateCloseBt = popupView.findViewById(R.id.goods_update_close_bt);



        goodsUpdateGoodsID.setText(goodsSelectedFromLv.getGoodsCode());
        goodsUpdateType.setText(goodsSelectedFromLv.getGoodsType());
        goodsUpdateSenderName.setText(goodsSelectedFromLv.getGoodsSendName());
        goodsUpdateSenderPhone.setText(goodsSelectedFromLv.getGoodsSendPhone());
        goodsUpdateSenderAddress.setText(goodsSelectedFromLv.getGoodsSendProvince()+","+ goodsSelectedFromLv.getGoodsSendDistrict() +","+goodsSelectedFromLv.getGoodsSendCity());
        goodsUpdateSenderDate.setText(goodsSelectedFromLv.getGoodsSendDate());
        goodsUpdateReceiveName.setText(goodsSelectedFromLv.getGoodsReceiveName());
        goodsUpdateReceivePhone.setText(goodsSelectedFromLv.getGoodsReceivePhone());
        goodsUpdateReceiveAddress.setText(goodsSelectedFromLv.getGoodsReceiveProvince()+","+ goodsSelectedFromLv.getGoodsReceiveDistrict() +","+goodsSelectedFromLv.getGoodsReceiveCity());
        goodsUpdateReceiveDate.setText(goodsSelectedFromLv.getGoodsReceiveDate());
        goodsUpdateGoodsQuantity.setText(goodsSelectedFromLv.getGoodsQuantity());
        goodsUpdateMoney.setText(goodsSelectedFromLv.getGoodsMoney());
        goodsUpdatePrice.setText("chua co data base");
        goodsUpdateNote.setText(goodsSelectedFromLv.getGoodsNote());

        //parameter for spinner
        String[] goodsStsSelectList = new String[]{"Chưa Nhận","Đang Ở Văn Phòng","Đang Vận Chuyển","Đang Ở Kho","Đang Giao","Đã Hoàn Thành"};
        ArrayAdapter<String> adapterMonth = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, goodsStsSelectList);
        goodsMainSts.setAdapter(adapterMonth);

        //init value for spinner
        if(goodsSelectedFromLv.getGoodsSts().matches("100")){
            goodsMainSts.setSelection(0);
        } else if(goodsSelectedFromLv.getGoodsSts().matches("200")) {
            goodsMainSts.setSelection(1);
        }else if(goodsSelectedFromLv.getGoodsSts().matches("300")) {
            goodsMainSts.setSelection(2);
        }else if(goodsSelectedFromLv.getGoodsSts().matches("400")) {
            goodsMainSts.setSelection(3);
        }else if(goodsSelectedFromLv.getGoodsSts().matches("500")) {
            goodsMainSts.setSelection(4);
        }else if(goodsSelectedFromLv.getGoodsSts().matches("600")) {
            goodsMainSts.setSelection(5);
        }

        // set selection value of spinner
        goodsMainSts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                switch (position) {
                    case 0:
                        goodsSelectedFromLv.setGoodsSts("100");
                        break;
                    case 1:
                        goodsSelectedFromLv.setGoodsSts("200");
                        break;
                    case 2:
                        goodsSelectedFromLv.setGoodsSts("300");
                        break;
                    case 3:
                        goodsSelectedFromLv.setGoodsSts("400");
                        break;
                    case 4:
                        goodsSelectedFromLv.setGoodsSts("500");
                        break;
                    case 5:
                        goodsSelectedFromLv.setGoodsSts("600");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                goodsSelectedFromLv.setGoodsSts("100");
            }
        });







            goodsUpdateClearBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });

        goodsUpdateReceiveCallBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });
        goodsUpdateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               goodsSelectedFromLv.setGoodsCode(goodsUpdateGoodsID.getText().toString());
               goodsSelectedFromLv.setGoodsType(goodsUpdateType.getText().toString());
               goodsSelectedFromLv.setGoodsSendName(goodsUpdateSenderName.getText().toString());
               goodsSelectedFromLv.setGoodsSendPhone(goodsUpdateSenderPhone.getText().toString());
                goodsSelectedFromLv.setGoodsSendCity(goodsUpdateSenderAddress.getText().toString()); // this one need to clarify, currently it not correct
                goodsSelectedFromLv.setGoodsSendDate(goodsUpdateSenderDate.getText().toString());
                goodsSelectedFromLv.setGoodsReceiveName(goodsUpdateReceiveName.getText().toString());
                goodsSelectedFromLv.setGoodsReceivePhone(goodsUpdateReceivePhone.getText().toString());
                goodsSelectedFromLv.setGoodsReceiveCity(goodsUpdateReceiveAddress.getText().toString()); // this one need to clarify, currently it is not correct
                goodsSelectedFromLv.setGoodsReceiveDate(goodsUpdateReceiveDate.getText().toString());
                goodsSelectedFromLv.setGoodsQuantity(goodsUpdateGoodsQuantity.getText().toString());
                goodsSelectedFromLv.setGoodsMoney(goodsUpdateMoney.getText().toString());
                //missing price
                goodsSelectedFromLv.setGoodsNote(goodsUpdateNote.getText().toString());

                goodsLocalDatabase.updateGoods(goodsSelectedFromLv);

            }
        });

        goodsUpdateCloseBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });

        //show keyboard for EditText in popup windows
        popupWindow.setFocusable(true);
        popupWindow.update();




    }

    public void makeCall (String phoneNumber){
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        startActivity(intent);

    }

}


