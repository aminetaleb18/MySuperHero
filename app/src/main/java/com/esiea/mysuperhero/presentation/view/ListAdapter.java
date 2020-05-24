package com.esiea.mysuperhero.presentation.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.esiea.mysuperhero.R;
import com.esiea.mysuperhero.presentation.model.Hero;
import com.squareup.picasso.Picasso;

import java.util.List;

public class   ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<Hero> values;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void itemClick(Hero heroItem);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView nameHero;
        ImageView imHero;
        View layout;

        ViewHolder(View v) {
            super(v);
            layout = v;
            nameHero = (TextView) v.findViewById(R.id.name);
            imHero = (ImageView)v.findViewById(R.id.icon);
        }
    }

    public void add(int position, Hero hero) {
        values.add(position, hero);
        notifyItemInserted(position);
    }

     void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
     public ListAdapter (List<Hero> myDataset, OnItemClickListener listener) {
        this.values = myDataset;
        this.listener = listener;
     }

    // Create new views (invoked by the layout manager)
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Hero currentHero = values.get(position);
        holder.nameHero.setText(currentHero.getName());
        /* gestion du chargement des images */
        String imageUri = currentHero.getUrl();
        Picasso.get().load(imageUri).into(holder.imHero);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                listener.itemClick(currentHero);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

}
