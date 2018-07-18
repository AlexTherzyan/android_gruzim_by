package com.example.a_terzjan.login.orders;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.a_terzjan.login.R;

import java.util.ArrayList;

public class OrderAdapter extends ArrayAdapter<Orders> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Orders> productList;

    public OrderAdapter(Context context, int resource, ArrayList<Orders> products) {
        super(context, resource, products);
        this.productList = products;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Orders orders = productList.get(position);


        String status;
        status = orders.getStatus();
        if (status.contains("1"))
        {
            viewHolder.status.setTextColor(Color.RED);
            viewHolder.status.setText("Завершен...");
        }
        else
        {
            viewHolder.status.setTextColor(Color.GREEN);
            viewHolder.status.setText("В ожидании...");
//            viewHolder.status.setText(orders.getStatus());
        }
        viewHolder.route.setText(orders.getRoute());





        return convertView;
    }

    private String formatValue(int count, String unit){
        return String.valueOf(count) + " " + unit;
    }
    private class ViewHolder {

        final TextView status, route;
        ViewHolder(View view){

            status = (TextView) view.findViewById(R.id.status);
            route = (TextView) view.findViewById(R.id.route);
        }
    }
}