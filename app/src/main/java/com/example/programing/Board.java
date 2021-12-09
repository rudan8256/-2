package com.example.programing;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class Board extends AppCompatActivity {


    private  FloatingActionButton write_btn;
   private ArrayList<Post> mDatas;
   private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
   private PostAdapter mAdapter;
   private RecyclerView mPostRecyclerView;
   private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        write_btn=findViewById(R.id.write_btn);
        mPostRecyclerView=findViewById(R.id.recyclerview);

        listDatas();

        swipeRefreshLayout=findViewById(R.id.refresh_board);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                listDatas();

                swipeRefreshLayout.setRefreshing(false);

            }
        });

        write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Board.this, Post_write.class);
                startActivity(intent);


            }
        });


    }

    public void listDatas(){
        mDatas = new ArrayList<>();//
        mStore.collection("board")//리사이클러뷰에 띄울 파이어베이스 테이블 경로
                .orderBy(FirebaseID.timestamp, Query.Direction.DESCENDING)//시간정렬순으로
                .addSnapshotListener(
                        new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                if (queryDocumentSnapshots != null) {
                                    mDatas.clear();//미리 생성된 게시글들을 다시 불러오지않게 데이터를 한번 정리
                                    for (DocumentSnapshot snap : queryDocumentSnapshots.getDocuments()) {
                                        Post post = snap.toObject(Post.class);

                                        mDatas.add(post);
                                    }
                                } else {
                                }

                                mAdapter = new PostAdapter(Board.this, mDatas);//mDatas라는 생성자를 넣어줌
                                mPostRecyclerView.setAdapter(mAdapter);
                            }
                        });
    }
}