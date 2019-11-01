package com.nea.a2cook.ui;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nea.a2cook.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LogInFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.logInBtnTextView)
    TextView mLogInBtnTextView;
    @BindView(R.id.emailEditText)
    EditText mEmailEditText;
    @BindView(R.id.passwordEditText) EditText mPasswordEditText;
    @BindView(R.id.logInProgressBar)
    ProgressBar mProgressBar;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Unbinder unbinder;



    private static final String TAG = LogInFragment.class.getSimpleName() ;

    public LogInFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        unbinder = ButterKnife.bind(this, view);
        hideProgressDialog();
        mLogInBtnTextView.setOnClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }


    public void showProgressDialog(){
        mProgressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
    public void hideProgressDialog(){
        mProgressBar.setVisibility(View.INVISIBLE);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
    private void signIn(String email, String password) {
        Log.e(TAG, "Sign in :"+ email);
        if (!validateForm()){
            return;
        }

        showProgressDialog();
        //START SIGN IN WITH EMAIL
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener( getActivity(), new OnCompleteListener<AuthResult> () {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Successful!!!",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }else {
                            Toast.makeText(getActivity(), task.getException().getLocalizedMessage() + "!!!",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        hideProgressDialog();
                    }
                });
//        sendEmailVerification();
    }

    private boolean validateForm(){
        ButterKnife.bind(this, getView());
        boolean valid = true;
        String email = mEmailEditText.getText().toString();
        String pass = mPasswordEditText.getText().toString();
        if (TextUtils.isEmpty(email)){
            mEmailEditText.setError("Email is required!!!");
            valid = false;
        }if (TextUtils.isEmpty(pass)){
            mPasswordEditText.setError("Password is required!!!");
            valid = false;
        }
        return valid;
    }

    public void updateUI(FirebaseUser user) {
        if (user != null){
            Log.e(TAG,"Congrats !!!!!" + user.getEmail().toUpperCase().charAt(0));
            Log.e(TAG, user.getUid());
            Intent intent = new Intent(getActivity(), com.nea.a2cook.ui.MainActivity.class);
            startActivity(intent);
        }else {
            Log.e(TAG,"COULD NOT AUTHENTICATE !!!!!!!!");
        }
    }

    @Override
    public void onClick(View view) {
        ButterKnife.bind(this, getView());
        if (view == mLogInBtnTextView){
            signIn(mEmailEditText.getText().toString(),mPasswordEditText.getText().toString());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}

