package pack.location.chat.com.chatmodule.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

public class CalculatePosition implements LocationListener
{

	private LocationManager locationManager;
	private String provider;

	private double lat = 0.00;
	private double lng = 0.00;

	private Context mContext;

	public String latStr;
	public String lngStr;

	public CalculatePosition(Context mContext){

		this.mContext = mContext;

		if ( Build.VERSION.SDK_INT >= 23 &&
				ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
				ContextCompat.checkSelfPermission( mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			return  ;
		}

		locationManager = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, true);
		Location location = locationManager.getLastKnownLocation(provider);

		// Initialize the location fields
		if (location != null) {
			locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			onLocationChanged(location);
		}
		locationManager.requestLocationUpdates(provider, 400, 1, this);
	}


	@Override
	public void onLocationChanged(Location loc)
	{
		if(loc != null && latStr== null && lngStr == null) {
			lat = loc.getLatitude();
			lng = loc.getLongitude();

			latStr = lat+"";
			latStr = latStr.substring(0,5);

			lngStr = lng+"";
			lngStr = lngStr.substring(0,5);
		}


	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onProviderDisabled(String provider){

	}

}