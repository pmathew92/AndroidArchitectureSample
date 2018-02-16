package com.wizroots.mvvm.view.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.wizroots.mvvm.R;
import com.wizroots.mvvm.viewmodel.DetailActivityViewModel;
import com.wizroots.mvvm.viewmodel.DetailViewModelFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.txt_name) TextView mName;
    @BindView(R.id.txt_email) TextView mEmail;
    @BindView(R.id.btn_fab) FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        long id=getIntent().getLongExtra(MainActivity.PARAM1,0);
        DetailActivityViewModel mViewModel = ViewModelProviders.of(this, new DetailViewModelFactory(this.getApplication(), id)).get(DetailActivityViewModel.class);

        mFab.setOnClickListener(v->{
            Intent intent=new Intent(getBaseContext(),EditActivity.class);
            intent.putExtra(MainActivity.PARAM1,id);
            startActivity(intent);
        });

        mViewModel.getObservableUserData().observe(this, user->{
            assert user != null;
            mName.setText(user.getName());
            mEmail.setText(user.getEmail());
        });
    }
}
