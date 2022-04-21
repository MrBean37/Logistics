package com.example.logistics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class GoodsFragmentListAdapter extends BaseAdapter {

    private List<GoodsInformation> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public GoodsFragmentListAdapter(Context aContext, List<GoodsInformation> listData){
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        if (listData.size() >0){
            return listData.get(position);
        }else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("CutPasteId")
    public View getView(final int position, View convertView, ViewGroup parent) {
        //GoodsInformation goodsInformation = this.listData.get(position);
        ViewHolder holder;  // note: can not set initial value because it will make not working
       // if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.goods_main_lv_item_layout, null);
            holder = new ViewHolder();
            holder.item_type = (TextView) convertView.findViewById(R.id.goods_main_lv_item_type);
            holder.sender_name = (TextView) convertView.findViewById(R.id.goods_main_lv_item_sender_name);
            holder.sender_address = (TextView) convertView.findViewById(R.id.goods_main_lv_item_sender_address);
            holder.receive_name = (TextView) convertView.findViewById(R.id.goods_main_lv_item_receiver_name);
            holder.receive_address = (TextView) convertView.findViewById(R.id.goods_main_lv_item_receiver_address);
            convertView.setTag(holder);

            holder.item_type.setText(listData.get(position).getGoodsType());
            holder.sender_name.setText(listData.get(position).getGoodsSendName());
            holder.sender_address.setText(listData.get(position).getGoodsSendProvince() +","+listData.get(position).getGoodsSendDistrict() +","+listData.get(position).getGoodsSendCity());
            holder.receive_name.setText(listData.get(position).getGoodsReceiveName());
            holder.receive_address.setText(listData.get(position).getGoodsReceiveProvince() +","+listData.get(position).getGoodsReceiveDistrict() +","+listData.get(position).getGoodsReceiveCity());



        return convertView;
    }

    static class ViewHolder {
        TextView item_type;
        TextView sender_name;
        TextView sender_address;
        TextView receive_name;
        TextView receive_address;
    }}
