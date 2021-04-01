package com.example.simsim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.simsim.adapter.MatchAdapter;
import com.example.simsim.dataclass.MatchData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    private Button tagBtn;
    private EditText tagEditText;

    ArrayList<MatchData> matchDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        // xml views
        tagBtn = (Button) findViewById(R.id.tagBtn);
        tagEditText = (EditText) findViewById(R.id.tagEditText);

        //firebase
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            userID = user.getUid();
            reference = FirebaseDatabase.getInstance().getReference("Users");
            this.getMatchData();
        }


        tagBtn.setOnClickListener(this);

        ListView matchListView = (ListView) findViewById(R.id.matchListView);
        final MatchAdapter matchAdapter = new MatchAdapter(this,matchDataList);
        matchListView.setAdapter(matchAdapter);

        matchListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Toast.makeText(HomeActivity.this,
                        "Clicked!!",
                        Toast.LENGTH_LONG).show();

            }
        });



    }

    private void getMatchData() {
        matchDataList = new ArrayList<MatchData>();
        //get Matchdata , can change to addListenerForSingleValueEvent
        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));

                }
            }
        });
        matchDataList.add(new MatchData("정수","Game",100));
        matchDataList.add(new MatchData("재준","Game",100));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tagBtn :
                tagUserInterest();
                break;
        }
    }
    private void tagUserInterest(){
        String tag = tagEditText.getText().toString();
        reference.child(user.getUid()).child("Tag").setValue(tag);
    }
}