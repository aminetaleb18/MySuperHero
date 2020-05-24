package com.esiea.mysuperhero.presentation.view;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.esiea.mysuperhero.R;

import java.util.ArrayList;

public class DataList extends RecyclerView.Adapter<DataList.ViewHolder>{

    private int listLayoutItem;
    private ArrayList<Item> ListItem;

    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView heroName;
        LinearLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
       //     heroName = itemView.findViewById(R.id.det)
       //    parentLayout = itemView.findViewById(R.id.layout);
        }

        @Override
        public void onClick(View v) {
            //nothing
        }
    }

    public DataList(Context context, int layoutId, ArrayList<Item> itemList){
        mContext = context;
        listLayoutItem = layoutId;
        this.ListItem = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listLayoutItem, parent, false);
        DataList.ViewHolder myViewHolder = new DataList.ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Item i = ListItem.get(position);
     //   holder.mNom.setText(i.getNom());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Information quand on clique sur un nom de la liste
                LayoutInflater factory = LayoutInflater.from(mContext);
                final View dialogContactView = factory.inflate(R.layout.detail_hero, null);
                AlertDialog.Builder diag = new AlertDialog.Builder(mContext);
                diag.setTitle("Information");
                diag.setView(dialogContactView);


                diag.setPositiveButton("AJOUTER AU FAVORI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                 /*       Toast toast = Toast.makeText(mContext, ListItem.get(position).getNom() + " a été supprimé !", Toast.LENGTH_LONG);
                        toast.show();

                        ListItem.remove(position);
                        notifyItemRemoved(position);
                        notifyItemChanged(position, getItemCount());*/
                    }
                });

                diag.setNegativeButton("FERMER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //nothing
                    }
                });



                diag.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ListItem.size();
    }


}
