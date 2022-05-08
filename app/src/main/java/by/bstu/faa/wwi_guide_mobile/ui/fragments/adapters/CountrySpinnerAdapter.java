package by.bstu.faa.wwi_guide_mobile.ui.fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.R;
import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.CountryDto;

public class CountrySpinnerAdapter extends ArrayAdapter<CountryDto> implements AdapterSetItems<CountryDto> {

    private List<CountryDto> items;

    public CountrySpinnerAdapter(Context context, int textViewResourceId, List<CountryDto> objects) {
        super(context, textViewResourceId, objects);
        this.items = objects;
    }

    public void setItems(List<CountryDto> items){
        this.items = items;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() { return items.size(); }

    @Override
    public CountryDto getItem(int position) { return items.get(position); }

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
                .inflate(R.layout.row_country, parent, false);

        TextView name = itemView.findViewById(R.id.name);
        name.setText(items.get(position).getName());

        ImageView flag = itemView.findViewById(R.id.icon);

        if (items.get(position).getImg() != null) {
            Glide.with(itemView).load(items.get(position).getImg()).into(flag);
        }
        else {
            Glide.with(itemView).load(CONSTANTS.URLS.NO_IMG).into(flag);
        }

        return itemView;
    }
}
