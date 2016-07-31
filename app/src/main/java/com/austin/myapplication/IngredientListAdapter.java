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

/**
 * Created by austin on 7/24/16.
 */
public class IngredientListAdapter extends BaseAdapter {
    Context context;
    LinkedHashMap<String, String> data;
    String[] keys;

    public IngredientListAdapter(Context context, LinkedHashMap<String, String> data) {
        this.context = context;
        this.data = data;
        this.keys = data.keySet().toArray(new String[data.size()]);

    }

    @Override
    public void notifyDataSetChanged(){
        super.notifyDataSetChanged();
        this.keys = data.keySet().toArray(new String[data.size()]);
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(keys[i]);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup viewGroup) {
        String key = keys[pos];
        String value = getItem(pos).toString();

        if(convertView == null){
            LayoutInflater lInflater = (LayoutInflater)context.getSystemService(
                    Activity.LAYOUT_INFLATER_SERVICE);

            convertView = lInflater.inflate(R.layout.ingredient_list_row, null);
        }

        TextView ingredientName = (TextView) convertView.findViewById(R.id.ingredient_name);
        TextView dateText = (TextView) convertView.findViewById(R.id.date_text);

        ingredientName.setText(key);
        dateText.setText(value);

        return convertView;
    }
}
