package com.example.logistics;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoodsScanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoodsScanFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public String codeContent;
    public String codeFormat;
    public int itemIDSelectedFromListview;
    public ListView goodsScanListView;
    public Fragment myFragment;
    public List<GoodsInformation> searchResult;
    public GoodsLocalDatabase goodsLocalDatabase;

    final Calendar myCalendar = Calendar.getInstance();
    public Date dateFrom = null;
    public Date dateTo = null;
    public EditText tripName;
    public int dateSelected = 0;

    public GoodsScanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GoodsScanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GoodsScanFragment newInstance(String param1, String param2) {
        GoodsScanFragment fragment = new GoodsScanFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.goods_scan_main_layout, container, false);

        goodsScanListView = (ListView) view.findViewById(R.id.goodsScanListView);
        Button goodsBarCodeScanBt = (Button) view.findViewById(R.id.goods_scan_barcode);
        Button goodsQRCodeScanBt = (Button) view.findViewById(R.id.goods_scan_QRcode);
        registerForContextMenu(goodsScanListView);

        // detect scroll direction of the listview and set visible or invisible for floating button
        MainActivity.visibleFloatButtonBaseScrollDirect(goodsScanListView);
        goodsBarCodeScanBt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                IntentIntegrator integrator = new IntentIntegrator(getActivity()).forSupportFragment(myFragment);

                //IntentIntegrator integrator = new IntentIntegrator(getActivity());
                // use forSupportFragment or forFragment method to use fragments instead of activity
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                integrator.setPrompt("Scan Mã Hàng");
                integrator.setBeepEnabled(true);
                integrator.setOrientationLocked(true);
                integrator.setCaptureActivity(Capture.class);
                integrator.initiateScan();

            }
        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        // clear content in listview

        goodsScanListView.setAdapter(null);
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        codeContent = scanningResult.getContents();
        codeFormat = scanningResult.getFormatName();
        goodsLocalDatabase = new GoodsLocalDatabase(getActivity());
        //registerForContextMenu(toolScanListView);
        if (scanningResult.getContents() != null) {
            searchResult =new ArrayList<>();

            try {
                searchResult = goodsLocalDatabase.getGoodsBaseCode(codeContent);
            } catch (NullPointerException e) {
                showPopup(getActivity().getWindow().getDecorView().getRootView(),codeContent);
            }

            if (searchResult != null && !searchResult.isEmpty()){
                goodsScanListView.setAdapter(new GoodsFragmentListAdapter(getActivity(), searchResult));
            }else {
                showPopup(getActivity().getWindow().getDecorView().getRootView(),codeContent);
            }

        } else {
            //Toast.makeText(getActivity(),"not scan anything",Toast.LENGTH_LONG);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Result");
            builder.setMessage("This Tool Not Available");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }
    }

    public void showPopup(View view, String result) {

        LayoutInflater inflater = (LayoutInflater)
                getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);

        //View popupView = inflater.inflate(R.layout.shopping_item_add, null);
        View popupView = inflater.inflate(R.layout.goods_scan_no_result_layout, null);

        // create the popup window
        //int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

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

        TextView scanCodeResult = popupView.findViewById(R.id.scan_code_result);
        Button scanCodeOkBt = popupView.findViewById(R.id.scan_code_button);
        Button scanCodeAddBt = popupView.findViewById(R.id.scan_code_button);
        scanCodeResult.setText(result);

        scanCodeOkBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }

        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.goodsScanListView) {

            MainActivity.curActiveFragment = 4; //set active fragment
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            //AdapterView.AdapterContextMenuInfo acmi = getActivity().getMenuInflater().inflate(R.menu.frag);
            Object obj = (Object) lv.getItemAtPosition(acmi.position);
            //itemIDSelectedFromListview = lv.getAdapter().getItemId(acmi.position);
            itemIDSelectedFromListview = acmi.position;
            MainActivity.goodsSelectFromListview = searchResult.get(itemIDSelectedFromListview);


            menu.add("Chi Tiết");
            menu.add("Gọi Người Gửi");
            menu.add("Gọi Người Nhận");
            menu.add("Cập Nhật");
            menu.add("Xóa");

        }
    }

}


