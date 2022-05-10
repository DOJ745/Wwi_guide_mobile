package by.bstu.faa.wwi_guide_mobile.ui.fragments.adapters;

import android.content.Context;
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
import by.bstu.faa.wwi_guide_mobile.database.entities.YearEntity;

public class YearsRecyclerAdapter extends RecyclerView.Adapter<YearsRecyclerAdapter.YearResultHolder>
        implements AdapterSetItems<YearEntity> {

    public interface OnItemClickListener { void onItemClick(YearEntity item, int position); }
    private final YearsRecyclerAdapter.OnItemClickListener onClickListener;

    private List<YearEntity> items = new ArrayList<>();
    private final LayoutInflater inflater;

    public YearsRecyclerAdapter(Context context, YearsRecyclerAdapter.OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.inflater = LayoutInflater.from(context);
    }

    // Layout for item
    @NonNull
    @Override
    public YearResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.collection_item_year, parent, false);
        return new YearResultHolder(itemView);
    }

    // Put data in elements of item's UI
    @Override
    public void onBindViewHolder(@NonNull YearResultHolder holder, int position) {
        Typeface typeface = Typeface.createFromAsset(holder.itemView.getContext().getAssets(), "font/old_type_nr_regular.ttf");
        holder.dateTextView.setTypeface(typeface);
        holder.titleTextView.setTypeface(typeface);

        YearEntity item = items.get(position);

        holder.dateTextView.setText(String.valueOf(item.getDate()));
        holder.titleTextView.setText(item.getTitle());

        Glide
                .with(holder.itemView)
                .asBitmap()
                .load(item.getImg())
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

    public void setItems(List<YearEntity> items) {
        this.items = items;
        this.notifyDataSetChanged();
    }

    // Define elements in view of our item
    static class YearResultHolder extends RecyclerView.ViewHolder {
        private final TextView dateTextView;
        private final TextView titleTextView;
        private final ImageView imgView;

        public YearResultHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.item_year_date);
            titleTextView = itemView.findViewById(R.id.item_year_title);
            imgView = itemView.findViewById(R.id.item_year_img);
        }
    }
}
