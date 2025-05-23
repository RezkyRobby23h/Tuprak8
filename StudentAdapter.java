package com.example.localsqlite;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> implements Filterable {
    private final ArrayList<Student> students = new ArrayList<>();
    private final ArrayList<Student> studentsFiltered = new ArrayList<>();
    private final Activity activity;

    public StudentAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students.clear();
        this.studentsFiltered.clear();
        if (students.size() > 0) {
            this.students.addAll(students);
            this.studentsFiltered.addAll(students);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.bind(studentsFiltered.get(position));
    }

    @Override
    public int getItemCount() {
        return studentsFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    studentsFiltered.clear();
                    studentsFiltered.addAll(students);
                } else {
                    ArrayList<Student> filteredList = new ArrayList<>();
                    for (Student student : students) {
                        // Mencari karakter di tengah data (konten nama)
                        if (student.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(student);
                        }
                    }
                    studentsFiltered.clear();
                    studentsFiltered.addAll(filteredList);
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = studentsFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                studentsFiltered.clear();
                studentsFiltered.addAll((ArrayList<Student>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {
        final TextView tvName, tvNim, tvTimestamp;
        final CardView cardView;

        StudentViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvNim = itemView.findViewById(R.id.tv_item_nim);
            tvTimestamp = itemView.findViewById(R.id.tv_item_timestamp);
            cardView = itemView.findViewById(R.id.card_view);
        }

        void bind(Student student) {
            tvName.setText(student.getName());
            tvNim.setText(student.getNim());

            // Menampilkan timestamp dengan format sesuai jenis operasi
            String timestampText = student.getTimestamp();
            if (timestampText.startsWith("Created")) {
                tvTimestamp.setText(timestampText);
            } else if (timestampText.startsWith("Updated")) {
                tvTimestamp.setText(timestampText);
            } else {
                tvTimestamp.setText(timestampText);
            }

            cardView.setOnClickListener(v -> {
                Intent intent = new Intent(activity, FormActivity.class);
                intent.putExtra(FormActivity.EXTRA_STUDENT, student);
                activity.startActivityForResult(intent, FormActivity.REQUEST_UPDATE);
            });
        }
    }
}