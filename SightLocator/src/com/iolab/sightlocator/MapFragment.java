package com.iolab.sightlocator;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MapFragment extends Fragment {
	@Override
	public void onCreate(Bundle savedInstanceState){
		Log.d("MyLogs","onCreate started");
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		Log.d("MyLogs","onCreateView started");
		if(container==null){
			return null;
		}
		return inflater.inflate(R.layout.map_fragment, container, false);
	}
}
