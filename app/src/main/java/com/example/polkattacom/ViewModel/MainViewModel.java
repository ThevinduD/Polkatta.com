package com.example.polkattacom.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.polkattacom.Domain.BannerModel;
import com.example.polkattacom.Domain.CategoryModel;
import com.example.polkattacom.Repository.MainRepository;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {
    private final MainRepository repository = new MainRepository();

    public LiveData<ArrayList<CategoryModel>> loadCategory() {
        return repository.loadCategory();
    }

    public LiveData<ArrayList<BannerModel>> loadBanner() { return repository.loadBanner(); }
}
