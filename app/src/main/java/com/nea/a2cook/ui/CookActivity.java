package com.nea.a2cook.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nea.a2cook.R;

public class CookActivity extends AppCompatActivity {
    @BindView( R.id.mealsTextView)
    TextView mEmojiTextView;
    @BindView ( R.id.listView )
    ListView mListView;
    private String[] meals = new String[] {"Smiley", "Wink", "Wink with tongue out", "Love eyes", "Hi5 hand", "Love eyes wink"};
    private String[] price = new String[] {"Express the the smiling effect", "Crushing effect best emoji", "Express love especially when you fall in love", "A simple salutation hi5", "Expess that in love happy kafeeling"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook);
        ButterKnife.bind ( this );

        MyMealsArrayAdapter adapter = new MyMealsArrayAdapter(this, android.R.layout.simple_list_item_1, meals,price); // must match constructor!
        mListView.setAdapter ( adapter );

        mListView.setOnItemClickListener ( new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String emojis = ((TextView)view).getText().toString();
                Toast.makeText(CookActivity.this, emojis, Toast.LENGTH_LONG).show();
            }
        });
        Intent intent = getIntent();
        String emojis = intent.getStringExtra("meals");
        mEmojiTextView.setText("Here is results on : " + meals);
    }
}
