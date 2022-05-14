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
import by.bstu.faa.wwi_guide_mobile.database.entities.ArmamentEntity;

public class ArmamentRecyclerAdapter extends RecyclerView.Adapter<ArmamentRecyclerAdapter.ArmamentResultHolder>
        implements AdapterSetItems<ArmamentEntity> {

    public interface OnItemClickListener { void onItemClick(ArmamentEntity item, int position); }
    private final ArmamentRecyclerAdapter.OnItemClickListener onClickListener;

    private List<ArmamentEntity> items = new ArrayList<>();
    private final LayoutInflater inflater;

    public ArmamentRecyclerAdapter(Context context, ArmamentRecyclerAdapter.OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ArmamentRecyclerAdapter.ArmamentResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = inflater.inflate(R.layout.collection_item_data, parent, false);
        return new ArmamentRecyclerAdapter.ArmamentResultHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArmamentRecyclerAdapter.ArmamentResultHolder holder, int position) {
        Typeface typeface = Typeface.createFromAsset(holder.itemView.getContext().getAssets(), "font/old_type_nr_regular.ttf");
        holder.titleTextView.setTypeface(typeface);
        holder.titleTextView.setTextColor(Color.BLACK);

        ArmamentEntity item = items.get(position);
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

    public void setItems(List<ArmamentEntity> items) {
        this.items = items;
        this.notifyDataSetChanged();
    }

    static class ArmamentResultHolder extends RecyclerView.ViewHolder {

        private final TextView titleTextView;
        private final ImageView imgView;

        public ArmamentResultHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.item_data_title);
            imgView = itemView.findViewById(R.id.item_data_img);
        }
    }

}
