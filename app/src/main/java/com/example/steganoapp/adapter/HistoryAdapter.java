package com.example.steganoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.steganoapp.R;
import com.example.steganoapp.model.history.DataHistory;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context context;
    private List<DataHistory> listData;
    private View.OnClickListener onClickListener;
    private String fileName;
    public  HistoryAdapter(){}
    public HistoryAdapter(Context context, List<DataHistory> listData, View.OnClickListener onClickListener) {
        this.context = context;
        this.listData = listData;
        this.onClickListener = onClickListener;
    }
    public HistoryAdapter(Context context, List<DataHistory> listData) {
        this.context = context;
        this.listData = listData;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_history, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DataHistory dataHistory = listData.get(position);
        holder.tv_date.setText(dataHistory.getDate());
        holder.tv_title.setText(dataHistory.getEmbeddingFile());
        holder.btnDownload.setOnClickListener(onClickListener);
        setFileName(dataHistory.getEmbeddingFile());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_date;
        TextView tv_title;
        ImageButton btnDownload;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.file_name);
            tv_date = itemView.findViewById(R.id.tv_date);
            btnDownload = itemView.findViewById(R.id.btn_download);
        }
    }
}
