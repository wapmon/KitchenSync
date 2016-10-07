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
public class RecipeListAdapter extends BaseAdapter {
    Context context;
    List<String> mRecipeList;

    public RecipeListAdapter(Context context, List<String> recipeList) {
        this.context = context;
        this.mRecipeList = recipeList;

    }

    @Override
    public int getCount() {
        return mRecipeList.size();
    }

    @Override
    public Object getItem(int i) {
        return mRecipeList.get(i);
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

            convertView = lInflater.inflate(R.layout.recipe_list_row, null);
        }

        TextView recipeName = (TextView) convertView.findViewById(R.id.recipeNameTextView);

        recipeName.setText(mRecipeList.get(pos));

        return convertView;
    }
}
