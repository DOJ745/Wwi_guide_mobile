package by.bstu.faa.wwi_guide_mobile.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.constants.Constants;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.RankDto;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.YearDto;

public class RankSpinnerAdapter extends ArrayAdapter<RankDto> {

    private List<RankDto> items;
    public RankSpinnerAdapter(Context context, int textViewResourceId, List<RankDto> objects) {
        super(context, textViewResourceId, objects);
        this.items = objects;
    }

    public void setItems(List<RankDto> items){
        this.items = items;
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
        name.setText(items.get(position).getName());

        ImageView icon = itemView.findViewById(R.id.icon);

        if (items.get(position).getImgUrl() != null) {
            Glide.with(itemView).load(items.get(position).getImgUrl()).into(icon);
        }
        else {
            Glide.with(itemView).load(Constants.Values.NO_IMG_URL).into(icon);
        }

        return itemView;
    }
}
