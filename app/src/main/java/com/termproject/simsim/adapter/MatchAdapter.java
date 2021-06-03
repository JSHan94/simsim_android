package com.termproject.simsim.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.termproject.simsim.R;
import com.termproject.simsim.dataclass.MatchData;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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

        try{
            TextView userNameView = (TextView) view.findViewById(R.id.userName);
            TextView userTagView = (TextView) view.findViewById(R.id.userTag);
            Button matchBtn = (Button) view.findViewById(R.id.matchBtn);

            String tag = data.get(position).getTag();
            String userName = data.get(position).getUserName();
            String dis = String.valueOf(distance.get(position)/1000)+"km";


            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));
            Date curDate = calendar.getTime();
            Date preDate =stringToDate(data.get(position).getTime(),"yyyyMMddhhmmss");

            String timeDiff = "("+calTimeDiff(curDate,preDate)+"분 전 접속)";
            userNameView.setText(userName+ " " + dis + " " + timeDiff);
            userTagView.setText(tag);

            matchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (matchBtn.getText()=="매치 신청 중"){
                        matchBtn.setText("매치");
                    }else{
                        matchBtn.setText("매치 신청 중");
                    }

                }
            });
        }catch(Exception e){
            Log.e("Error in MatchAdapter", e.toString());
            Log.e("Error in MatchAdapter", "During calling "+ data.get(position).getUserName());
        }
        return view;
    }

    private String calTimeDiff(Date cur, Date pre){
        long millis = cur.getTime() - pre.getTime();
        long mins = (millis/ (60 * 1000)%60);

        String diff = String.valueOf(mins);
        return diff;
    }

    private Date stringToDate(String aDate,String aFormat) {

        if(aDate==null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date date = simpledateformat.parse(aDate, pos);
        return date;
    }
}
