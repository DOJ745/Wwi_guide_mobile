package by.bstu.faa.wwi_guide_mobile.ui.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import by.bstu.faa.wwi_guide_mobile.R;


public class TokenResultAdapter extends RecyclerView.Adapter<TokenResultAdapter.TokenResultHolder> {

    @NonNull
    @Override
    public TokenResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TokenResultHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class TokenResultHolder extends RecyclerView.ViewHolder {

        private TextView tokenTextView;
        private ImageView tokenImageView;

        public TokenResultHolder(@NonNull View itemView) {
            super(itemView);

            tokenTextView = itemView.findViewById(R.id.token_item_value);
            tokenImageView = itemView.findViewById(R.id.token_item_image);
        }
    }
}
