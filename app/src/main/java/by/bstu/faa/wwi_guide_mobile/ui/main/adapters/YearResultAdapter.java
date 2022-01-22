package by.bstu.faa.wwi_guide_mobile.ui.main.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.YearDto;

public class YearResultAdapter extends RecyclerView.Adapter<YearResultAdapter.YearResultHolder> {

    private List<YearDto> items = new ArrayList<>();

    @NonNull
    @Override
    public YearResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.year_item, parent, false);

        return new YearResultHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull YearResultHolder holder, int position) {

        YearDto year = items.get(position);

        holder.yearDateTextView.setText(year.getDate());
        holder.yearTitleTextView.setText(year.getTitle());

        if (year.getImgUrl() != null) {
            String imageUrl = year.getImgUrl().replace("https://", "http://");

            Glide.with(holder.itemView).load(imageUrl).into(holder.yearImageView);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<YearDto> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    class YearResultHolder extends RecyclerView.ViewHolder {

        private TextView yearDateTextView;
        private TextView yearTitleTextView;
        private ImageView yearImageView;

        public YearResultHolder(@NonNull View itemView) {
            super(itemView);

            yearDateTextView = itemView.findViewById(R.id.year_date_value);
            yearTitleTextView = itemView.findViewById(R.id.year_title_value);
            yearImageView = itemView.findViewById(R.id.year_item_image);
        }
    }
}
