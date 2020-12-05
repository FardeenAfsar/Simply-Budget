package com.fardeen.budget_planner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static float Balance;
    public float getBalance(){ return Balance;}
    public void  setBalance(float newBalance){ MainActivity.Balance = newBalance;}
    InputImage image;
    private static final int GALLERY_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Load Prefs
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        Balance = sharedPreferences.getFloat("BALANCE", 0.0f);

        Bundle bundle = new Bundle();
        bundle.putFloat("Balance", Balance);
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Fragment
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                homeFragment).commit();
        bottomNav.getMenu().getItem(1).setChecked(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle bundle = new Bundle();
        bundle.putFloat("Balance", Balance);
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                homeFragment).commit();
    }

    @Override
    protected void onPause () {
        super.onPause();
        //Prefs
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("BALANCE", Balance);
        editor.apply();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            Bundle bundle = new Bundle();
                            bundle.putFloat("Balance", Balance);
                            HomeFragment homeFragment = new HomeFragment();
                            homeFragment.setArguments(bundle);
                            selectedFragment = homeFragment;
                            break;
                        case R.id.nav_stats:
                            selectedFragment = new StatsFragment();
                            break;
                        case R.id.nav_history:
                            selectedFragment = new HistoryFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                            selectedFragment).commit();
                    return true;
                }
            };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri imageData = data.getData();
            try {
                image = InputImage.fromFilePath(this, imageData);
            } catch (IOException e) {
                e.printStackTrace();
            }
            runTextRecognition(image);
        }
    }
    private void runTextRecognition(InputImage image) {
        final float[] total = new float[1];
        TextRecognizer recognizer = TextRecognition.getClient();
        recognizer.process(image)
                .addOnSuccessListener(
                        new OnSuccessListener<Text>() {
                            @Override
                            public void onSuccess(Text texts) {
                                total[0] = processTextRecognitionResult(texts);
                                openExtension(total[0]);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                                e.printStackTrace();
                            }
                        });
    }


    private float processTextRecognitionResult(Text texts) {
        List<Text.TextBlock> blocks = texts.getTextBlocks();
        if (blocks.size() == 0) {
            return 0f;
        }
        String string = texts.getText().toLowerCase();
        if(string.contains("subtotal") || string.contains("total") ){
            int index = string.indexOf("total");
            string = string.substring(index,index+20);
            string = string.replaceAll("[^\\d.]","");
            float total;
            try{
                total = Float.parseFloat(string);
            }catch (NumberFormatException e){
                total = 0;
                Toast.makeText(this,"Unable to recognize amount!",Toast.LENGTH_SHORT).show();
            }
            return total;
        }
        return 0;
    }

    public void openExtension (float total) {
        Intent intent = new Intent(this, FAB_PLUS_EXTENSION.class);
        intent.putExtra("total",String.valueOf(total));
        startActivity(intent);
    }


}