package com.iolab.sightlocator;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.iolab.sightlocator.Appl.ViewUpdateListener;

public class SightsTextFragment extends Fragment implements 
											OnMapClickListener,
											OnMapLongClickListener,
											OnMarkerClickListener, 
											ViewUpdateListener {

	private static final LatLng RAILWAY_STATION 			= new LatLng(49.839860,23.993669);
	private static final LatLng STS_OLHA_AND_ELISABETH 		= new LatLng(49.8367019,24.0048451);
	private static final LatLng SOFTSERVE_OFFICE_4 			= new LatLng(49.832786,23.997022);

	private static String PathToSdcard = Environment
			.getExternalStorageDirectory() + "/Download/";
	private static final String ONE_PIXEL = 					PathToSdcard + "onePixel.jpg";
	private static final String STRING_STS_OLHA_AND_ELISABETH = PathToSdcard + "elis.jpg";
	private static final String STRING_RAILWAY_STATION = 		PathToSdcard + "railway.jpg";
	private static final String STRING_SOFTSERVE_OFFICE_4 = 	PathToSdcard + "ss_logo.jpg";
	
	private final int ICON_SIZE = 200;
	public static long updateMarkerClickIndex=0;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			updateMarkerClickIndex = savedInstanceState.getLong("updateViewCallIndex", 0);
		}
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.text_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (savedInstanceState != null) {
			String uri = savedInstanceState.getString("uri");

			if (null != uri) {
				ImageView imageView = (ImageView) getActivity().findViewById(
						R.id.imageView);
				imageView.setImageURI(Uri.parse(uri));
				if (!uri.contains("onePixel")) {
					imageView.setImageBitmap(resizeBitmap(imageView, ICON_SIZE));
				}

				imageView.setTag(R.string.imageview_tag_uri, uri);
			}
		}
		registerImageViewClickListener();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Appl.subscribeForMarkerClickUpdates(this);
		Appl.subscribeForMapClickUpdates(this);
		Appl.subscribeForMapLongClickUpdates(this);
		Appl.subscribeForViewUpdates(this);
	}

	/**
	 * Resize bitmap to New Width (set by method argument) by New
	 * Height(calculated according aspect ratio)
	 * 
	 * @param imageView
	 *            the Image View where the bitmap is placed
	 * @return the resized image bitmap
	 */
	protected Bitmap resizeBitmap(ImageView imageView, int newWidth) {
		// get the bitmap from Image View
		Bitmap bitmapFromImageView = ((BitmapDrawable) imageView.getDrawable())
				.getBitmap();
		// get Width and Height
		int originalWidth = bitmapFromImageView.getWidth();
		int originalHeight = bitmapFromImageView.getHeight();
		// find the proportion (aspect ratio)
		float scale = (float) newWidth / originalWidth;
		// find new Height keeping aspect ratio
		int newHeight = (int) Math.round(originalHeight * scale);
		// get new resized Bitmap
		Bitmap resizedBitmap = Bitmap.createScaledBitmap(
				bitmapFromImageView,
				newWidth, 
				newHeight, 
				true);
		return resizedBitmap;
	}

	/**
	 * Change text in text fragment to new one.
	 * 
	 * @param newText
	 *            the new text to be displayed
	 */
	private void changeTextFragment(String newText) {
		TextView textView = getTextView();
		textView.setText(newText);
	}

	private TextView getTextView() {
		if (getActivity().getFragmentManager() == null){
			Log.d("MSG","getFragmentManager is null ");
			
//			return (TextView) getView().findViewById(R.id.textView);
		}

		Fragment textFragmet = getActivity().getFragmentManager().findFragmentById(R.id.text_fragment);
		TextView textView = (TextView) textFragmet.getView().findViewById(R.id.textView);
		//TextView textView = (TextView) getView().findViewById(R.id.textView);
		return textView;
	}

	/**
	 * On markers click change image fragment from default (one pixel image) to
	 * an icon, using image uri. The uri is stored in ImageView tag and then
	 * used in onSaveInstanceState() and onActivityCreated methods
	 * 
	 * @param uri
	 *            the uri, path to device Download folder
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private void changeImageFragmentUsingImageUri(String uri) {
		ImageView imageView = getImageView();

		imageView.setImageURI(Uri.parse(uri));

		if (!uri.contains("onePixel")) {
			Bitmap resizedBitmap = resizeBitmap(imageView, ICON_SIZE);
			imageView.setImageBitmap(resizedBitmap);
		}

		imageView.setTag(R.string.imageview_tag_uri, uri);
	}

	protected ImageView getImageView() {
		Fragment fragment = getFragmentManager().findFragmentById(
				R.id.text_fragment);
		ImageView imageView = (ImageView) fragment.getView().findViewById(
				R.id.imageView);
		return imageView;
	}

	public boolean onMarkerClick(final Marker marker) {
		Log.d("MSG", "onMarkerClick(final Marker marker)");
		return false;
		}
	
//	public boolean onMarkerClick(final Marker marker) {
//
//		String railwayStation = this.getString(R.string.railway_station_wiki);
//		String softserveOffice4 = this.getString(R.string.softserve_office_4);
//		String stsOlhaAndElisabeth = this
//				.getString(R.string.sts_olha_and_elisabeth);
//
//		if (marker.getPosition().equals(RAILWAY_STATION)) {
//			marker.showInfoWindow();
//			changeTextFragment(railwayStation);
//			changeImageFragmentUsingImageUri(STRING_RAILWAY_STATION);
//			return true;
//		}
//
//		if (marker.getPosition().equals(SOFTSERVE_OFFICE_4)) {
//			marker.showInfoWindow();
//			changeTextFragment(softserveOffice4);
//			changeImageFragmentUsingImageUri(STRING_SOFTSERVE_OFFICE_4);
//			return true;
//		}
//
//		if (marker.getPosition().equals(STS_OLHA_AND_ELISABETH)) {
//			marker.showInfoWindow();
//			changeTextFragment(stsOlhaAndElisabeth);
//			changeImageFragmentUsingImageUri(STRING_STS_OLHA_AND_ELISABETH);
//			return true;
//		}
//
//		return false;
//	}

	@Override
	public void onMapLongClick(LatLng arg0) {

		String loremIpsum = getString(R.string.lorem_ipsum);
		// changes the text fragment to default (lorem ipsum text)
		changeTextFragment(loremIpsum);
		// changes the image fragment to default (one pixel image)
		changeImageFragmentUsingImageUri(ONE_PIXEL);
	}

	@Override
	public void onMapClick(LatLng arg0) {

		String loremIpsum = getString(R.string.lorem_ipsum);
		// changes the text fragment to default (lorem ipsum text)
		changeTextFragment(loremIpsum);
		// changes the image fragment to default (one pixel image)
		changeImageFragmentUsingImageUri(ONE_PIXEL);
	}

	private void registerImageViewClickListener() {
		ImageView imageView = getImageView();
		imageView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
//				Log.d("MSG", " view click");
			}
		});
	}

	@Override
	public void onSaveInstanceState(Bundle args) {
		super.onSaveInstanceState(args);
		ImageView imageView = (ImageView) getActivity().findViewById(
				R.id.imageView);
		String uri = (String) imageView.getTag(R.string.imageview_tag_uri);
		if (null != uri) {
			args.putString("uri", uri);
		}
		args.putLong("updateMarkerClickIndex", updateMarkerClickIndex);
	}
	
	@Override
	public void onPause(){
		super.onPause();
		Appl.unsubscribeFromMarkerClickUpdates(this);
		Appl.unsubscribeFromMapClickUpdates(this);
		Appl.unsubscribeFromMapLongClickUpdates(this);
		Appl.subscribeForViewUpdates(this);
	}
	
	@Override
	public void onUpdateView(Bundle bundle) {
//		String sightDescription = bundle.getString(Tags.SIGHT_DESCRIPTION);
		if (bundle.getString(Tags.SIGHT_DESCRIPTION) != null) {
			Log.d("MSG", "onUpdateView TXT:  Bundle String sightDescription: "
					+ bundle.getString(Tags.SIGHT_DESCRIPTION));
			String sightDescription = bundle.getString(Tags.SIGHT_DESCRIPTION);

			// Log.d("MSG",
			// "onUpdateView:  Bundle String sightDescription: "+bundle.getString(Tags.SIGHT_DESCRIPTION));
			Log.d("MSG", "onUpdateView TXT:  Bundle ON_MARKER_CLICK_INDEX: "
					+ bundle.getLong(Tags.ON_MARKER_CLICK_INDEX));
			Log.d("MSG", "onUpdateView TXT:  Bundle updateMarkerClickIndex: "
					+ updateMarkerClickIndex);
			
			changeTextFragment(sightDescription);

//			if (sightDescription == null
//					&& bundle.getLong(Tags.ON_MARKER_CLICK_INDEX) == updateMarkerClickIndex) {
//				changeTextFragment("Welcome to Sight Locator");
//			} else {
//				changeTextFragment(sightDescription);
//			}
		}
	}
	
}
