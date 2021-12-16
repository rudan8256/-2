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

public class sportList extends AppCompatActivity {

    private RecyclerView ppRc;
    private sportListAdapter listadapter;
    private LinearLayout listsel_btn,complete_btn,link_btn;
    private ImageView Backpress;
    private TextView date_txt,time_txt,map_txt,sibal;

    private Calendar calendar = Calendar.getInstance();
    private  Calendar minDate = Calendar.getInstance();
    private Calendar maxDate = Calendar.getInstance();
    private String sel_date, sel_time,sel_map;
    private BottomSheetDialog listdialog;

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    boolean click=false;
    int selnum=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_list);

        listsel_btn=findViewById(R.id.listsel_btn);
        ppRc=findViewById(R.id.pp_list_rr);
        Backpress = findViewById(R.id.btn_back);
        link_btn = findViewById(R.id.link_btn);

        link_btn.setVisibility(View.INVISIBLE);

        getWindow().setStatusBarColor(Color.WHITE);

        ArrayList<Sport> s_list = new ArrayList<>();


        s_list.add(new Sport(" 양궁 ",R.drawable.didrnd,"","https://www.archery.or.kr/archer/main/archeryMan.do"));
        s_list.add(new Sport(" 탁구 ",R.drawable.xkrrn,"","http://www.koreatta.or.kr/servlets/org/Main"));
        s_list.add(new Sport("스케이트보드",R.drawable.tmzpdlxm,"","https://www.koreaskateboard.or.kr/"));
        s_list.add(new Sport(" 수영 ",R.drawable.tndud,"","http://www.koreaswim.or.kr/"));
        s_list.add(new Sport(" 태권도 ",R.drawable.xornjseh,"","http://www.koreataekwondo.org/"));

        s_list.add(new Sport(" 야구 ",R.drawable.dirn,"","http://www.korea-baseball.com/"));
        s_list.add(new Sport(" 육상 " ,R.drawable.dbrtkd,"","http://www.kaaf.or.kr/ver3/main/main.asp"));
        s_list.add(new Sport(" 수영 ",R.drawable.tndud,"","http://www.koreaswim.or.kr/"));
        s_list.add(new Sport(" 펜싱 ",R.drawable.vpstld,"","https://fencing.sports.or.kr/"));
        s_list.add(new Sport(" 유도 ",R.drawable.dbeh,"","http://judo.sports.or.kr/"));
        s_list.add(new Sport(" 사격 ",R.drawable.tkrur,"","https://www.shooting.or.kr/"));

        s_list.add(new Sport(" 복싱 ",R.drawable.qhrtld,"","http://boxing.sports.or.kr/"));
        s_list.add(new Sport(" 배구 ",R.drawable.volleyball,"","https://www.kva.or.kr/"));
        s_list.add(new Sport(" 축구 ",R.drawable.cnrrn,"","https://www.kfa.or.kr/"));



        ArrayList<String> list = new ArrayList<>();
        for (int i=0; i<100; i++) {
            list.add(String.format("TEXT %d", i)) ;
        }

        listadapter = new sportListAdapter(s_list,getApplicationContext()) ;
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
        listadapter.setOnItemClickListener(new sportListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                listdialog = new BottomSheetDialog(sportList.this);
                listdialog.setContentView(R.layout.dialog_sport_setting);
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
                                sportList.this,
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

                        TimePickerDialog dialog = new TimePickerDialog(sportList.this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar, listener, 15, 24, false);
                        dialog.setTitle("대여시작시간");
                        dialog.show();

                    }
                });

                listdialog.findViewById(R.id.map_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Dialog dialog = new Dialog(sportList.this);
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