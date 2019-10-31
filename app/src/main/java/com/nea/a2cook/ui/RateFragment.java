package com.nea.a2cook.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nea.a2cook.R;

public class RateFragment extends Fragment {
    private TextView mTextView;
    private Button mDismissButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_rate_fragment, container, false);

        mDismissButton = v.findViewById(R.id.dismissButton);
        mDismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.remove(new RateFragment()).commit();
            }
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null){
            TextView textView = view.findViewById(R.id.rateTextView);
//            textView.setText("Hello");
            Log.e("====================", "Working");
        }
    }
}
