package by.bstu.faa.wwi_guide_mobile.ui.adapters;

import android.content.Context;
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
import by.bstu.faa.wwi_guide_mobile.network_service.data_objects.dto.YearDto;

public class YearsRecyclerAdapter extends RecyclerView.Adapter<YearsRecyclerAdapter.YearResultHolder>
        implements AdapterSetItems<YearDto> {

    public interface OnItemClickListener { void onItemClick(YearDto item, int position); }
    private final YearsRecyclerAdapter.OnItemClickListener onClickListener;

    private List<YearDto> items = new ArrayList<>();
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

        YearDto item = items.get(position);

        holder.yearDateTextView.setText(String.valueOf(item.getDate()));
        holder.yearTitleTextView.setText(item.getTitle());

        if (item.getImg() != null) {
           Glide
                   .with(holder.itemView)
                   .asBitmap()
                   .load(item.getImg())
                   .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                   .placeholder(R.drawable.test_icon)
                   .error(R.drawable.ic_launcher_background)
                   .into(holder.yearImageView);
        }

        holder.itemView.setOnClickListener(v -> onClickListener.onItemClick(item, position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<YearDto> items) {
        this.items = items;
        this.notifyDataSetChanged();
    }

    // Define elements in view of our item

    static class YearResultHolder extends RecyclerView.ViewHolder {

        private final TextView yearDateTextView;
        private final TextView yearTitleTextView;
        private final ImageView yearImageView;

        public YearResultHolder(@NonNull View itemView) {
            super(itemView);
            yearDateTextView = itemView.findViewById(R.id.year_date_value);
            yearTitleTextView = itemView.findViewById(R.id.year_title_value);
            yearImageView = itemView.findViewById(R.id.year_item_image);
        }
    }
}
