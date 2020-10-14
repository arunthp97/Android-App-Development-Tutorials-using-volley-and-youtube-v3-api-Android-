package com.developer.arun.androidappdevelopmenttutorails;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> implements Filterable {
    private Context mcontext;
    private ArrayList<ExampleItem> mexamplelist;
    private ArrayList<ExampleItem> mexamplelistfull;

    public ExampleAdapter(Context mcontext, ArrayList<ExampleItem> mexamplelist) {
        this.mcontext = mcontext;
        this.mexamplelist = mexamplelist;
        mexamplelistfull=new ArrayList<>(mexamplelist);
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(mcontext).inflate(R.layout.example_item,viewGroup,false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder exampleViewHolder, final int i) {
        ExampleItem currentitem=mexamplelist.get(i);
        final String video_id=currentitem.getVideo_id();
        final String video_title=currentitem.getVideo_title();
        String url=currentitem.getUrl();

        exampleViewHolder.textView1.setText(video_title);
        Picasso.get().load(url).fit().centerInside().into(exampleViewHolder.imageView);
        exampleViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(mcontext, Beginner_Detail.class);
                    intent.putExtra("id", mexamplelist.get(i).getVideo_id());
                    mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mexamplelist.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ExampleItem> filteredlist=new ArrayList<>();
            if(constraint==null||constraint.length()==0)
            {
                filteredlist.addAll(mexamplelistfull);

            }
            else
            {
                String filterpattern=constraint.toString().toLowerCase().trim();
                for (ExampleItem exampleItem:mexamplelistfull)
                {
                    if(exampleItem.getVideo_title().toLowerCase().contains(filterpattern))
                    {
                        filteredlist.add(exampleItem);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filteredlist;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, final FilterResults results) {

                mexamplelist.clear();
                mexamplelist.addAll((ArrayList) results.values);
                notifyDataSetChanged();
        }
    };

    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView1;
        public CardView cardView;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageview);
            textView1 = (TextView) itemView.findViewById(R.id.textview1);
            cardView = (CardView) itemView.findViewById(R.id.relate);
        }
    }
}
