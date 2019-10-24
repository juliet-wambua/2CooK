package com.nea.a2cook.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nea.a2cook.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.findMealsButton)
    Button mFindMealsButton;
    @BindView(R.id.mealsTextView)
    EditText mMealsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        ButterKnife.bind ( this );

        mFindMealsButton.setOnClickListener ( this );
    }

    @Override
    public void onClick(View v) {
        if (v == mFindMealsButton) {
            String meals = mMealsTextView.getText ().toString ();
            Intent intent = new Intent ( MainActivity.this, CookActivity.class );
            intent.putExtra ( "meals", meals );
            startActivity ( intent );
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
