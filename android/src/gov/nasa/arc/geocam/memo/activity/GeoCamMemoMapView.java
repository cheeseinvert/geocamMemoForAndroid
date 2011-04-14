package gov.nasa.arc.geocam.memo.activity;

import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import gov.nasa.arc.geocam.memo.R;
import gov.nasa.arc.geocam.memo.UIUtils;
import gov.nasa.arc.geocam.memo.service.MemoMapOverlay;
import roboguice.activity.RoboMapActivity;
import roboguice.inject.InjectView;;

public class GeoCamMemoMapView extends RoboMapActivity{

	@InjectView(R.id.mapview)	MapView mapView;

	MapController mapController;
	List<Overlay> mapOverlays;
	Drawable drawable;
	MemoMapOverlay itemizedOverlay;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_view);
		
		//Get the latitude and longitude from the Intent
		Intent intent = getIntent();
		double latitude = intent.getDoubleExtra("Latitude", 0.00);
		double longitude = intent.getDoubleExtra("Longitude", 0.00);
				
		mapView.setBuiltInZoomControls(true);
		mapOverlays = mapView.getOverlays();
		
		drawable = this.getResources().getDrawable(R.drawable.map_marker);
		itemizedOverlay = new MemoMapOverlay(drawable);
		
		//Create a GeoPoint to signify the geolocation an overlay containing the geopoint
		GeoPoint point = new GeoPoint((int)(latitude * 1E6), 
				                      (int)(longitude * 1E6));
		OverlayItem overlayitem = new OverlayItem(point, "", "");
		itemizedOverlay.addOverlay(overlayitem);
		mapOverlays.add(itemizedOverlay);
		
		//Get the map controller and animate to the GeoPoint
		mapController = mapView.getController();
		mapController.animateTo(point);
		mapController.setZoom(16);
	}
	
	public void onHomeClick(View v) {
		UIUtils.goHome(this);
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
}