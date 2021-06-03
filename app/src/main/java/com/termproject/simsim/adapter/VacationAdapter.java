package com.termproject.simsim.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;

import com.termproject.simsim.R;
import com.termproject.simsim.dataclass.VacationData;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class VacationAdapter extends BaseAdapter {
    ArrayList<VacationData> data;
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;

    public VacationAdapter(){

    }
    public VacationAdapter(Context context, ArrayList<VacationData> data) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public VacationData getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = mLayoutInflater.inflate(R.layout.vacation_item,null);

        try{
            TextView placeView = (TextView) view.findViewById(R.id.place);
            TextView dateView = (TextView) view.findViewById(R.id.date);
            TextView membersView = (TextView) view.findViewById(R.id.members);
            Button vacationBtn = (Button) view.findViewById(R.id.vacationBtn);


            String place = data.get(position).getPlace();
            String date = data.get(position).getDate();
            String members = data.get(position).getMembers();
            placeView.setText(place);
            dateView.setText(date);
            membersView.setText(members);

            vacationBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if (vacationBtn.getText()=="선택"){
                        vacationBtn.setText("결정");
                    }else{
                        vacationBtn.setText("선택");
                    }

                }
            });

        }catch (Exception e){
            Log.e("Errors in VacAdapter", e.toString());
        }
        return view;
    }
}
