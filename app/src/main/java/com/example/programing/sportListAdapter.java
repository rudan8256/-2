package com.example.programing;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class sportListAdapter extends RecyclerView.Adapter<sportListAdapter.ViewHolder> {

    private ArrayList<Sport> mData = null ;
    private sportListAdapter.OnItemClickListener mListener = null;
    int curNum=-1;
    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);
    private Context context;
    boolean jud=true;


    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ppname;
        ImageView ppphoto;
        LinearLayout backround;
        LinearLayout layout_back;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            ppname = itemView.findViewById(R.id.pp_list_name);
            ppphoto = itemView.findViewById(R.id.pp_list_photo);
            backround = itemView.findViewById(R.id.photo_backround);
            layout_back = itemView.findViewById(R.id.layout_back);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemClick(v, pos);
                        }

                        if(mSelectedItems.get(pos)){
                            mSelectedItems.put(pos,false);
                            itemView.findViewById(R.id.layout_back).setBackgroundColor(Color.WHITE);
                           ppname.setTextColor(Color.parseColor("#263061"));
                           backround.setBackgroundResource(R.drawable.sport_photo_backround);
                        }
                        else{
                            mSelectedItems.put(pos,true);
                            itemView.findViewById(R.id.layout_back).setBackgroundResource(R.drawable.sport_click_listevent);
                            ppname.setTextColor(Color.WHITE);
                            backround.setBackgroundResource(R.drawable.sport_click_listevent);

                            for(int i=0;i< mData.size();++i) {
                                if(i!= pos)mSelectedItems.put(i, false);

                            }
                            notifyDataSetChanged();
                        }

                    }
                }
            });

        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    sportListAdapter(ArrayList<Sport> list, Context context) {
        this.mData = list ;
        this.context= context;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public sportListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.pp_list_item, parent, false) ;
        sportListAdapter.ViewHolder vh = new sportListAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(sportListAdapter.ViewHolder holder, int position) {


        holder.ppname.setText(mData.get(position).getName()) ;
        holder.ppphoto.setImageResource(mData.get(position).getDrawable_id());

//        if(jud) {
//            Animation animation = AnimationUtils.loadAnimation(context, R.anim.pop_up);
//            holder.layout_back.setAnimation(animation);
//        }
//        if(position ==  5 ){
//            jud = false;
//        }

        if (  mSelectedItems.get(position, false) ){
            holder.itemView.findViewById(R.id.layout_back).setBackgroundResource(R.drawable.sport_click_listevent);
            holder.ppname.setTextColor(Color.WHITE);
            holder.backround.setBackgroundResource(R.drawable.sport_click_listevent);

        } else {
            holder.itemView.findViewById(R.id.layout_back).setBackgroundColor(Color.WHITE);
            holder.ppname.setTextColor(Color.parseColor("#263061"));
            holder.backround.setBackgroundResource(R.drawable.sport_photo_backround);

        }


    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }

    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }

    public void setOnItemClickListener(sportListAdapter.OnItemClickListener listener){
        this.mListener = listener;
    }
}
