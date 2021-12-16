package com.example.programing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyList extends AppCompatActivity {

    RecyclerView mylist_recy;
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ArrayList<UserToDoList> dolistData = new ArrayList<>();
    LinearLayout into_sportlist;
    private ArrayList<UserToDoList> userToDoLists;
    private MylistAdapter mylistAdapter;
    User userdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        mylist_recy = findViewById(R.id.mylist_recy);
        into_sportlist=findViewById(R.id.into_sportlist);



        into_sportlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyList.this,sportList.class);
                startActivity(intent);
            }
        });




        getWindow().setStatusBarColor(Color.parseColor("#908EF0"));
    }

    void callFb(){

        dolistData.clear();

        mStore.collection("user").document(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    userdata = task.getResult().toObject(User.class);

                    dolistData = userdata.getUserToDoLists();

                    mylistAdapter = new MylistAdapter(dolistData,getApplicationContext());
                    mylist_recy.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                    mylist_recy.setAdapter(mylistAdapter);
                    mylistAdapter.notifyDataSetChanged();

                    LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.layoutanim_popup);
                    mylist_recy.setLayoutAnimation(controller);
                    mylist_recy.scheduleLayoutAnimation();


                    //각 아이템 클릭시 함수
                    if(mylistAdapter != null) {
                        mylistAdapter.setOnItemClickListener(new MylistAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View v, int pos) {


                                Dialog dialog = new Dialog(MyList.this);
                                dialog.setContentView(R.layout.mylist_complete);


                                //완료
                                dialog.findViewById(R.id.yes_btn).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        if(!userToDoLists.get(pos).getComplete()) {

                                            userToDoLists = userdata.getUserToDoLists();

                                            userToDoLists.get(pos).setComplete(true);


                                            Map inputdata = new HashMap<String, ArrayList<UserToDoList>>();
                                            inputdata.put(FirebaseID.UserToDoList, userToDoLists);

                                            mStore.collection("user").document(mAuth.getCurrentUser().getUid()).set(inputdata, SetOptions.merge());

                                            dialog.dismiss();

                                            onStart();
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(), "이미 완료하였습니다",Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                                //취소
                                dialog.findViewById(R.id.no_btn).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        dialog.dismiss();
                                    }
                                });

                                dialog.findViewById(R.id.delete_btn).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        userToDoLists = userdata.getUserToDoLists();

                                        userToDoLists.remove(pos);


                                        Map inputdata = new HashMap<String, ArrayList<UserToDoList>>();
                                        inputdata.put(FirebaseID.UserToDoList, userToDoLists);

                                        mStore.collection("user").document(mAuth.getCurrentUser().getUid()).set(inputdata, SetOptions.merge());

                                        onStart();
                                        dialog.dismiss();
                                    }
                                });

                                dialog.show();
                            }
                        });
                    }

                }
            }
        });
    }


    @Override
    public void onStart(){
        super.onStart();

        callFb();
    }

}