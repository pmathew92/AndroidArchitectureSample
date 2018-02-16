package com.wizroots.mvvm.view.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wizroots.mvvm.R;
import com.wizroots.mvvm.model.User;
import com.wizroots.mvvm.viewmodel.MainActivityViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG=MainActivity.class.getSimpleName();
    public static final String PARAM1="user_id";
    private MainActivityViewModel mViewModel;

    private final CompositeDisposable _d=new CompositeDisposable();

    private SingleObserver<Long> observer;

    @BindView(R.id.layout_sign_in)  RelativeLayout mLayoutSignIn;
    @BindView(R.id.layout_sign_up)  RelativeLayout mLayoutSignUp;
    @BindView(R.id.edt_email) TextInputEditText mSignInEmail;
    @BindView(R.id.edt_password) TextInputEditText mSignInPassword;
    @BindView(R.id.edt_signup_email) TextInputEditText mSignUpEmail;
    @BindView(R.id.edt_signup_name) TextInputEditText mSignUpName;
    @BindView(R.id.edt_signup_password) TextInputEditText mSignUpPassword;
    @BindView(R.id.layout_name) TextInputLayout mLayoutName;
    @BindView(R.id.layout_signup_email) TextInputLayout mSignUpEmailLayout;
    @BindView(R.id.layout_signup_password) TextInputLayout mSignUpPasswordLayout;
    @BindView(R.id.layout_email) TextInputLayout mSignInEmailLayout;
    @BindView(R.id.layout_password) TextInputLayout mSignInPasswordLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mViewModel= ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mViewModel.getProgressStatus().observe(this, aBoolean -> {
            if(aBoolean !=null)
                Log.d(TAG, "Show Progress ");
            else
                Log.d(TAG, "Hide progress");
        });

        observer=new SingleObserver<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                _d.add(d);
            }

            @Override
            public void onSuccess(Long aLong) {
                mViewModel.setProgressStatus(false);
                signInUser(aLong);
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getBaseContext(),"Incorrect Details!!",Toast.LENGTH_SHORT).show();
            }
        };

    }

    @OnClick(R.id.txt_sign_up)
    protected void toggleSignup(){
        mLayoutSignIn.setVisibility(View.GONE);
        mLayoutSignUp.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.txt_sign_in)
    protected void toggleSignin(){
        mLayoutSignUp.setVisibility(View.GONE);
        mLayoutSignIn.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_sign_up)
    public void onSignUpClick(){
        String name=mSignUpName.getText().toString();
        String password=mSignUpPassword.getText().toString();
        String email=mSignUpEmail.getText().toString();
        if(validateSignUp(name,email,password)){
            User user=new User(name,email,password);
            mViewModel.insertUser(user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }

    }


    @OnClick(R.id.btn_sign_in)
    public void onSignInClick(){
        String password=mSignInPassword.getText().toString();
        String email=mSignInEmail.getText().toString();
        if(validateSigIn(email,password)){
            mViewModel.checkUser(email,password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }
    }

    /**
     * Method to check whether the sign-up fields are empty or not
     * @param name
     * @param email
     * @param password
     * @return
     */
    private boolean validateSignUp(String name,String email,String password) {
        boolean error=false;
        if(TextUtils.isEmpty(name)){
            mLayoutName.setError("Name shouldn't be empty ");
            error=true;
        }else mLayoutName.setError(null);

        if(TextUtils.isEmpty(email)){
                mSignUpEmailLayout.setError("Email shouldn't be empty");
                error=true;
        }else mSignUpEmailLayout.setError(null);

        if(TextUtils.isEmpty(password)){
            mSignUpPasswordLayout.setError("Password shouldn't be empty");
            error=true;
        }else mSignUpPasswordLayout.setError(null);


        return !error;
    }

    /**
     * MEthod to check whether the sign-in fields are empty or not
     * @param email
     * @param password
     * @return
     */
    private boolean validateSigIn(String email,String password){
        boolean error=false;

        if(TextUtils.isEmpty(email)){
            mSignInEmailLayout.setError("Email shouldn't be empty");
            error=true;
        }else mSignInEmailLayout.setError(null);

        if(TextUtils.isEmpty(password)){
            mSignInPasswordLayout.setError("Password shouldn't be empty");
            error=true;
        }else mSignInPasswordLayout.setError(null);


        return !error;
    }

    /**
     * Method to redirect the user to the next page
     * @param userId
     */
    private void signInUser(long userId){
        Intent intent=new Intent(getApplicationContext(),DetailActivity.class);
        intent.putExtra(PARAM1,userId);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        _d.clear();
    }
}
