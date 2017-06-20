package com.ben.fdam_ver_020.adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ben.fdam_ver_020.R;
import com.ben.fdam_ver_020.activity.AddDeviceActivity;
import com.ben.fdam_ver_020.activity.InfoDeviceActivity;
import com.ben.fdam_ver_020.bean.Device;
import com.ben.fdam_ver_020.utils.ItemClick;

import java.util.List;

public class DeviceResViewAdapter extends RecyclerView.Adapter<DeviceResViewAdapter.DeviceViewHolder> {

    private List<Device> devices;

    public static class DeviceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardView;
        TextView textView_id;
        TextView textView_name;
        TextView textView_location;
        TextView textView_phone;
        ImageButton edit_button;

        private ItemClick listener;

        public DeviceViewHolder(final View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.card);
            textView_id = (TextView) itemView.findViewById(R.id.textView_id);
            textView_name = (TextView) itemView.findViewById(R.id.textView_name);
            textView_location = (TextView) itemView.findViewById(R.id.textView_location);
            textView_phone = (TextView) itemView.findViewById(R.id.textView_phone);
            edit_button = (ImageButton) itemView.findViewById(R.id.editDeviceButton);

            cardView.setPreventCornerOverlap(false); //Instead, it adds padding to avoid such intersection (See setPreventCornerOverlap(boolean) to change this behavior)

            edit_button.setOnClickListener(this);
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

    public DeviceResViewAdapter(List<Device> testList) {
        this.devices = testList;
    }

    @Override
    public DeviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item, parent, false);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DeviceViewHolder holder, int position) {

        holder.textView_id.setText(String.valueOf(devices.get(position).getDevice_id()));
        holder.textView_name.setText(devices.get(position).getDevice_name());
        holder.textView_location.setText(devices.get(position).getDevice_location());

        if (devices.get(position).getSims().size() > 0) {
            holder.textView_phone.setText(devices.get(position).getSims().get(devices.get(position).getSims().size()-1).getSim_num());
        }else {
            holder.textView_phone.setText("No sim");
        }

        holder.setOnItemClickListener(new ItemClick() {
            @Override
            public void onItemClick(View view, int position) {

                switch (view.getId()){
                    case R.id.editDeviceButton:
                        Intent intentEdit = new Intent(view.getContext(), AddDeviceActivity.class);
                        intentEdit.putExtra("Device", devices.get(position));
                        view.getContext().startActivity(intentEdit);
                        break;
                    case R.id.card:
                        Intent intentInfo = new Intent(view.getContext(), InfoDeviceActivity.class);
                        intentInfo.putExtra("Device", devices.get(position));
                        view.getContext().startActivity(intentInfo);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }
}
