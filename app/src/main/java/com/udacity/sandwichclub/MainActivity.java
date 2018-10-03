package com.udacity.sandwichclub;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.udacity.sandwichclub.adapter.MainListAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String[] sandwiches = getResources().getStringArray(R.array.sandwich_names);
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);

//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_list_item_1, sandwiches);

        MainListAdapter mainAdapter = new MainListAdapter(this,sandwiches);

        // Simplification: Using a ListView instead of a RecyclerView
        ListView listView = findViewById(R.id.sandwiches_listview);
        listView.setAdapter(mainAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                launchDetailActivity(position,view);
            }
        });
    }

    private void launchDetailActivity(int position, View view) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_POSITION, position);
        view.setTransitionName(String.valueOf(position));
        ActivityOptions transitionActivityOption = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,view,view.getTransitionName());
        startActivity(intent,transitionActivityOption.toBundle());
    }
}
