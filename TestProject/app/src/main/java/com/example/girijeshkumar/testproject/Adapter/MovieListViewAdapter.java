package com.example.girijeshkumar.testproject.Adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.girijeshkumar.testproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Model.Question;

/**
 * Created by girijeshkumar on 30/06/16.
 */
public class MovieListViewAdapter extends BaseAdapter {

    Context context;
    List<Question> rowItems;

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtDesc;
    }

    public MovieListViewAdapter(Context context, ArrayList<Question> items) {
        this.context = context;
        this.rowItems = items;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Question getItem(int position) {

        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.list_item2);
            // holder.txtTitle = (TextView) convertView.findViewById(R.id.title);\
            holder.imageView = (ImageView) convertView.findViewById(R.id.ImageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Question rowItem = (Question) getItem(position);
        holder.txtDesc.setText(rowItem.getTitle());

//        String imgUrl = "https://www.gravatar.com/avatar/4c09d545d50d5bb4e2c0fc4194ab3108?s=128&d=identicon&r=PG&f=1";
        String imgUrl = rowItem.getOwner().getProfileImage();
        Log.i("imgUrl>>>>>>>", imgUrl);

        if (!TextUtils.isEmpty(imgUrl))
        {
            Picasso.with(context)
                    .load(imgUrl)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.imageView);
        }
        return convertView;
    }
}
