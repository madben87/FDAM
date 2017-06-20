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
import com.ben.fdam_ver_020.activity.InfoDeviceActivity;
import com.ben.fdam_ver_020.activity.InfoSimActivity;
import com.ben.fdam_ver_020.bean.Description;
import com.ben.fdam_ver_020.bean.Sim;
import com.ben.fdam_ver_020.bean.SimHistory;
import com.ben.fdam_ver_020.database.SimDaoImpl;
import com.ben.fdam_ver_020.utils.ItemClick;

import java.util.List;

public class SimHistoryAdapter extends RecyclerView.Adapter<SimHistoryAdapter.SimHistoryHolder> {

    public SimHistoryAdapter(List<Sim> sims/*, int id_device*/) {
        this.sims = sims;
        //this.id_device = id_device;
    }

    private List<Sim> sims;
    //private int id_device;

    public static class SimHistoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardView;
        TextView textView_install;
        TextView textView_num_device;

        private ItemClick listener;

        public SimHistoryHolder(final View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.card_history_sim);
            textView_install = (TextView) itemView.findViewById(R.id.textView_history_sim_install);
            textView_num_device = (TextView) itemView.findViewById(R.id.textView_history_num_device);

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
    public SimHistoryHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_sim_item, viewGroup, false);
        return new SimHistoryAdapter.SimHistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(SimHistoryHolder holder, int i) {

        if (sims.get(i).getSimHistories().get(sims.get(i).getSimHistories().size()-1).getDate_uninstall() == null) {
            holder.textView_install.setText("Install");
        }else {
            holder.textView_install.setText("Uninstall");
        }

        holder.textView_num_device.setText(sims.get(i).getSim_num());

        holder.setOnItemClickListener(new ItemClick() {
            @Override
            public void onItemClick(View view, int position) {

                switch (view.getId()) {
                    case R.id.card_history_sim:

                        Intent intentInfo = new Intent(view.getContext(), InfoSimActivity.class);
                        intentInfo.putExtra("Sim", sims.get(position));
                        view.getContext().startActivity(intentInfo);

                        String s = sims.get(position).getSim_num();
                        Toast.makeText(view.getContext(), s, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return sims.size();
    }
}
