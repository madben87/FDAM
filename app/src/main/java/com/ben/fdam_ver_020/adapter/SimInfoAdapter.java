package com.ben.fdam_ver_020.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ben.fdam_ver_020.R;
import com.ben.fdam_ver_020.bean.Sim;
import com.ben.fdam_ver_020.bean.SimHistory;
import com.ben.fdam_ver_020.utils.ItemClick;

import java.util.List;

public class SimInfoAdapter extends RecyclerView.Adapter<SimInfoAdapter.SimInfoHolder> {

    public SimInfoAdapter(List<SimHistory> simHistories) {
        this.simHistories = simHistories;
    }

    private List<SimHistory> simHistories;
    //private int id_device;

    public static class SimInfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardView;
        TextView textView_install;
        TextView textView_uninstall;
        TextView textView_id_device;

        private ItemClick listener;

        public SimInfoHolder(final View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.card_info_history_sim);
            textView_install = (TextView) itemView.findViewById(R.id.textView_info_history_sim_install);
            textView_uninstall = (TextView) itemView.findViewById(R.id.textView_info_history_sim_uninstall);
            textView_id_device = (TextView) itemView.findViewById(R.id.textView_info_history_num_device);

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
    public SimInfoHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.info_history_sim_item, viewGroup, false);
        return new SimInfoAdapter.SimInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(SimInfoHolder holder, int i) {

        /*if (sims.get(i).getSimHistories().get(sims.get(i).getSimHistories().size()-1).getDate_uninstall() == null) {
            holder.textView_install.setText("Install");
        }else {
            holder.textView_install.setText("Uninstall");
        }

        holder.textView_num_device.setText(sims.get(i).getSim_num());*/

        if (simHistories.get(i).getId_device() != null) {
            holder.textView_id_device.setText(simHistories.get(i).getId_device());
        }

        if (simHistories.get(i).getDate_install() != null) {
            holder.textView_install.setText(simHistories.get(i).getDate_install());
        }

        if (simHistories.get(i).getDate_uninstall() != null) {
            holder.textView_uninstall.setText(simHistories.get(i).getDate_uninstall());
        }

        holder.setOnItemClickListener(new ItemClick() {
            @Override
            public void onItemClick(View view, int position) {

                switch (view.getId()) {
                    case R.id.card_history_sim:
                        String s = simHistories.get(position).getId_device();
                        Toast.makeText(view.getContext(), s, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return simHistories.size();
    }
}
