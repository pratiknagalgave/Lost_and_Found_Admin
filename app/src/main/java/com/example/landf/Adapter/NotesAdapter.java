package com.example.landf.Adapter;

import android.content.Context;
import android.content.Intent;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.landf.EditActivity;
import com.example.landf.Model.Listdata;
import com.example.landf.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyHolder>
{

    List<Listdata> noteslist;
    private Context context;
    public  NotesAdapter(List<Listdata> noteslist,Context context)
    {
        this.context=context;
        this.noteslist=noteslist;

    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,viewGroup,false);

        MyHolder myHolder=new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int position) {
        Listdata data=noteslist.get(position);
        myHolder.title.setText(data.getTitle());
        myHolder.desc.setText(data.getDesc());
        myHolder.postedtime.setText(data.getPosteddate());
        myHolder.password.setText(data.getPassword());
        Picasso
                .get()
                .load(data.getUrlidtv())
                .into( myHolder.imageV);

    }

    @Override
    public int getItemCount() {
        return noteslist.size();
    }

    class  MyHolder extends RecyclerView.ViewHolder  {
        TextView title,desc, postedtime, password;

        ImageView imageV;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            desc=itemView.findViewById(R.id.desc);
            postedtime = itemView.findViewById(R.id.postedtime);
            password = itemView.findViewById(R.id.password);
            //imageuritv = itemView.findViewById(R.id.urlidtv);
            imageV = itemView.findViewById(R.id.imageuri);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Listdata listdata=noteslist.get(getAdapterPosition());
                    Intent i=new Intent(context, EditActivity.class);
                    i.putExtra("id",listdata.id);
                    i.putExtra("title",listdata.title);
                    i.putExtra("desc",listdata.desc);
                    i.putExtra("postedtime",listdata.posteddate);
                    i.putExtra("password", listdata.password);
                    i.putExtra("urlidtv", listdata.urlidtv);

                    context.startActivity(i);
                    }
            });

        }


    }
}
