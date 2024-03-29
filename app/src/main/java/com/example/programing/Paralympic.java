package com.example.programing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Paralympic extends AppCompatActivity {

    private RecyclerView ppRc;
    private  ppListAdapter listadapter;
    private  LinearLayout listsel_btn,complete_btn,link_btn;
    private  ImageView Backpress;
    private  TextView date_txt,time_txt,map_txt,sibal;

    private  Calendar calendar = Calendar.getInstance();
    private  Calendar minDate = Calendar.getInstance();
    private  Calendar maxDate = Calendar.getInstance();
    private  String sel_date, sel_time,sel_map;
    private  BottomSheetDialog listdialog;

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

   boolean click=false;
   int selnum=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paralympic);

        listsel_btn=findViewById(R.id.listsel_btn);
        ppRc=findViewById(R.id.pp_list_rr);
        Backpress = findViewById(R.id.btn_back);
        link_btn = findViewById(R.id.link_btn);

        link_btn.setVisibility(View.INVISIBLE);

        getWindow().setStatusBarColor(Color.WHITE);

        ArrayList<Sport> s_list = new ArrayList<>();

        s_list.add(new Sport("골볼",R.drawable.pp_goalball, "","http://kbgf.koreanpc.kr/"));
        s_list.add(new Sport("보치아",R.drawable.pp_boccia,"","http://koreaboccia.koreanpc.kr/board/post_view?board_idx=29&cms_code=sportsintro"));
        s_list.add(new Sport("배드민턴",R.drawable.pp_badminton,"","http://www.korea-parabadminton.or.kr/"));
        s_list.add(new Sport("사격",R.drawable.pp_shooting,"","http://ksfd.kosad.kr/"));
        s_list.add(new Sport("사이클 도로",R.drawable.pp_cyclingroad,"","http://www.c-kdcf.kr/"));
        s_list.add(new Sport("사이클 트랙",R.drawable.pp_cyclingtrack,"","http://www.c-kdcf.kr/"));

        s_list.add(new Sport("수영",R.drawable.pp_swimming,"","http://swimming.koreanpc.kr/"));
        s_list.add(new Sport("승마",R.drawable.pp_equestrian,"","http://kead.kosad.kr/index.do"));
        s_list.add(new Sport("양궁",R.drawable.pp_archery,"","http://archery.koreanpc.kr/"));
        s_list.add(new Sport("역도",R.drawable.pp_powerlifting,"","http://kpfd.koreanpc.kr/"));
        s_list.add(new Sport("유도",R.drawable.pp_judo,"","http://www.kjfd.or.kr/"));
        s_list.add(new Sport("육상",R.drawable.pp_athleticsframe,"","http://kafd.koreanpc.kr/"));

        s_list.add(new Sport("조정",R.drawable.pp_rowing,"","http://kpra.koreanpc.kr/"));
        s_list.add(new Sport("좌식배구",R.drawable.pp_sittingvolleyball,"","http://kovad.or.kr/"));
        s_list.add(new Sport("축구(5인)",R.drawable.pp_football5aside,"","http://kofad.koreanpc.kr/"));
        s_list.add(new Sport("카누 스프린트",R.drawable.pp_canoesprint,"","https://www.koreanpc.kr/ibuilder.do?menu_idx=56&gubun_cd1=001&gubun_cd2=025"));
        s_list.add(new Sport("탁구",R.drawable.pp_tabletennis,"","http://tt.koreanpc.kr/"));
        s_list.add(new Sport("태권도",R.drawable.pp_taekwondo,"","http://kotad.koreanpc.kr/"));

        s_list.add(new Sport("트라이애슬론",R.drawable.pp_triathlon,"","https://www.koreanpc.kr/ibuilder.do?menu_idx=56&gubun_cd1=001&gubun_cd2=026"));
        s_list.add(new Sport("휠체어농구",R.drawable.pp_wheelchairbasketball,"","http://www.kwbf.or.kr/"));
        s_list.add(new Sport("휠체어럭비",R.drawable.pp_wheelchairrugby,"","http://kwra.kr/kwra/index.php"));
        s_list.add(new Sport("휠체어테니스",R.drawable.pp_wheelchairtennis,"","http://ktad.koreanpc.kr/"));
        s_list.add(new Sport("휠체어펜싱",R.drawable.pp_wheelchairfencing,"","http://wfencing.koreanpc.kr/"));

        ArrayList<String> list = new ArrayList<>();
        for (int i=0; i<100; i++) {
            list.add(String.format("TEXT %d", i)) ;
        }

        listadapter = new ppListAdapter(s_list,getApplicationContext()) ;
        listadapter.notifyDataSetChanged();
        ppRc.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        ppRc.setAdapter(listadapter);

        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.layoutanim_slide);
        ppRc.setLayoutAnimation(controller);
       ppRc.scheduleLayoutAnimation();


        //뒤로가기
        Backpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //해당 종목을 선택했을떄
        listadapter.setOnItemClickListener(new ppListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {


                listdialog = new BottomSheetDialog(Paralympic.this);
                listdialog.setContentView(R.layout.dialog_setting);
                listdialog.setCanceledOnTouchOutside(true);

                if( ! click && selnum == -1){

                    click=true;
                    selnum=pos;

                    link_btn.setVisibility(View.VISIBLE);
                    link_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(s_list.get(pos).getUri_content()));
                            startActivity(intent);
                        }
                    });

                }
                else if(selnum != pos && click){
                    selnum=pos;

                    link_btn.setVisibility(View.VISIBLE);

                    link_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(s_list.get(pos).getUri_content()));
                            startActivity(intent);
                        }
                    });
                }
                else{
                    //이전에 클릭상태에서 풀리는 상태
                    click=false;
                    selnum =-1;

                    link_btn.setVisibility(View.INVISIBLE);

                    link_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(s_list.get(pos).getUri_content()));
                            startActivity(intent);
                        }
                    });
                }


                listsel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if( click ){
                            completeFunc(s_list.get(pos));
                        }
                        else{

                            Toast.makeText(getApplicationContext(),"종목을 먼저 선택해 주세요",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });



        listsel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Toast.makeText(getApplicationContext(),"종목을 먼저 선택해 주세요",Toast.LENGTH_SHORT).show();
            }
        });


    }

    void completeFunc(Sport sel_sport){

        listdialog.show();

        date_txt= listdialog.findViewById(R.id.date_txt);
        time_txt= listdialog.findViewById(R.id.time_txt);
        map_txt= listdialog.findViewById(R.id.map_txt);


        //초기화
        date_txt.setText("날짜");
        time_txt.setText("시간");
        map_txt.setText("장소");
        sel_date=null;
        sel_map=null;
        sel_time=null;


        listdialog.findViewById(R.id.date_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //데이트피커 다이얼로그 생성
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Paralympic.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                date_txt.setText(year + "-"+(month+1)+"-"+dayOfMonth);
                                sel_date=year + "-"+(month+1)+"-"+dayOfMonth;

                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );

                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());

            }
        });

        listdialog.findViewById(R.id.time_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog dialog = new TimePickerDialog(Paralympic.this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar, listener, 15, 24, false);
                dialog.setTitle("운동 시작 시간");
                dialog.show();

            }
        });

        listdialog.findViewById(R.id.map_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(Paralympic.this);
                dialog.setContentView(R.layout.dialog_mapsetting);

                EditText inputmap_data = dialog.findViewById(R.id.input_map);


                dialog.findViewById(R.id.complete_btn_map).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        sel_map = inputmap_data.getText().toString();

                        map_txt.setText( sel_map);

                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });

        //모두다 설정완료

        listdialog.findViewById(R.id.complete_btnL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(null != sel_map && sel_time != null && sel_map != null){

                    mStore.collection("user").document(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()) {
                                ArrayList<UserToDoList> userToDoLists = (ArrayList<UserToDoList>) task.getResult().get(FirebaseID.UserToDoList);

                                UserToDoList newDolist = new UserToDoList(sel_date, sel_time, sel_map, sel_sport,false);

                                userToDoLists.add(newDolist);

                                Map inputdata = new HashMap<String, ArrayList<UserToDoList>>();
                                inputdata.put(FirebaseID.UserToDoList, userToDoLists);

                                mStore.collection("user").document(mAuth.getCurrentUser().getUid()).set(inputdata, SetOptions.merge());

                                listdialog.dismiss();
                                finish();
                            }
                        }
                    });


                }
                else{
                    Toast.makeText(getApplicationContext(),"세가지 모두 설정해 주십시요",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }




    private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // 설정버튼 눌렀을 때

            sel_time= String.valueOf(hourOfDay) +"시 "+ String.valueOf(minute)+"분 ";

            time_txt.setText(sel_time);

        }
    };

}