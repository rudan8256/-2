package com.example.programing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class Paralympic extends AppCompatActivity {

    RecyclerView ppRc;
    ppListAdapter listadapter;
    LinearLayout compt_btn;
    BottomSheetDialog listdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paralympic);

        compt_btn=findViewById(R.id.complete_btn);
        ppRc=findViewById(R.id.pp_list_rr);

        ArrayList<Sport> s_list = new ArrayList<>();

        s_list.add(new Sport("골볼",R.drawable.pp_goalball));
        s_list.add(new Sport("보치아",R.drawable.pp_boccia));
        s_list.add(new Sport("배드민턴",R.drawable.pp_badminton));
        s_list.add(new Sport("사격",R.drawable.pp_shooting));
        s_list.add(new Sport("사이클 도로",R.drawable.pp_cyclingroad));
        s_list.add(new Sport("사이클 트랙",R.drawable.pp_cyclingtrack));

        s_list.add(new Sport("수영",R.drawable.pp_swimming));
        s_list.add(new Sport("승마",R.drawable.pp_equestrian));
        s_list.add(new Sport("양궁",R.drawable.pp_archery));
        s_list.add(new Sport("역도",R.drawable.pp_powerlifting));
        s_list.add(new Sport("유도",R.drawable.pp_judo));
        s_list.add(new Sport("육상",R.drawable.pp_athleticsframe));

        s_list.add(new Sport("조정",R.drawable.pp_rowing));
        s_list.add(new Sport("좌식배구",R.drawable.pp_sittingvolleyball));
        s_list.add(new Sport("축구(5인)",R.drawable.pp_football5aside));
        s_list.add(new Sport("카느 스프린트",R.drawable.pp_canoesprint));
        s_list.add(new Sport("탁구",R.drawable.pp_tabletennis));
        s_list.add(new Sport("태권도",R.drawable.pp_taekwondo));

        s_list.add(new Sport("트라이애슬론",R.drawable.pp_triathlon));
        s_list.add(new Sport("휠체어농구",R.drawable.pp_wheelchairbasketball));
        s_list.add(new Sport("휠체어럭비",R.drawable.pp_wheelchairrugby));
        s_list.add(new Sport("휠체어테니스",R.drawable.pp_wheelchairtennis));
        s_list.add(new Sport("휠체어펜싱",R.drawable.pp_wheelchairfencing));

        ArrayList<String> list = new ArrayList<>();
        for (int i=0; i<100; i++) {
            list.add(String.format("TEXT %d", i)) ;
        }

        listadapter = new ppListAdapter(s_list) ;
        listadapter.notifyDataSetChanged();
        ppRc.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        ppRc.setAdapter(listadapter) ;


        listadapter.setOnItemClickListener(new ppListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

            }
        });


        listdialog = new BottomSheetDialog(Paralympic.this);
        listdialog.setContentView(R.layout.dialog_setting);
        listdialog.setCanceledOnTouchOutside(true);

        compt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listdialog.show();


            }
        });


    }
}