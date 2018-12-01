package com.example.maru.despro;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Monitor extends Fragment{

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView= inflater.inflate (R.layout.monitor_tab2, container ,false);
        return  rootView;
    }
}
