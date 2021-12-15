package com.example.programing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.otaliastudios.zoom.ZoomLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Post_Comment extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String post_id,writer_id_post;
    private ArrayList<String> image_urllist;
    private Post post;
    private ArrayList<Uri> uriList = new ArrayList<Uri>();
    private MultiImageAdapter photoadapter;
    private RecyclerView photo_list;
    private TextView com_title, com_text, com_nick, com_date, com_click,likeText;
    private Timestamp timestamp;
    private ArrayList<Comment> Cdata;
    public boolean Compared_c = true;
    private EditText com_edit;
    private Integer ll;
    private ImageView btn_comment,likeButton;
    public static Context mcontext;
    ZoomLayout zoomLayout;
    private RecyclerView mCommentRecyclerView;
    String P_comment_id;
    private PostCommentAdapter contentAdapter;
    DocumentReference docRef;
    private String comment_p, post_t, post_num, comment_post;//
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comment);


        mcontext = this;
        ll = new Integer(0);
        btn_comment = (ImageView) findViewById(R.id.btn_comment);
        com_nick = (TextView) findViewById(R.id.Comment_nickname);          //본문 작성자
        com_title = (TextView) findViewById(R.id.Comment_title);            //제목
        com_text = (TextView) findViewById(R.id.Comment_text);              //본문
        com_date = (TextView)findViewById(R.id.Comment_date);               //작성날짜

        com_edit = (EditText) findViewById(R.id.Edit_comment);              //댓글 작성 내용 입력창
        likeButton = (ImageView) findViewById(R.id.like_button);            //좋아요 버튼
        likeText = (TextView) findViewById(R.id.like_text);                 //좋아요 개수 보여주는 텍스트
        zoomLayout = (ZoomLayout) findViewById(R.id.post_zoomlayout);
        mCommentRecyclerView = findViewById(R.id.comment_recycler);         //코멘트 리사이클러뷰



        Intent intent = getIntent();//데이터 전달받기
        post_id = intent.getStringExtra("post_id");
        photo_list=findViewById(R.id.photo_list);

        docRef = mStore.collection("board").document(post_id);


        //댓글입력버튼 누를떄 onclick으로 감
        findViewById(R.id.btn_comment).setOnClickListener(this);

        swipeRefreshLayout=findViewById(R.id.refresh_comment);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {



                swipeRefreshLayout.setRefreshing(false);

            }
        });

        //사진 가져오기
        mStore.collection("board").document(post_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                post = task.getResult().toObject(Post.class);

                writer_id_post = post.getWriter_id();
                image_urllist = post.getImage_url();
                timestamp = post.getTimestamp();

                if (image_urllist.size()>0) {


                    for(String image_url : image_urllist) {
                        Log.d("####", "image_url : " + image_url);
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageReference = storage.getReference();

                        Log.d("###", "최종 사진 주소 : " + "post_image/" + image_url + ".jpg");
                        //StorageReference submitImage = storageReference.child("post_image/" + image_url + ".jpg");
                        storageReference.child("post_image/" + image_url + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                uriList.add(uri);
                                photoadapter = new MultiImageAdapter(uriList, getApplicationContext());
                                photo_list.setAdapter(photoadapter);
                                photo_list.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                                photoadapter.setOnItemClickListener(new MultiImageAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View v, int pos) {

                                        Intent intent=new Intent(Post_Comment.this, ImageViewpager.class);
                                        intent.putExtra("uri",uriList);
                                        intent.putExtra("uri_Num", String.valueOf(pos));
                                        startActivity(intent);
                                    }
                                });


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // 실패
                            }
                        });
                    }
                }
            }
        });

        //현재 사용자의 정보받아오기
        mStore.collection("user").document(mAuth.getCurrentUser().getUid())// 여기 콜렉션 패스 경로가 중요해 보면 패스 경로가 user로 되어있어서
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult() != null) {

                            comment_p = (String) task.getResult().getData().get(FirebaseID.nickname);//

                        }
                    }
                });
    }

    public void compared(String comment_id) {
        Compared_c = false;
        com_edit.setHint("대댓글 작성하기");
        P_comment_id = comment_id;

        //키보드 올리기 코드
        com_edit.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);


    }

    @Override
    public void onStart() {
        super.onStart();

        Cdata = new ArrayList<Comment>();
        mStore.collection("board").document(post_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Post post = documentSnapshot.toObject(Post.class);
                com_nick.setText(post.getP_nickname());          //본문 작성자
                com_title.setText(post.getTitle());            //제목
                com_text.setText(post.getContents());             //본문
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy.MM.dd HH:mm");
                com_date.setText(simpleDateFormat.format(timestamp.toDate()));

                Log.e("###",post.getTimestamp().toDate().toString());

                Cdata.clear();
                Cdata = post.getComments();
                contentAdapter = new PostCommentAdapter(Cdata, Post_Comment.this, docRef);//mDatas라는 생성자를 넣어줌
                mCommentRecyclerView.setAdapter(contentAdapter);
            }
        });
    }

    //댓글 대댓글 작성 함수
    @Override
    public void onClick(View v) {
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (Compared_c) { // 댓글
                    if (mAuth.getCurrentUser() != null) {
                        post = documentSnapshot.toObject(Post.class);

                        ArrayList<Comment> data = new ArrayList<>();

                        if (post.getComments() != null) {
                            data = post.getComments();
                        }

                        Comment cur_comment = new Comment();

                        int Csize = post.getComent_Num();

                        cur_comment.setComment(com_edit.getText().toString());
                        cur_comment.setC_nickname(comment_p);
                        cur_comment.setDocumentId(mAuth.getCurrentUser().getUid());
                        cur_comment.setComment_id(Integer.toString((1 + Csize) * 100));

                        if (Csize + 1 >= 100) {
                            Toast.makeText(Post_Comment.this, "댓글수 제한 100개을 넘었습니다", Toast.LENGTH_LONG).show();
                            return;
                        }
                        post.setComent_Num(Csize + 1);
                        data.add(cur_comment);
                        Collections.sort(data);
                        post.setComments(data);



                        mStore.collection("board").document(post_id).set(post);
                        String mId = mStore.collection("message").document().getId();

                        long datetime = System.currentTimeMillis();
                        Date date = new Date(datetime);
                        Timestamp timestamp = new Timestamp(date);



                        View view = getCurrentFocus();//작성버튼을 누르면 에딧텍스트 키보드 내리게 하기

                        if (view != null) {//댓글작성시 키보드 내리고 댓글에 작성한 내용 초기화

                            InputMethodManager hide = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            hide.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            com_edit.setText("");
                        }

                        Intent intent = getIntent();//데이터 전달받기

                        finish();
                        startActivity(intent);
                    }

                }
                // 대댓글
                else if (P_comment_id != null) {
                    if (mAuth.getCurrentUser() != null) {//새로 Comment란 컬렉션에 넣어줌

                        post = documentSnapshot.toObject(Post.class);
                        ArrayList<Comment> data = new ArrayList<>();
                        int Csize = 1;

                        if (post.getComments() != null) {
                            data = post.getComments();

                            for (int i = 0; i < data.size(); ++i) {
                                Log.e("&&&", data.get(i).getComment_id());
                                if ((data.get(i).getComment_id()).equals(P_comment_id)) {
                                    Csize = 1 + data.get(i).getCcoment_Num();
                                    Log.e("&&&", P_comment_id + ' ' + Integer.toString(Csize));
                                }
                                data.get(i).setCcoment_Num(Csize);
                            }
                        }

                        if (Csize >= 100) {
                            Toast.makeText(Post_Comment.this, "대댓글수 제한 100개을 넘었습니다", Toast.LENGTH_LONG).show();
                            return;
                        }
                        Comment cur_comment = new Comment();


                        cur_comment.setComment(com_edit.getText().toString());
                        cur_comment.setC_nickname(comment_p);
                        cur_comment.setDocumentId(mAuth.getCurrentUser().getUid());
                        cur_comment.setComment_id(Integer.toString((Integer.parseInt(P_comment_id)) + Csize));

                        data.add(cur_comment);
                        Collections.sort(data);
                        post.setComments(data);


                        mStore.collection("board").document(post_id).set(post);

                        String mId = mStore.collection("message").document().getId();

                        long datetime = System.currentTimeMillis();
                        Date date = new Date(datetime);
                        Timestamp timestamp = new Timestamp(date);

                        View view = getCurrentFocus();//작성버튼을 누르면 에딧텍스트 키보드 내리게 하기

                        if (view != null) {//댓글작성시 키보드 내리고 댓글에 작성한 내용 초기화

                            InputMethodManager hide = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            hide.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            com_edit.setText("");
                        }

                        Intent intent = getIntent();//데이터 전달받기

                        finish();
                        startActivity(intent);

                    }
                    Compared_c = true;
                }
            }
        });
    }
}