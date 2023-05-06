package com.example.medical;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DiagnosisAdapter extends RecyclerView.Adapter<DiagnosisAdapter.DiagnosisVH> {

    List<Diagnosis> diagnosisList;

    public DiagnosisAdapter(List<Diagnosis> diagnosisList) {
        this.diagnosisList = diagnosisList;
    }

    @NonNull
    @Override
    public DiagnosisVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);

        return new DiagnosisVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiagnosisVH holder, int position) {
        Diagnosis diagnosis = diagnosisList.get(position);
        holder.titleName.setText(diagnosis.getProblem());
        holder.treatments.setText(diagnosis.getSolution());

        boolean isExpandable = diagnosisList.get(position).isExpendable();
        holder.relativeLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return diagnosisList.size();
    }

    public class DiagnosisVH extends RecyclerView.ViewHolder {

        TextView titleName, treatments;
        LinearLayout linearLayout;
        RelativeLayout relativeLayout;

        public DiagnosisVH(@NonNull View itemView) {
            super(itemView);

            titleName = itemView.findViewById(R.id.problem_name);
            treatments = itemView.findViewById(R.id.treatment_text);

            linearLayout = itemView.findViewById(R.id.linearL);
            relativeLayout = itemView.findViewById(R.id.expandable_layout);


            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Diagnosis diagnosis = diagnosisList.get(getBindingAdapterPosition());
                    diagnosis.setExpendable(!diagnosis.isExpendable());
                    notifyItemChanged(getBindingAdapterPosition());
                }
            });


        }
    }
}
