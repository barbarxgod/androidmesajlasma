package com.example.a201713709035kori.ui.grupoluştur;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.a201713709035kori.GrupModel;
import com.example.a201713709035kori.R;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class GrupAdapter extends RecyclerView.Adapter<GrupAdapter.GrupViewHolder> {


    List<GrupModel> grupModelList;
    public GrupAdapter(List<GrupModel> grupModelList) {

        this.grupModelList = grupModelList;

    }
    @NonNull
    @Override
    public  GrupAdapter.GrupViewHolder onCreateViewHolder (@NonNull ViewGroup parent , int viewType) {


         GrupViewHolder grupViewHolder=new GrupViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grupolustur_grup, parent, false));
                 return grupViewHolder;
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull GrupViewHolder holder, int position) {
        GrupModel grupModel = grupModelList.get(position);
               holder.setData(grupModel);
    }


    @Override
    public int getItemCount() {
        return grupModelList.size();
    }


    public class GrupViewHolder extends RecyclerView.ViewHolder
    {
        TextView grupAdı,grupAciklamasi;
         ImageView grupResmi;
        public GrupViewHolder (View itemView)
        {
            super(itemView);
            grupResmi=itemView.findViewById(R.id.grupolustur_grup_resim);
            grupAdı=itemView.findViewById(R.id.grupolustur_grup_adı);
            grupAciklamasi=itemView.findViewById(R.id.grupolustur_grup_aciklama);

        }

        public void setData(GrupModel grupModel) {

            grupAdı.setText(grupModel.getGrupAdi());
            grupAciklamasi.setText(grupModel.getGrupAciklamasi());

            if(grupModel.getGrupResmi()!=null)
            {

            }
        }
    }

}
