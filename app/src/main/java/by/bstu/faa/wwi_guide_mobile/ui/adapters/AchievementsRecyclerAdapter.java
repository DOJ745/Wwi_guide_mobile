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

import java.util.ArrayList;
import java.util.List;

import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.AchievementDto;

public class AchievementsRecyclerAdapter extends RecyclerView.Adapter<AchievementsRecyclerAdapter.AchievementResultHolder>
        implements AdapterSetItems<AchievementDto> {

    private List<AchievementDto> items = new ArrayList<>();
    private final LayoutInflater inflater;

    public AchievementsRecyclerAdapter(Context context) { this.inflater = LayoutInflater.from(context); }

    @NonNull
    @Override
    public AchievementResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.collection_item_achievement, parent, false);
        return new AchievementsRecyclerAdapter.AchievementResultHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementResultHolder holder, int position) {
        AchievementDto item = items.get(position);

        holder.achievementItemName.setText(item.getName());
        holder.achievementItemDescription.setText(item.getDescription());
        if (item.getImg() != null) {
            Glide.with(holder.itemView).load(item.getImg()).into(holder.achievementImageView);
        }
        else {
            Glide.with(holder.itemView).load(CONSTANTS.URLS.NO_IMG).into(holder.achievementImageView);
        }
    }

    @Override
    public int getItemCount() { return items.size(); }

    public void setItems(List<AchievementDto> items) {
        this.items = items;
        this.notifyDataSetChanged();
    }

    static class AchievementResultHolder extends RecyclerView.ViewHolder {

        private final TextView achievementItemName;
        private final TextView achievementItemDescription;
        private final ImageView achievementImageView;

        public AchievementResultHolder(@NonNull View itemView) {
            super(itemView);
            achievementItemName = itemView.findViewById(R.id.achievement_item_name);
            achievementItemDescription = itemView.findViewById(R.id.achievement_item_description);
            achievementImageView = itemView.findViewById(R.id.achievement_item_img);
        }
    }
}
