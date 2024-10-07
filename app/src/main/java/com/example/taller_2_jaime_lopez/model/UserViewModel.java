package com.example.taller_2_jaime_lopez.model;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UserViewModel extends AndroidViewModel {

    private final MutableLiveData<String> userName;
    private final SharedPreferences sharedPreferences;

    public UserViewModel(Application application) {
        super(application);
        sharedPreferences = application.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        userName = new MutableLiveData<>();
        loadUserName();
    }

    public LiveData<String> getUserName() {
        return userName;
    }

    public void saveUserName(String name) {
        userName.setValue(name);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName", name);
        editor.apply();
    }

    private void loadUserName() {
        String name = sharedPreferences.getString("userName", "");
        userName.setValue(name);
    }
}

