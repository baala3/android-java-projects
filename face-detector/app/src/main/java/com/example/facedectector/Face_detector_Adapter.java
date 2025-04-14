package com.example.facedectector;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class Face_detector_Adapter extends RecyclerView.Adapter<Face_detector_Adapter.FaceViewHolder> {
    private ArrayList<Face_detection_Model> faceDetectionModelArrayList;


    public Face_detector_Adapter(ArrayList<Face_detection_Model> faceDetectionModelArrayList) {
        this.faceDetectionModelArrayList = faceDetectionModelArrayList;
    }

    @NonNull
    @Override
    public FaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_face_dector,parent,false);
     return new FaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FaceViewHolder holder, int position) {
        Face_detection_Model face_detection_model=faceDetectionModelArrayList.get(position);
        holder.textView1.setText(String.valueOf(face_detection_model.getId()));
        holder.textView2.setText(face_detection_model.getText());

    }

    @Override
    public int getItemCount() {
        return faceDetectionModelArrayList.size();
    }

    public class FaceViewHolder extends RecyclerView.ViewHolder{
public TextView textView1;
public TextView textView2;
        public FaceViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1=itemView.findViewById(R.id.item_face_detectionn_textview1);
            textView2=itemView.findViewById(R.id.item_face_detectionn_textview2);
        }
    }
}
