package com.example.polkattacom.Repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.polkattacom.Domain.BannerModel;
import com.example.polkattacom.Domain.CategoryModel;
import com.example.polkattacom.Domain.ItemsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainRepository {

    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://polkatta-com-default-rtdb.asia-southeast1.firebasedatabase.app");

    public LiveData<ArrayList<CategoryModel>> loadCategory() {
        MutableLiveData<ArrayList<CategoryModel>> listData = new MutableLiveData<>();
        DatabaseReference ref = firebaseDatabase.getReference("Category");

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<CategoryModel> list = new ArrayList<>();
                for (DataSnapshot childSnapShot:snapshot.getChildren()){
                    CategoryModel item = childSnapShot.getValue(CategoryModel.class);
                    if (item!=null) {
                        Log.d("FirebaseItem", "Loaded: " + item.getTitle());
                        list.add(item);
                    }
                }
                listData.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return listData;
    }

    public LiveData<ArrayList<BannerModel>> loadBanner() {
        MutableLiveData<ArrayList<BannerModel>> listData = new MutableLiveData<>();
        DatabaseReference ref = firebaseDatabase.getReference("Banner");

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<BannerModel> list = new ArrayList<>();
                for (DataSnapshot childSnapShot:snapshot.getChildren()){
                    BannerModel item = childSnapShot.getValue(BannerModel.class);
                    if (item!=null) {
                        list.add(item);
                    }
                }
                listData.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return listData;
    }

    public LiveData<ArrayList<ItemsModel>> loadPopular() {
        MutableLiveData<ArrayList<ItemsModel>> listData = new MutableLiveData<>();
        DatabaseReference ref = firebaseDatabase.getReference("Items");

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ItemsModel> list = new ArrayList<>();
                for (DataSnapshot childSnapShot:snapshot.getChildren()){
                    ItemsModel item = childSnapShot.getValue(ItemsModel.class);
                    if (item!=null) {
                        list.add(item);
                    }
                }
                listData.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return listData;
    }
}
