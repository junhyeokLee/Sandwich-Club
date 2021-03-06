package com.udacity.sandwichclub.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class MainListAdapter extends BaseAdapter{

    Context mContext;
    String[] sandwiches;

    public MainListAdapter(Context context,String[] sandwiches){
        this.mContext = context;
        this.sandwiches = sandwiches;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("ViewHolder") View row = inflater.inflate(R.layout.list_item,parent,false);

        ImageView thumbnail = row.findViewById(R.id.item_thumb);
        TextView titleTV = row.findViewById(R.id.item_title);
        final ProgressBar progressBar = row.findViewById(R.id.thumb_progressbar);

        convertView = thumbnail;

        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);

        Picasso.with(this.mContext)
                .load(sandwich.getImage())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_loading)
                .into(thumbnail, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });

        convertView.setTransitionName(sandwich.getMainName());
        titleTV.setText(sandwich.getMainName());

        return row;
    }
}
