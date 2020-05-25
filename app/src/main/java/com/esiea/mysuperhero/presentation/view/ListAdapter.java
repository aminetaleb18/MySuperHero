package com.esiea.mysuperhero.presentation.view;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.esiea.mysuperhero.R;
import com.esiea.mysuperhero.Singletons;
import com.esiea.mysuperhero.presentation.model.Hero;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class   ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements Filterable {
    private List<Hero> values;
    private List<Hero> valuesFiltered;
    private OnItemClickListener listener;
    int positionClicked = 0;

    public void setFavori (Context context, String isfavori) {
        Toast.makeText(context,"favori : "+isfavori, Toast.LENGTH_LONG);
        Log.e("position"," : pos : "+positionClicked);
        Hero hero = Singletons.getHeroPosition(positionClicked);
        hero.setFavori(isfavori);
        notifyDataSetChanged();
    }


    public interface OnItemClickListener{
        void itemClick(Hero heroItem);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView nameHero;
        ImageView imHero;
        ImageView fav;
        View layout;

        ViewHolder(View v) {
            super(v);
            layout = v;
            nameHero = (TextView) v.findViewById(R.id.name);
            imHero = (ImageView)v.findViewById(R.id.icon);
            fav = (ImageView)v.findViewById(R.id.favori);

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
        this.valuesFiltered = myDataset;
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
        final Hero currentHero = valuesFiltered.get(position);
        positionClicked = position;
        holder.nameHero.setText(currentHero.getName());
        /* gestion du chargement des images */
        String imageUri = currentHero.getUrl();
        Picasso.get().load(imageUri).into(holder.imHero);

        if (currentHero.getFavori() != null) {
            if ( currentHero.getFavori().equals("1") )
                holder.fav.setVisibility(View.VISIBLE);
            else
                holder.fav.setVisibility(View.INVISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                positionClicked = position;
                listener.itemClick(currentHero);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return valuesFiltered.size();
    }

    @Override
    public Filter getFilter () {
        return new Filter() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    valuesFiltered = values;
                } else {
                    List<Hero> filteredList = new ArrayList<>();
                    for (Hero row : values) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    valuesFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = valuesFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                valuesFiltered = (ArrayList<Hero>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


}
