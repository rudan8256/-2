package com.example.programing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Mainlayout extends AppCompatActivity {

    private CardView maplinear,board,oylmpic_list,sport_list;
    private CardView profilecard;
    private TextView usernick;
    public FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private FirebaseAuth mFirebaseAuth=FirebaseAuth.getInstance();
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainlayout);


        maplinear=findViewById(R.id.mapsearch);
        oylmpic_list= findViewById(R.id.oylmpic_list);
        sport_list = findViewById(R.id.sport_list);
        board=findViewById(R.id.board);
        usernick=findViewById(R.id.user_nick);


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
}