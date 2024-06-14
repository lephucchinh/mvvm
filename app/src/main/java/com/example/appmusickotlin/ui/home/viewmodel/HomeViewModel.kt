package com.example.appmusickotlin.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appmusickotlin.ui.home.Fragment.HomeFragment

class HomeViewModel : ViewModel() {
    private var _currentFragment = MutableLiveData<String>()
    var currentFragment: LiveData<String> = _currentFragment


}