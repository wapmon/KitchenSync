package com.austin.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by austin on 7/24/16.
 */
public class IngredientListAdapter extends BaseAdapter {
    Context context;
    List<String> mIngredientList;
    List<String> mDateList;

    public IngredientListAdapter(Context context, List<String> ingredientList, List<String> dateList) {
        this.context = context;
        this.mIngredientList = ingredientList;
        this.mDateList = dateList;

    }

    @Override
    public int getCount() {
        return mIngredientList.size();
    }

    @Override
    public Object getItem(int i) {
        return mIngredientList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup viewGroup) {

        if(convertView == null){
            LayoutInflater lInflater = (LayoutInflater)context.getSystemService(
                    Activity.LAYOUT_INFLATER_SERVICE);

            convertView = lInflater.inflate(R.layout.ingredient_list_row, null);
        }

        TextView ingredientName = (TextView) convertView.findViewById(R.id.ingredient_name);
        TextView dateText = (TextView) convertView.findViewById(R.id.date_text);

        ingredientName.setText(mIngredientList.get(pos));
        dateText.setText(mDateList.get(pos));

        return convertView;
    }
}
