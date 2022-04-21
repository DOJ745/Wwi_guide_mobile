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
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.EventDto;

public class EventsRecyclerAdapter extends RecyclerView.Adapter<EventsRecyclerAdapter.EventResultHolder> {

    public interface OnEventClickListener { void onEventClick(EventDto event, int position); }

    private final OnEventClickListener onClickListener;
    private List<EventDto> items = new ArrayList<>();
    private final LayoutInflater inflater;

    public EventsRecyclerAdapter(Context context, OnEventClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public EventsRecyclerAdapter.EventResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = inflater.inflate(R.layout.collection_item_event, parent, false);
        return new EventResultHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsRecyclerAdapter.EventResultHolder holder, int position) {

        EventDto item = items.get(position);
        holder.eventTitleTextView.setText(item.getTitle());

        if (item.getImages() != null) {
            Glide.with(holder.itemView).load(item.getImages().get(0)).into(holder.eventImageView);
        }
        else {
            Glide.with(holder.itemView).load(CONSTANTS.URLS.NO_IMG).into(holder.eventImageView);
        }

        holder.itemView.setOnClickListener(v -> onClickListener.onEventClick(item, position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<EventDto> items) {
        this.items = items;
        this.notifyDataSetChanged();
    }

    static class EventResultHolder extends RecyclerView.ViewHolder {

        private final TextView eventTitleTextView;
        private final ImageView eventImageView;

        public EventResultHolder(@NonNull View itemView) {
            super(itemView);
            eventTitleTextView = itemView.findViewById(R.id.event_title_value);
            eventImageView = itemView.findViewById(R.id.event_item_image);
        }
    }
}
