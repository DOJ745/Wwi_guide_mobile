package by.bstu.faa.wwi_guide_mobile.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import by.bstu.faa.wwi_guide_mobile.R;

public class RankSpinnerAdapter extends ArrayAdapter<String> {

    String[] dayOfWeek = { "Понедельник", "Вторник", "Среда", "Четверг",
            "Котопятница", "Субкота", "Воскресенье" };

    public RankSpinnerAdapter(Context context, int textViewResourceId, String[] objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rank_row, parent, false);

        TextView name = itemView.findViewById(R.id.name);
        name.setText(dayOfWeek[position]);

        ImageView icon = itemView.findViewById(R.id.icon);

        if (dayOfWeek[position].equals("Котопятница") || dayOfWeek[position].equals("Субкота")) {
            icon.setImageResource(R.drawable.ic_launcher_foreground);
        } else {
            icon.setImageResource(R.drawable.ic_launcher_background);
        }
        return itemView;
    }
}
