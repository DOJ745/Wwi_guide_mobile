package by.bstu.faa.wwi_guide_mobile.ui.fragments.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.database.entities.TestThemeEntity;

public class TestsThemesRecyclerAdapter extends RecyclerView.Adapter<TestsThemesRecyclerAdapter.TestThemeResultHolder>
        implements AdapterSetItems<TestThemeEntity> {

    public interface OnItemClickListener { void onItemClick(TestThemeEntity item, int position); }
    private final TestsThemesRecyclerAdapter.OnItemClickListener onClickListener;
    private List<TestThemeEntity> items = new ArrayList<>();
    private final LayoutInflater inflater;

    public TestsThemesRecyclerAdapter(Context context, OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TestsThemesRecyclerAdapter.TestThemeResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.collection_item_test_theme, parent, false);
        return new TestsThemesRecyclerAdapter.TestThemeResultHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TestsThemesRecyclerAdapter.TestThemeResultHolder holder, int position) {
        Typeface typeface = Typeface.createFromAsset(holder.itemView.getContext().getAssets(), "font/old_type_nr_regular.ttf");
        holder.nameTextView.setTypeface(typeface);

        TestThemeEntity item = items.get(position);
        holder.nameTextView.setText(item.getName());

        holder.itemView.setOnClickListener(v -> onClickListener.onItemClick(item, position));
    }

    @Override
    public int getItemCount() { return items.size(); }

    @Override
    public void setItems(List<TestThemeEntity> items) {
        this.items = items;
        this.notifyDataSetChanged();
    }

    static class TestThemeResultHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;

        public TestThemeResultHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.item_test_theme_name);
        }
    }
}
