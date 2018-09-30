package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONArray;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView titleTV, descriptionTV, originTV, ingredientsTV, alsoKnownTV;
    private ImageView imageView;
    private View bottomScrimView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

//        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        initvies();
        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(imageView);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void initvies(){
        descriptionTV = findViewById(R.id.description_tv);
        originTV = findViewById(R.id.origin_tv);
        ingredientsTV = findViewById(R.id.ingredients_tv);
        alsoKnownTV = findViewById(R.id.also_known_tv);
        imageView = findViewById(R.id.image_iv);

    }

    private void populateUI(Sandwich sandwich) {

        if(sandwich.getDescription().isEmpty() || sandwich.getDescription().equals(" ")) {
            descriptionTV.setText(getResources().getString(R.string.not_avail));
        } else{
            descriptionTV.setText(sandwich.getDescription());
        }
        if (sandwich.getPlaceOfOrigin().isEmpty() || sandwich.getPlaceOfOrigin().equals(" ")) {
            originTV.setText(getResources().getString(R.string.not_avail));
        } else {
            originTV.setText(sandwich.getPlaceOfOrigin());
        }
//        if(sandwich.getIngredients().isEmpty() || sandwich.getIngredients().equals(" ")) {
//            ingredientsTV.setText(getResources().getString(R.string.not_avail));
//        } else{
//            ingredientsTV.setText(sandwich.getIngredients().toString());
//        }
//        if(sandwich.getAlsoKnownAs().isEmpty() || sandwich.getAlsoKnownAs().equals(" ")) {
//            alsoKnownTV.setText(getResources().getString(R.string.not_avail));
//        } else{
//            alsoKnownTV.setText(sandwich.getAlsoKnownAs().toString());
//        }
        settingList(sandwich.getIngredients(),ingredientsTV);
        settingList(sandwich.getAlsoKnownAs(),alsoKnownTV);

    }

    private void settingList(List<String> list, TextView textView) {
        if (list.isEmpty()) {
            textView.setText(getResources().getString(R.string.not_avail));
            return;
        }
        StringBuilder data = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            data.append(list.get(i));
            if (i != list.size() - 1)
                data.append(",");
        }

        textView.setText(data.toString().replace(",", "\n"));

    }
}
