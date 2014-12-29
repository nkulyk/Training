package com.iolab.sightlocator;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class Appl {
	
	static Context appContext;
	
	//all the following listeners should perform their callback methods
	//in the UI thread
	static List<OnMarkerClickListener> onMarkerClickListeners = new ArrayList<GoogleMap.OnMarkerClickListener>();
	static List<OnMapClickListener> onMapClickListeners = new ArrayList<OnMapClickListener>();
	static List<OnMapLongClickListener> onMapLongClickListeners = new ArrayList<OnMapLongClickListener>();
	static List<ViewUpdateListener> viewUpdateListeners = new ArrayList<ViewUpdateListener>();
	
	/**
	 * The ResultReceiver will be used to send data from background services to
	 * the UI thread in a {@link Bundle}. When the result is received on the UI thread, all the
	 * viewUpdateListeners will have their views updated depending on the content of the Bundle.
	 */
	public static final ResultReceiver receiver = new ResultReceiver(
			new Handler()) {

		@Override
		protected void onReceiveResult(final int resultCode,
				final Bundle resultData) {
			for (ViewUpdateListener viewUpdateListener : viewUpdateListeners) {
				viewUpdateListener.onUpdateView(resultData);
			}
		}
	};
	
	public static void subscribeForMarkerClickUpdates(OnMarkerClickListener onMarkerClickListener){
		onMarkerClickListeners.add(onMarkerClickListener);
	}
	
	public static void unsubscribeFromMarkerClickUpdates(OnMarkerClickListener onMarkerClickListener){
		onMarkerClickListeners.remove(onMarkerClickListener);
	}
	
	public static void subscribeForMapClickUpdates(OnMapClickListener onMapClickListener){
		onMapClickListeners.add(onMapClickListener);
	}
	
	public static void unsubscribeFromMapClickUpdates(OnMapClickListener onMapClickListener){
		onMapClickListeners.remove(onMapClickListener);
	}
	
	public static void subscribeForMapLongClickUpdates(OnMapLongClickListener onMapLongClickListener){
		onMapLongClickListeners.add(onMapLongClickListener);
	}
	
	public static void unsubscribeFromMapLongClickUpdates(OnMapLongClickListener onMapLongClickListener){
		onMapLongClickListeners.remove(onMapLongClickListener);
	}
	
	public static void subscribeForViewUpdates(ViewUpdateListener viewUpdateListener){
		viewUpdateListeners.add(viewUpdateListener);
	}
	
	public static void unsubscribeFromViewUpdates(ViewUpdateListener viewUpdateListener){
		viewUpdateListeners.remove(viewUpdateListener);
	}
	
	interface ViewUpdateListener {
		void onUpdateView(Bundle bundle);
	}
}
