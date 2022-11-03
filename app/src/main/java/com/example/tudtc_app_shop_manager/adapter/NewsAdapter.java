package com.example.tudtc_app_shop_manager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tudtc_app_shop_manager.R;
import com.example.tudtc_app_shop_manager.model.News;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {
    private List<News> list;
    private Context context;

    public NewsAdapter(List<News> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        News news = list.get(position);
        if (news != null){
            holder.tvTitle.setText(news.getTitle());
            holder.tvDescription.setText(news.getDescription());
            Glide.with(context).load(news.getImage()).into(holder.img);
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(context, WebViewNewsActivity.class);
//                    intent.putExtra("link", news.getLink());
//                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    public class NewsHolder extends RecyclerView.ViewHolder{
        private LinearLayout layout;
        private TextView tvTitle, tvDescription;
        private ImageView img;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout_item_news);
            tvTitle = itemView.findViewById(R.id.tv_item_title_new);
            tvDescription = itemView.findViewById(R.id.tv_description_item_news);
            img = itemView.findViewById(R.id.img_item_news);
        }
    }

    public void setData(List<News> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
