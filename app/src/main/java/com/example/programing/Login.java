package com.example.programing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; // 파이어베이스 인증
    private EditText mEtEmail, mEtPwd; // 회원가입 입력필드
    private static final int RC_SIGN_IN = 1000; // 구글 로그인 결과 코드
    private FirebaseAuth mAuth; // 파이어베이스 인증 객체
    private GoogleApiClient mGoogleApiClient; // 구글 API 클라이언트 객체
    private String retVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth=FirebaseAuth.getInstance();
        mEtEmail=findViewById(R.id.et_email);
        mEtPwd=findViewById(R.id.et_pwd);

        TextView registerT=findViewById(R.id.registerButton);
        LinearLayout login_btn = findViewById(R.id.btn_login);

        registerT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();
                MessageDigest md= null;
                try {
                    md = MessageDigest.getInstance("SHA-1");
                    md.update(strPwd.getBytes());

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
                Log.e("Login","로그인요청");
                mFirebaseAuth.signInWithEmailAndPassword(strEmail, retVal).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e("###","로그인 정보 맞음");

                            Log.e("Login","로그인성공");
                            Intent intent = new Intent(Login.this, Mainlayout.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Log.e("Login","로그인실패");
                            Toast.makeText(Login.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


    }
}