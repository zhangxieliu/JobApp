package com.fosu.jobapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.fosu.jobapp.R;
import com.fosu.jobapp.bean.ColorSuggestion;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/16.
 */

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.search_view)
    FloatingSearchView mSearchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mSearchView.setSuggestionsTextColor(Color.BLACK);
        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                //get suggestions based on newQuery

                //pass them on to the search view
                mSearchView.swapSuggestions(Arrays.asList(
                        new ColorSuggestion("green"),
                        new ColorSuggestion("blue"),
                        new ColorSuggestion("pink"),
                        new ColorSuggestion("purple"),
                        new ColorSuggestion("brown"),
                        new ColorSuggestion("gray"),
                        new ColorSuggestion("Granny Smith Apple"),
                        new ColorSuggestion("Indigo"),
                        new ColorSuggestion("Periwinkle"),
                        new ColorSuggestion("Mahogany"),
                        new ColorSuggestion("Maize"),
                        new ColorSuggestion("Mahogany"),
                        new ColorSuggestion("Outer Space"),
                        new ColorSuggestion("Melon"),
                        new ColorSuggestion("Yellow"),
                        new ColorSuggestion("Orange"),
                        new ColorSuggestion("Red"),
                        new ColorSuggestion("Orchid")));
            }
        });
    }
}
