package com.example.todorealm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.RealmResults;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    private RealmResults<MyDataModel> data;

    public DataAdapter(RealmResults<MyDataModel> data) {
        this.data = data;
    }

    public void updateData(RealmResults<MyDataModel> newData) {
        data = newData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        MyDataModel item = data.get(position);
        holder.textName.setText(item.getName());
        holder.textAge.setText(String.valueOf(item.getAge()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textAge;

        DataViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textAge = itemView.findViewById(R.id.textAge);
        }
    }
}
