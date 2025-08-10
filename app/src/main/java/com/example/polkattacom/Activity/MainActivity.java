package com.example.polkattacom.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import com.example.polkattacom.Adapter.BannerAdapter;
import com.example.polkattacom.Adapter.CategoryAdapter;

import com.example.polkattacom.Adapter.PopularAdapter;
import com.example.polkattacom.Domain.BannerModel;
import com.example.polkattacom.R;
import com.example.polkattacom.ViewModel.MainViewModel;
import com.example.polkattacom.databinding.ActivityMainBinding;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    ImageButton callBtn;
    ImageButton mapButton;

    static int PERMISSON_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(
                getWindow(),
                getWindow().getDecorView()
        );

        if (controller != null) {
            controller.hide(WindowInsetsCompat.Type.statusBars() | WindowInsetsCompat.Type.navigationBars());
            controller.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        }

        viewModel = new MainViewModel();

        callBtn = findViewById(R.id.callBtn);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSON_CODE);
        }

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNo = "0715779072";
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:"+phoneNo));
                startActivity(i);
            }
        });

        mapButton = findViewById(R.id.btn_open_map);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open StoreMapActivity on button click
                Intent intent = new Intent(MainActivity.this, StoreMapActivity.class);
                startActivity(intent);
            }
        });
        
        initCategory();
        initBanner();
        initPopular();
        initBottomNavigation();
    }

    private void initBottomNavigation() {
        binding.bottomNavigation.setItemSelected(R.id.home, true);
        binding.bottomNavigation.setOnItemSelectedListener((ChipNavigationBar.OnItemSelectedListener) i -> {
            if (i==R.id.home) {

            } else if (i==R.id.cart) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });
    }

    private void initPopular() {
        binding.progressBarPopular.setVisibility(View.VISIBLE);
        viewModel.loadPopular().observeForever(itemsModels -> {
            if(!itemsModels.isEmpty()) {
                binding.popularView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                binding.popularView.setAdapter(new PopularAdapter(itemsModels));
                binding.popularView.setNestedScrollingEnabled(true);

            }
            binding.progressBarPopular.setVisibility(View.GONE);
        });
        viewModel.loadPopular();
    }

    private void initCategory() {
        binding.progressBarCategory.setVisibility(View.VISIBLE);
        viewModel.loadCategory().observeForever(categoryModels -> {
            binding.categoryView.setLayoutManager(new LinearLayoutManager(
                    MainActivity.this, LinearLayoutManager.HORIZONTAL, false
            ));
            binding.categoryView.setAdapter(new CategoryAdapter(categoryModels));
            binding.categoryView.setNestedScrollingEnabled(true);
            binding.progressBarCategory.setVisibility(View.GONE);
        });
    }

    private void banners(ArrayList<BannerModel> bannerModels) {
        binding.viewPagerBanner.setAdapter(new BannerAdapter(bannerModels, binding.viewPagerBanner));
        binding.viewPagerBanner.setClipToPadding(false);
        binding.viewPagerBanner.setClipChildren(false);
        binding.viewPagerBanner.setOffscreenPageLimit(3);
        binding.viewPagerBanner.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));

        binding.viewPagerBanner.setPageTransformer(compositePageTransformer);
    }

    private void initBanner() {
        binding.progressBarBanner.setVisibility(View.VISIBLE);
        viewModel.loadBanner().observeForever(bannerModels -> {
            if (bannerModels!=null && !bannerModels.isEmpty()){
                banners(bannerModels);
                binding.progressBarBanner.setVisibility(View.GONE);
            }
        });
    }
}