package com.example.programing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Mainlayout extends AppCompatActivity {

    private CardView maplinear,board,oylmpic_list,sport_list;
    private CardView profilecard;
    private TextView usernick,num_list;
    public FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private FirebaseAuth mFirebaseAuth=FirebaseAuth.getInstance();
    FirebaseUser user;
    User userdata;
    ArrayList<UserToDoList> dolistData = new ArrayList<>();
    int ExNum,listNum;
    LinearLayout lengthview;
    View exgage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainlayout);


        maplinear=findViewById(R.id.mapsearch);
        oylmpic_list= findViewById(R.id.oylmpic_list);
        sport_list = findViewById(R.id.sport_list);
        board=findViewById(R.id.board);
        usernick=findViewById(R.id.user_nick);
        lengthview=findViewById(R.id.length_view);
        exgage =findViewById(R.id.ex_gage);
         num_list=findViewById(R.id.num_list);

        user = mFirebaseAuth.getCurrentUser();




        mStore.collection("user").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                User mUser = (User) task.getResult().toObject(User.class);

                usernick.setText(mUser.getNickname()+"님!");
            }
        });

        //시설찾기
        maplinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext() , MainActivity.class);
                startActivity(intent);
            }
        });

        //게시판
        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext() , Board.class);
                startActivity(intent);
            }
        });


        //패럶림픽 종목
        oylmpic_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Paralympic.class);
                startActivity(intent);
            }
        });

        //나의 운동리스트
        sport_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MyList.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onStart(){
        super.onStart();

        mStore.collection("user").document(mFirebaseAuth.getCurrentUser().
                getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    userdata = task.getResult().toObject(User.class);

                    listNum =  userdata.getUserToDoLists().size();
                    ExNum = userdata.getEx_num();

                    int ex_len = lengthview.getWidth() * ExNum / 1000;


                    exgage.getLayoutParams().width=ex_len;
                    exgage.setLayoutParams(exgage.getLayoutParams());


                    num_list.setText(String.valueOf(listNum));

                }
            }
        });
    }
}