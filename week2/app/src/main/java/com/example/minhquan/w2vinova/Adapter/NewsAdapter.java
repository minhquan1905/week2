package com.example.minhquan.w2vinova.Adapter;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.minhquan.w2vinova.Model.DataSearch;
import com.example.minhquan.w2vinova.Model.Doc;
import com.example.minhquan.w2vinova.R;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    List<Doc> listNews;
    Context context;
    Doc doc;

    public NewsAdapter(Context context) {
        listNews = new ArrayList<>();
        this.context = context;
    }

    public void setData(List<Doc> docs){
        this.listNews = docs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_adapter_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        doc = listNews.get(position);
        if (doc.getMultimedia().size() != 0){
            Glide.with(context).load(doc.getMultimedia().get(0).getUrl()).into(holder.imageNews);
        }else{
            holder.imageNews.setImageResource(R.drawable.nytimes);
        }

        holder.titleNews.setText(doc.getHeadline().getMain());
        holder.titleNews.setWidth(holder.cardView.getWidth());

    }

    @Override
    public int getItemCount() {
        return listNews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageNews;
        TextView titleNews;
        CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview);
            imageNews = itemView.findViewById(R.id.imgNews);
            titleNews = itemView.findViewById(R.id.tvTitleNews);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //Toast.makeText(context, "Clicked!!!", Toast.LENGTH_SHORT).show();

            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();

            builder.setToolbarColor(ContextCompat.getColor(context,R.color.colorPrimary));
            builder.addDefaultShareMenuItem();

            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_share_2);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "https://www.nytimes.com/");

            int requestCode = 100;

            PendingIntent pendingIntent = PendingIntent.getActivity(context,
                    requestCode,
                    intent,
                    0);

            builder.setActionButton(bitmap, "Share Link", pendingIntent, true);

            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(context, Uri.parse(doc.getWebUrl()));

        }
    }
}
