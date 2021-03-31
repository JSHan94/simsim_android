package com.example.simsim.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.simsim.dataclass.MatchData;

import java.util.ArrayList;

public class MatchAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<MatchData> data;

    public MatchAdapter(Context context, ArrayList<MatchData> data){
        mContext = context;
        this.data = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public MatchData getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String tag = data.get(position).getTag();
        String userName = data.get(position).getUserName();
        int distance = data.get(position).getDistance();

        return null;
    }
}
