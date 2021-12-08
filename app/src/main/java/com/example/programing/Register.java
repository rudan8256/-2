package com.example.programing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Register extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증
    private EditText mEtEmail, rPwd1,rPwd2,mEtPwd2,enick; // 회원가입 입력필드
    private LinearLayout mBtnRegister; // 회원가입 버튼
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private String retVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth=FirebaseAuth.getInstance();
        mEtEmail=findViewById(R.id.r_email);
        rPwd1=findViewById(R.id.r_pwd1);
        rPwd2=findViewById(R.id.r_pwd2);
        mBtnRegister=findViewById(R.id.btn_register);
        enick=findViewById(R.id.r_nick);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //회원가입 처리 시작
                String strEmail=mEtEmail.getText().toString();
                String strPwd1=rPwd1.getText().toString();
                String strPwd2=rPwd2.getText().toString();
                String nickname=enick.getText().toString();

                if(strPwd1.equals(strPwd2) && !TextUtils.isEmpty(nickname)) {
                    //Firebase Auth 진행
                    MessageDigest md= null;
                    try {
                        md = MessageDigest.getInstance("SHA-1");
                        md.update(strPwd1.getBytes());

                        byte byteData[]=md.digest();

                        StringBuffer sb=new StringBuffer();
                        for(int i=0; i<byteData.length; i++) {
                            sb.append(Integer.toString((byteData[i]&0xff)+0x100, 16).substring(1));
                        }
                        retVal=sb.toString();
                        Log.e("###",retVal);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }

                    Log.e("###",strEmail);
                    Log.e("###",retVal);




                    mFirebaseAuth.createUserWithEmailAndPassword(strEmail, retVal).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {

                                FirebaseUser user = mFirebaseAuth.getCurrentUser();
                                //String idToken, String emailId, String password, String nickname, ArrayList<String> liked_Post

                                User userAccount = new User(user.getUid(),  strEmail, retVal, nickname);
                                mStore.collection("user").document(user.getUid()).set(userAccount);
                                finish();


                                Toast.makeText(Register.this, "회원가입에 성공하셨습니다", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Register.this, "회원가입에 실패하셨습니다", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else if(!TextUtils.isEmpty(nickname)){
                    Toast.makeText(Register.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Register.this, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}