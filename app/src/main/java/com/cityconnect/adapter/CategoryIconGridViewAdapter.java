package com.cityconnect.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;

import com.cityconnect.R;
import com.cityconnect.interfaces.GridItemClickInterface;
import com.cityconnect.modal.CategoryModel;

import java.util.List;

public class CategoryIconGridViewAdapter extends BaseAdapter {

    private Context context;
    private List<CategoryModel> items;
    LayoutInflater inflater;
    GridItemClickInterface listener;
    public CategoryIconGridViewAdapter(Context context, List<CategoryModel> items) {
        this.context = context;
        this.items = items;
        this.listener = (GridItemClickInterface) context;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.category_icon_cell, null);
        }
        ImageButton button = (ImageButton) convertView.findViewById(R.id.category_icon);
        button.setImageResource(R.drawable.beggers);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.clickedGridItemAtIndex(position);
            }
        });
        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public int getCount() {
        return  items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}