package com.rsschool.android2021;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements GenerateButtonClickListener, BackButtonClickListener {
    private String fragmentName = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            openFirstFragment(0);
        }
    }

    private void openFirstFragment(int previousNumber) {
        fragmentName = "first";
        final Fragment firstFragment = FirstFragment.newInstance(previousNumber);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, firstFragment);
        transaction.commit();
    }

    @Override
    public void openSecondFragment(int min, int max) {
        fragmentName = "second";
        final Fragment secondFragment = SecondFragment.newInstance(min, max);
        final FragmentTransaction secondTransaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, secondFragment);
        secondTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        switch (fragmentName) {
            case "first":
                super.onBackPressed();
                break;
            case "second":
                openFirstFragment(SecondFragment.getGenerateNumber());
                break;
        }
    }

    @Override
    public void backButtonClicked(int value) {
        openFirstFragment(value);
    }
}
