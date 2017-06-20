package com.ben.fdam_ver_020.adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ben.fdam_ver_020.R;
import com.ben.fdam_ver_020.activity.AddDeviceActivity;
import com.ben.fdam_ver_020.activity.InfoDescriptionActivity;
import com.ben.fdam_ver_020.bean.Description;
import com.ben.fdam_ver_020.utils.ItemClick;

import java.util.List;

public class DescriptionHistoryAdapter extends RecyclerView.Adapter<DescriptionHistoryAdapter.DescriptionHistoryHolder> {

    public DescriptionHistoryAdapter(List<Description> descriptions) {
        this.descriptions = descriptions;
    }

    private List<Description> descriptions;

    public static class DescriptionHistoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardView;
        TextView textView_date;
        TextView textView_description;

        private ItemClick listener;

        public DescriptionHistoryHolder(final View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.card_history_description);
            textView_date = (TextView) itemView.findViewById(R.id.textView_history_date);
            textView_description = (TextView) itemView.findViewById(R.id.textView_history_description);

            cardView.setPreventCornerOverlap(false); //Instead, it adds padding to avoid such intersection (See setPreventCornerOverlap(boolean) to change this behavior)

            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            listener.onItemClick(v, this.getLayoutPosition());
        }

        public void setOnItemClickListener(ItemClick listener){
            this.listener = listener;
        }
    }

    @Override
    public DescriptionHistoryHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_description_item, viewGroup, false);
        return new DescriptionHistoryAdapter.DescriptionHistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(DescriptionHistoryHolder holder, int i) {
        holder.textView_date.setText(descriptions.get(i).getDescription_date());
        holder.textView_description.setText(descriptions.get(i).getDescription_item());

        holder.setOnItemClickListener(new ItemClick() {
            @Override
            public void onItemClick(View view, int position) {

                switch (view.getId()) {
                    case R.id.card_history_description:

                        Intent intent = new Intent(view.getContext(), InfoDescriptionActivity.class);
                        intent.putExtra("Description", descriptions.get(position));
                        view.getContext().startActivity(intent);

                        String s = descriptions.get(position).getDescription_date() + " - " + descriptions.get(position).getDescription_item();
                        Toast.makeText(view.getContext(), s, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return descriptions.size();
    }
}
