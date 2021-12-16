package com.example.programing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.Table;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Post_write extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();//사용자 정보 가져오기
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private DocumentReference docRef;
    private EditText mTitle, mContents;//제목, 내용
    private String p_nickname;//게시판에 표기할 닉네잉 //이게 가져온 값을 저장하는 임시 변수
    private TextView post_photo, post_tree, post_gallery,post_list;
    private String writer_id;
    private ImageView post_imageView;
    private TextView post_save, btn_back;
    private static final int FROM_CAMERA = 1;
    private static final int FROM_GALLERY = 2;
    private Table choosedTable=null;
    private String forum_sort;
    private String image_url,token;
    private ArrayList<String> subscriber;
    private FirebaseStorage storage;
    private String imageFilePath;
    private Dialog addTreeDialog;
    private RecyclerView postAddTreeRV;
    private LinearLayoutManager linearLayoutManager;
    private  UserToDoList sel_DoLists = new UserToDoList();

    private ArrayList<Uri> uriList = new ArrayList<>();
    private RecyclerView photo_list;
    private MultiImageAdapter photoadapter;
    StorageReference storageReference;
    private ArrayList<String>image_urllist = new ArrayList<>();
    private MylistPickAdapter mylistAdapter;
    ArrayList<UserToDoList> dolistData = new ArrayList<>();
    User userdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_write);

        mTitle = findViewById(R.id.Post_write_title);//제목 , item_post.xml의 변수와 혼동주의
        mContents = findViewById(R.id.Post_write_contents);
        post_save=findViewById(R.id.post_save);
        btn_back=findViewById(R.id.btn_back);
        post_gallery=findViewById(R.id.post_gallery);
        photo_list  =findViewById(R.id.photo_list);
        post_list = findViewById(R.id.post_sport_list);


        storage=FirebaseStorage.getInstance();

        storageReference=storage.getReferenceFromUrl("gs://programing-a07fe.appspot.com/");



        TedPermission.with(getApplicationContext())
                .setPermissionListener(permissionListener)
                .setRationaleMessage("카메라 권한이 필요합니다")
                .setDeniedMessage("거부하셨습니다")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

        if (mAuth.getCurrentUser() != null) {
            mStore.collection("user").document(mAuth.getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.getResult() != null) {
                                writer_id = (String) task.getResult().getData().get(FirebaseID.documentId);
                                Log.d("확인", "현재 사용자 uid입니다:" + writer_id);
                            }
                        }
                    });
        }


        post_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useGallery();
            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // 게시글 올리기
        post_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePost();
            }
        });

        post_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp_List();
            }
        });
    }

    void popUp_List(){

        mStore.collection("user").document(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    userdata = task.getResult().toObject(User.class);

                    dolistData = userdata.getUserToDoLists();


                    Dialog dialog = new Dialog(Post_write.this);
                    dialog.setContentView(R.layout.dialog_postwrite_list);

                    RecyclerView recyclerView = dialog.findViewById(R.id.recy_postwritelist);


                    mylistAdapter = new MylistPickAdapter(dolistData,getApplicationContext());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(mylistAdapter);
                    mylistAdapter.notifyDataSetChanged();


                    mylistAdapter.setOnItemClickListener
                            (new MylistPickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View v, int pos) {


                                    sel_DoLists=dolistData.get(pos);
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "선택되었습니다", Toast.LENGTH_LONG).show();
                                }
                            });


                    dialog.show();
                }
            }
            });


    }

    private void useGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, FROM_GALLERY);
    }

    private File createImageFile() throws IOException {
        String timestamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName="TEST_"+timestamp+"_";
        File storageDir=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image=File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        imageFilePath=image.getAbsolutePath();
        return image;
    }

    public void SavePost()
    {
        Log.d("###", "SavePost진입");


        if (mAuth.getCurrentUser() != null) {
            String PostID = mStore.collection("board").document().getId();//제목이 같아도 게시글이 겹치지않게
            final Post[] post = new Post[1];
            DocumentReference docRef1 = mStore.collection("user").document(mAuth.getUid());
            docRef1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User userAccount = documentSnapshot.toObject(User.class);


                    long datetime = System.currentTimeMillis();
                    Date date = new Date(datetime);
                    Timestamp timestamp = new Timestamp(date);

                    post[0] = new Post(mAuth.getUid(), mTitle.getText().toString(), mContents.getText().toString(),
                            userAccount.getNickname(), "0",   new ArrayList<>(), image_urllist,timestamp ,PostID,0,sel_DoLists);

                    mStore.collection("board").document(PostID).set(post[0]);

                }
            });
            finish();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        uriList.clear(); // 초기화한번해주고
        if(data == null){   // 어떤 이미지도 선택하지 않은 경우
            Toast.makeText(getApplicationContext(), "이미지를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
        }
        else{   // 이미지를 하나라도 선택한 경우
            if(data.getClipData() == null){     // 이미지를 하나만 선택한 경우
                Log.e("single choice: ", String.valueOf(data.getData()));
                Uri imageUri = data.getData();
                uriList.add(imageUri);

            }
            else{      // 이미지를 여러장 선택한 경우
                ClipData clipData = data.getClipData();

                if(clipData.getItemCount() > 10){   // 선택한 이미지가 11장 이상인 경우
                    Toast.makeText(getApplicationContext(), "사진은 10장까지 선택 가능합니다.", Toast.LENGTH_LONG).show();
                }
                else{   // 선택한 이미지가 1장 이상 10장 이하인 경우

                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        Uri imageUri = clipData.getItemAt(i).getUri();  // 선택한 이미지들의 uri를 가져온다.
                        try {
                            uriList.add(imageUri);  //uri를 list에 담는다.

                        } catch (Exception e) {
                        }
                    }
                }
            }

            photoadapter = new MultiImageAdapter(uriList, getApplicationContext());
            photo_list.setAdapter(photoadapter);
            photo_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

            //사진 스토리지에 업로드

            UploadPhoto(uriList,0);


            photoadapter.setOnItemClickListener(new MultiImageAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int pos) {

                    Intent intent=new Intent(Post_write.this,Image_zoom.class);
                    intent.putExtra("uri",uriList.get(pos));
                    startActivity(intent);
                }
            });
        }


    }

    private void UploadPhoto(ArrayList<Uri> uris, int n){

        int i=0;
        for(Uri uri:uris ) {
            Log.d("###", "Uri 는: " + uri);
            String filename = mAuth.getUid() + "_" + System.currentTimeMillis() + n;
            StorageReference ref = storageReference.child("post_image/" + filename + ".jpg");
            image_urllist.add(filename);
            image_url = filename;
            Log.d("###", filename);

            UploadTask uploadTask;
            uploadTask = ref.putFile(uri);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    //Toast.makeText(getApplicationContext(),"업로드 실패",Toast.LENGTH_LONG).show();

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //Toast.makeText(getApplicationContext(),"업로드 성공",Toast.LENGTH_LONG).show();

                }
            });
            ++i;
        }
    }



    private int exifOrientationDegrees(int exifOrientation) {
        if(exifOrientation== ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if(exifOrientation==ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if(exifOrientation==ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix=new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
    }

    PermissionListener permissionListener=new PermissionListener() {
        @Override
        public void onPermissionGranted() {
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(getApplicationContext(),"권한이 거부됨",Toast.LENGTH_SHORT).show();
        }
    };


}