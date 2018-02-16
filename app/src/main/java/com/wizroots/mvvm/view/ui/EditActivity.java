package com.wizroots.mvvm.view.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.wizroots.mvvm.R;
import com.wizroots.mvvm.viewmodel.DetailActivityViewModel;
import com.wizroots.mvvm.viewmodel.DetailViewModelFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EditActivity extends AppCompatActivity {

    private static final String TAG = EditActivity.class.getSimpleName();
    @BindView(R.id.layout_txt) TextInputLayout mLayout;
    @BindView(R.id.edt_edit_name) EditText mName;

    private DetailActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);

        long id=getIntent().getLongExtra(MainActivity.PARAM1,0);
        mViewModel= ViewModelProviders.of(this,new DetailViewModelFactory(this.getApplication(),id)).get(DetailActivityViewModel.class);

    }

    @OnClick(R.id.btn_edit)
    void editName(){
        String name=mName.getText().toString();
        if(validateName(name)) {
            Completable.fromAction(() -> mViewModel.updateName(name))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::finish, throwable -> Log.e(TAG,"Error while updating"));
        }
    }


    private boolean validateName(String name){
        if(TextUtils.isEmpty(name)) {
            mLayout.setError("Name can't be empty");
            return false;
        }
        else mLayout.setError(null);
        return true;
    }
}
