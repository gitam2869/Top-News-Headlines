package com.example.topnewsheadlines;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<NewsInfo> newsInfoArrayList;
    private OnItemClickListener mListener;

    public NewsAdapter(ArrayList<NewsInfo> newsInfoArrayList) {
        this.newsInfoArrayList = newsInfoArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.all_news_list, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        NewsInfo newsInfo = newsInfoArrayList.get(position);

        holder.textViewHeadline.setText(newsInfo.getHeadline());
        holder.textViewPublishedAt.setText(newsInfo.getPublishedAt());

        Picasso.get().load(newsInfo.getImage()).into(holder.imageViewNewsImage, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }


    //create variable

    @Override
    public int getItemCount() {
        return newsInfoArrayList.size();
    }

    //interface

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onItemClick1(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView materialCardAllNews;
        ImageView imageViewNewsImage;
        TextView textViewHeadline;
        TextView textViewPublishedAt;
        TextView textViewSource;

        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            materialCardAllNews = itemView.findViewById(R.id.idCardViewAllNews);
            imageViewNewsImage = itemView.findViewById(R.id.idImageViewImageAllNews);
            textViewHeadline = itemView.findViewById(R.id.idTextViewHeadlineAllNews);
            textViewPublishedAt = itemView.findViewById(R.id.idTextViewPublishedAtAllNews);
            textViewSource = itemView.findViewById(R.id.idTextViewSourceAllNews);

            progressBar = itemView.findViewById(R.id.idProgressbarImageLoadingAllNews);

            materialCardAllNews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            textViewSource.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick1(position);
                        }
                    }
                }
            });
        }
    }
}
