package com.ben.fdam_ver_020.adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ben.fdam_ver_020.R;
import com.ben.fdam_ver_020.activity.AddStaffActivity;
import com.ben.fdam_ver_020.activity.InfoStaffActivity;
import com.ben.fdam_ver_020.bean.Staff;
import com.ben.fdam_ver_020.utils.ItemClick;

import java.util.List;

public class StaffResViewAdapter extends RecyclerView.Adapter<StaffResViewAdapter.StaffViewHolder> {

    private List<Staff> data;

    public static class StaffViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardView;
        TextView textView_name;
        TextView textView_last_name;
        TextView textView_phone;
        TextView textView_manager;
        ImageButton edit_button;

        private ItemClick listener;

        public StaffViewHolder(final View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.card_staff);
            cardView.setPreventCornerOverlap(false); //Instead, it adds padding to avoid such intersection (See setPreventCornerOverlap(boolean) to change this behavior)

            textView_name = (TextView) itemView.findViewById(R.id.textView_staff_name);
            textView_last_name = (TextView) itemView.findViewById(R.id.textView_staff_lastname);
            textView_phone = (TextView) itemView.findViewById(R.id.textView_staff_phone);
            textView_manager = (TextView) itemView.findViewById(R.id.textView_staff_manager);
            edit_button = (ImageButton) itemView.findViewById(R.id.editStaffButton);

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

    public StaffResViewAdapter(List<Staff> staffList) {
        this.data = staffList;
    }

    @Override
    public StaffResViewAdapter.StaffViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.staff_item, parent, false);
        return new StaffResViewAdapter.StaffViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final StaffResViewAdapter.StaffViewHolder holder, int position) {

        holder.textView_name.setText(data.get(position).getStaff_name());
        holder.textView_last_name.setText(data.get(position).getStaff_last_name());
        holder.textView_phone.setText(data.get(position).getStaff_phone().isEmpty() ? "No phone" : data.get(position).getStaff_phone());
        holder.textView_manager.setText(data.get(position).getStaff_manager());

        holder.setOnItemClickListener(new ItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                String s = data.get(position).getStaff_name() + " - " + data.get(position).getStaff_last_name();
                Toast.makeText(view.getContext(), s, Toast.LENGTH_SHORT).show();

                /*Intent intent = new Intent(view.getContext(), AddStaffActivity.class);
                intent.putExtra("Staff", data.get(position));
                view.getContext().startActivity(intent);*/

                switch (view.getId()) {
                    case R.id.editStaffButton:
                        Intent intentEdit = new Intent(view.getContext(), AddStaffActivity.class);
                        intentEdit.putExtra("Staff", data.get(position));
                        view.getContext().startActivity(intentEdit);
                        break;
                    case R.id.card_staff:
                        Intent intentInfo = new Intent(view.getContext(), InfoStaffActivity.class);
                        intentInfo.putExtra("Staff", data.get(position));
                        view.getContext().startActivity(intentInfo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
