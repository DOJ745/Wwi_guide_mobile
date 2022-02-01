package by.bstu.faa.wwi_guide_mobile.ui.adapters;

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
import by.bstu.faa.wwi_guide_mobile.constants.Constants;
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

    // Put data in UI elements

    @Override
    public void onBindViewHolder(@NonNull YearResultHolder holder, int position) {

        YearDto year = items.get(position);

        holder.yearDateTextView.setText(String.valueOf(year.getDate()));
        holder.yearTitleTextView.setText(year.getTitle());

        if (year.getImgUrl() != null) {
            Glide.with(holder.itemView).load(year.getImgUrl()).into(holder.yearImageView);
        }
        else {
            Glide.with(holder.itemView).load(Constants.Values.NO_IMG_URL).into(holder.yearImageView);
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

    // Define elements in view of our item

    class YearResultHolder extends RecyclerView.ViewHolder {

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
