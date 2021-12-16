package com.example.programing;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class MylistPickAdapter extends RecyclerView.Adapter<MylistPickAdapter.ViewHolder> {

    private ArrayList<UserToDoList> mData = null ;
    private MylistPickAdapter.OnItemClickListener mListener = null;
    private Context context;
    public HashMap<String,Integer> photoData = new HashMap<>();

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
       LinearLayout item_layout,complete_check,subject_back;
        TextView sel_sportname,sel_date,sel_time,sel_map;
        ImageView sport_photo;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            subject_back = itemView.findViewById(R.id.subject_back);
            sel_sportname = itemView.findViewById(R.id.sel_sportname);
            sel_date = itemView.findViewById(R.id.sel_date);
            sel_time = itemView.findViewById(R.id.sel_time);
            sel_map = itemView.findViewById(R.id.sel_map);
            item_layout = itemView.findViewById(R.id.item_layout);
            sport_photo = itemView.findViewById(R.id.sport_photo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });


        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    MylistPickAdapter(ArrayList<UserToDoList> list, Context context) {
        this.mData = list ;
        this.context =context;

    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public MylistPickAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.mylistpick_adapter, parent, false) ;
        MylistPickAdapter.ViewHolder vh = new MylistPickAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(MylistPickAdapter.ViewHolder holder, int position) {


//        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_to_left);
//        holder.item_layout.setAnimation(animation);

        holder.sel_sportname.setText(mData.get(position).getSel_sport().getName()) ;
        holder.sel_date.setText(mData.get(position).getSel_date());
        holder.sel_time.setText(mData.get(position).getSel_time());
        holder.sel_map.setText(mData.get(position).getSel_map());




        Log.e("****",String.valueOf(R.drawable.pp_badminton));

        mappping();

       holder.sport_photo.setImageResource( photoData.get(mData.get(position).getSel_sport().getName()));

       holder.subject_back.setBackgroundResource(R.drawable.subject_right_round);


    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }

    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }

    public void setOnItemClickListener(MylistPickAdapter.OnItemClickListener listener){
        this.mListener = listener;
    }

    void mappping() {

        photoData.put("골볼", R.drawable.pp_goalball);
        photoData.put("보치아", R.drawable.pp_boccia);
        photoData.put("배드민턴", R.drawable.pp_badminton);
        photoData.put("사격", R.drawable.pp_shooting);
        photoData.put("사이클 도로", R.drawable.pp_cyclingroad);
        photoData.put("사이클 트랙", R.drawable.pp_cyclingtrack);

        photoData.put("수영", R.drawable.pp_swimming);
        photoData.put("승마", R.drawable.pp_equestrian);
        photoData.put("양궁", R.drawable.pp_archery);
        photoData.put("역도", R.drawable.pp_powerlifting);
        photoData.put("유도", R.drawable.pp_judo);
        photoData.put("육상", R.drawable.pp_athleticsframe);

        photoData.put("조정", R.drawable.pp_rowing);
        photoData.put("좌식배구", R.drawable.pp_sittingvolleyball);
        photoData.put("축구(5인)", R.drawable.pp_football5aside);
        photoData.put("카느 스프린트", R.drawable.pp_canoesprint);
        photoData.put("탁구", R.drawable.pp_tabletennis);
        photoData.put("태권도", R.drawable.pp_taekwondo);

        photoData.put("트라이애슬론", R.drawable.pp_triathlon);
        photoData.put("휠체어농구", R.drawable.pp_wheelchairbasketball);
        photoData.put("휠체어럭비", R.drawable.pp_wheelchairrugby);
        photoData.put("휠체어테니스", R.drawable.pp_wheelchairtennis);
        photoData.put("휠체어펜싱", R.drawable.pp_wheelchairfencing);

    }

}
