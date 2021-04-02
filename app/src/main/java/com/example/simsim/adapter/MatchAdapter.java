package com.example.simsim.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simsim.HomeActivity;
import com.example.simsim.R;
import com.example.simsim.dataclass.MatchData;

import java.util.ArrayList;
import java.util.List;

public class MatchAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<MatchData> data;
    private Button matchBtn;
    private double userLongitude, userLatitude;
    private List<Integer> distance;
    public MatchAdapter(){

    }

    public MatchAdapter(Context context, ArrayList<MatchData> data, List<Integer> distance){
        mContext = context;
        this.data = data;
        this.distance = distance;
//        this.userLatitude = userLatitude;
//        this.userLongitude = userLongitude;
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
        View view = mLayoutInflater.inflate(R.layout.row,null);


        TextView userNameView = (TextView) view.findViewById(R.id.userName);
        TextView userTagView = (TextView) view.findViewById(R.id.userTag);
        Button matchBtn = (Button) view.findViewById(R.id.matchBtn);

        String tag = data.get(position).getTag();
        String userName = data.get(position).getUserName();
        try{
            userNameView.setText(userName+ " " + String.valueOf(distance.get(position)) + "km");
            userTagView.setText(tag);
        }catch(Exception e){
            Log.e("Error in MatchAdapter", e.toString());
        }


        return view;
    }
}
