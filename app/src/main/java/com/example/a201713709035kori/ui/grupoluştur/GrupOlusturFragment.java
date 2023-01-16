package com.example.a201713709035kori.ui.grupoluştur;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a201713709035kori.GrupModel;
import com.example.a201713709035kori.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public class GrupOlusturFragment extends Fragment {
    TextView grupAdı;
    TextView grupAciklamasi;
    ImageView grupResmi;
    Button grupOlusturButton;
    RecyclerView gruplar;

    FirebaseAuth mAuth;
    FirebaseFirestore mStore;
    FirebaseStorage mStorage;

    Uri resimUri;
    ArrayList<GrupModel> grupModelArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_grup_olustur,container,false);

        grupAdı=view.findViewById(R.id.grupOlustur_grupadı);
        grupAciklamasi=view.findViewById(R.id.grupOlustur_grupaciklamasi);
        grupResmi=view.findViewById(R.id.grupOlustur_grupresmi);
        grupOlusturButton= view.findViewById(R.id.grupOlustur_grupolusturbutonu);
        gruplar=view.findViewById(R.id.grupOlustur_gruplar);

        mAuth =FirebaseAuth.getInstance();
        mStore=FirebaseFirestore.getInstance();
        mStorage= FirebaseStorage.getInstance();
        grupModelArrayList = new ArrayList<>();

        ActivityResultLauncher<Intent> activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result ->
{      if (result.getResultCode() ==getActivity().RESULT_OK){
       Intent     data=result.getData();
resimUri=data.getData();
               grupResmi.setImageURI(resimUri);
    }
});

grupResmi.setOnClickListener(new View.OnClickListener() {
               @Override
 public void onClick (View v ) {
Intent  intent=new Intent();
 intent.setType("image/*");
intent.setAction(Intent.ACTION_GET_CONTENT);    activityResultLauncher.launch(intent);
}
});

 grupOlusturButton.setOnClickListener(v -> {

     String grupAdi= grupAdı.getText().toString();
     String grupAciklama= grupAciklamasi.getText().toString();

     if (grupAdi.isEmpty() || grupAciklama.isEmpty()) {

        Toast.makeText(getContext(),"Lütfen ilgili tüm alanları doldurunuz",Toast.LENGTH_SHORT).show();
     return;
     }

     if (resimUri != null)
     {

         StorageReference storageReference =mStorage.getReference().child("resimleri/"  + UUID.randomUUID().toString());

         storageReference.putFile(resimUri).addOnSuccessListener(taskSnapshot -> {
 storageReference.getDownloadUrl().addOnSuccessListener(uri ->
         {
             String resimUrl=uri.toString();
           Toast.makeText(getContext(),"Grup Oluşturuldu",Toast.LENGTH_SHORT).show();
           grupOluştur(grupAdi,grupAciklama,resimUrl);
         });
         });
         return;
     }
     else {

         grupOluştur(grupAdi,grupAciklama,null);
     }

 });



 gruplariGetir();
return view;
}
  private void grupOluştur(String grupAdi, String grupAciklama,String resimUrl)
  {

      String kullaniciId=mAuth.getUid();

      mStore.collection("userdata/"+kullaniciId+ "gruplar/").add(new HashMap<String,Object>() {
          {
              put("Grup adi", grupAdi);
              put("Grup aciklama", grupAciklama);
              put("Grup resmi", resimUrl);
              put("number", new ArrayList<String>());
          }}).addOnSuccessListener(aVoid ->
      {
          Toast.makeText(getContext(),"Grup oluşturuldu", Toast.LENGTH_SHORT).show();
          gruplariGetir();
      }).addOnFailureListener(e ->{
          Toast.makeText(getContext(),"Grup oluşturulamadı", Toast.LENGTH_SHORT).show();
      });

      }

       private void gruplariGetir(){

        String kullaniciId=mAuth.getUid();
        mStore.collection( "/userData"+ kullaniciId + "/gruplar").get().addOnSuccessListener(v ->
        {
            grupModelArrayList.clear();
            for (DocumentSnapshot documentSnapshot : v.getDocuments())
            {
               GrupModel grupModel =documentSnapshot.toObject(GrupModel.class);
               grupModelArrayList.add(grupModel);
            }
            gruplar.setAdapter(new GrupAdapter(grupModelArrayList));
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
            gruplar.setLayoutManager(linearLayoutManager);
        });
       }

}
