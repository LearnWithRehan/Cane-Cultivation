package com.example.bhagesworsurvey;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.ViewHolder> {

    private List<SurveyData> list;
    private OnItemClickListener listener;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public interface OnItemClickListener {
        void onItemClick(SurveyData data);
    }

    public SurveyAdapter(List<SurveyData> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMachineNo, tvVCode, tvGCode, tvGName, tvFName, tvVName,sdate, tvVariety, tvCtype, tvPlotNo, tvArea;

        public ViewHolder(View view) {
            super(view);
            tvMachineNo = view.findViewById(R.id.tvMachineNo);
            tvVCode = view.findViewById(R.id.tvVCode);
            tvGCode = view.findViewById(R.id.tvGCode);
            tvGName = view.findViewById(R.id.tvGName);
            tvFName = view.findViewById(R.id.tvFName);
            tvVName = view.findViewById(R.id.tvVName);
            sdate = view.findViewById(R.id.sdate);
            tvVariety = view.findViewById(R.id.tvVariety);
            tvCtype = view.findViewById(R.id.tvCtype);
            tvPlotNo = view.findViewById(R.id.tvPlotNo);
            tvArea = view.findViewById(R.id.tvArea);

            view.setOnClickListener(v -> {
                int previous = selectedPosition;
                selectedPosition = getAdapterPosition();
                notifyItemChanged(previous);
                notifyItemChanged(selectedPosition);

                // Get the clicked data
                SurveyData data = list.get(getAdapterPosition());

                // Start the new activity and pass the SurveyData as a Serializable extra
                Intent intent = new Intent(view.getContext(), SurveyDetailActivity.class);
                intent.putExtra("SurveyData", data);  // Passing SurveyData object as Serializable
                view.getContext().startActivity(intent);
            });
        }

        public void bind(SurveyData data, boolean isSelected) {
            tvMachineNo.setText(data.getMachineNo());
            tvVCode.setText(data.getV_code());
            tvGCode.setText(data.getG_code());
            tvGName.setText(data.getG_name());
            tvFName.setText(data.getF_name());
            tvVName.setText(data.getV_Name());
            sdate.setText(data.getSdate());
            tvVariety.setText(data.getVariety());
            tvCtype.setText(data.getCtype());
            tvPlotNo.setText(data.getPlotNo());
            tvArea.setText(String.valueOf(data.getAREA()));

            itemView.setBackgroundColor(isSelected ? Color.LTGRAY : Color.TRANSPARENT);
        }
    }

    @Override
    public SurveyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_survey, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SurveyAdapter.ViewHolder holder, int position) {
        holder.bind(list.get(position), position == selectedPosition);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
