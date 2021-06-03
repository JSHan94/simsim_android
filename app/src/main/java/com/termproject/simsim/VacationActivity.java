package com.termproject.simsim;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.termproject.simsim.adapter.VacationAdapter;
import com.termproject.simsim.dataclass.VacationData;

import java.util.ArrayList;

public class VacationActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<VacationData> vacationDataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation);

        Button detailBtn = (Button) findViewById(R.id.detailBtn);
        detailBtn.setOnClickListener(this);

        vacationDataList = new ArrayList<VacationData>();
        vacationDataList.add(new VacationData("강릉 해수욕장","05/13~05/16","만수,James"));
        vacationDataList.add(new VacationData("태국 여행","07/03~07/06","병수,종수"));
        vacationDataList.add(new VacationData("경주 글램핑","09/20~10/26","성현,재석,지현"));

        ListView vacationListView = (ListView) findViewById(R.id.vacationListView);
        final VacationAdapter vacationAdapter =  new VacationAdapter(getApplicationContext(),vacationDataList);

        vacationListView.setAdapter(vacationAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detailBtn:
                TextView vacationInfo = (TextView) findViewById(R.id.vacationInfo);
                vacationInfo.setText(R.string.vacationInfo);

                ImageView vacationInfoImg = (ImageView) findViewById(R.id.vacationInfoImg);
                vacationInfoImg.setImageResource(R.drawable.vacationimg);
                break;
        }
    }
}