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
import by.bstu.faa.wwi_guide_mobile.database.entities.AchievementEntity;
import lombok.Setter;

public class AchievementsRecyclerAdapter extends RecyclerView.Adapter<AchievementsRecyclerAdapter.AchievementResultHolder>
        implements AdapterSetItems<AchievementEntity> {

    private List<AchievementEntity> items = new ArrayList<>();
    @Setter
    private List<String> userAchievements = new ArrayList<>();
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
        Typeface typeface = Typeface.createFromAsset(holder.itemView.getContext().getAssets(), "font/old_type_nr_regular.ttf");
        holder.nameTextView.setTypeface(typeface);
        holder.nameTextView.setTextColor(Color.BLACK);
        holder.descriptionTextView.setTypeface(typeface);
        holder.descriptionTextView.setTextColor(Color.BLACK);

        AchievementEntity item = items.get(position);
        holder.nameTextView.setText(item.getName());
        holder.descriptionTextView.setText(item.getDescription());
        if(userAchievements.contains(item.getId())){
            holder.imgView.setAlpha(1.0f);
        }
        Glide
                .with(holder.itemView)
                .asBitmap()
                .load(item.getImg())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(holder.imgView);

    }

    @Override
    public int getItemCount() { return items.size(); }

    public void setItems(List<AchievementEntity> items) {
        this.items = items;
        this.notifyDataSetChanged();
    }

    static class AchievementResultHolder extends RecyclerView.ViewHolder {

        private final TextView nameTextView;
        private final TextView descriptionTextView;
        private final ImageView imgView;

        public AchievementResultHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.item_achievement_name);
            descriptionTextView = itemView.findViewById(R.id.item_achievement_description);
            imgView = itemView.findViewById(R.id.item_achievement_img);
        }
    }
}
