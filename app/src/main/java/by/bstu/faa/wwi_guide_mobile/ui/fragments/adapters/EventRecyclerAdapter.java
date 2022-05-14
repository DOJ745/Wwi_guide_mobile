package by.bstu.faa.wwi_guide_mobile.ui.fragments.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.database.entities.EventEntity;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.EventResultHolder>
        implements AdapterSetItems<EventEntity> {

    public interface OnItemClickListener { void onItemClick(EventEntity item, int position); }
    private final OnItemClickListener onClickListener;

    private List<EventEntity> items = new ArrayList<>();
    private final LayoutInflater inflater;

    public EventRecyclerAdapter(Context context, OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public EventRecyclerAdapter.EventResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = inflater.inflate(R.layout.collection_item_data, parent, false);
        return new EventResultHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventRecyclerAdapter.EventResultHolder holder, int position) {
        Typeface typeface = Typeface.createFromAsset(holder.itemView.getContext().getAssets(), "font/old_type_nr_regular.ttf");
        holder.titleTextView.setTypeface(typeface);
        holder.titleTextView.setTextColor(Color.BLACK);

        EventEntity item = items.get(position);
        holder.titleTextView.setText(item.getTitle());

        Glide
                .with(holder.itemView)
                .asBitmap()
                .load(item.getImages().get(0))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(holder.imgView);

        holder.itemView.setOnClickListener(v -> onClickListener.onItemClick(item, position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<EventEntity> items) {
        this.items = items;
        this.notifyDataSetChanged();
    }

    static class EventResultHolder extends RecyclerView.ViewHolder {

        private final TextView titleTextView;
        private final ImageView imgView;

        public EventResultHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.item_data_title);
            imgView = itemView.findViewById(R.id.item_data_img);
        }
    }
}
