package com.mqhamdam.myphotoalbum;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder>{
    List<EImages> eImagesList = new ArrayList<>();

    public void setImages(List<EImages> eImagesList) {
        this.eImagesList = eImagesList;
    }

    public EImages getImageAt(int position) {
        return eImagesList.get(position);
    }


    @NonNull
    @Override
    public ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_card,parent,false);
        return new ImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesViewHolder holder, int position) {
        EImages eImages = eImagesList.get(position);
        holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(eImages.getImage(),0,eImages.getImage().length));
        holder.textViewTitle.setText(eImages.getImage_title());
        holder.textViewDescription.setText(eImages.getImage_description());
    }

    @Override
    public int getItemCount() {
        return eImagesList.size();
    }

    public static class ImagesViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewTitle, textViewDescription;

        public ImagesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.txtTitle);
            textViewDescription = itemView.findViewById(R.id.txtDescr);
        }


    }

}
