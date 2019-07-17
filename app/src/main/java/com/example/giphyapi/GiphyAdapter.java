package com.example.giphyapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class GiphyAdapter extends RecyclerView.Adapter<GiphyAdapter.ViewHolder> {
    private List<String> gifUrls;
    private Context context;
    private OnGiphyClicked listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.giphy_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String url = gifUrls.get(position);
        Glide.with(context).load(url).into(holder.ivPhoto);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.giphyClicked(url);
            }
        });
    }


    public GiphyAdapter(List<String> gifUrls, OnGiphyClicked onGiphyClicked) {
        this.gifUrls = gifUrls;
        this.listener = onGiphyClicked;
    }

    @Override
    public int getItemCount() {
        return gifUrls.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivPhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.iv_photo);
        }
    }
    public interface OnGiphyClicked {
        void giphyClicked(String url);
    }
}