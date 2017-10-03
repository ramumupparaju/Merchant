package com.incon.connect.ui;

import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.incon.connect.R;
import com.incon.connect.callbacks.ILocationCallbacks;
import com.incon.connect.databinding.DialogRegistrationGooglemapBinding;
import com.incon.connect.dto.Location.AddressComponent;
import com.incon.connect.dto.Location.LocationPostData;
import com.incon.connect.dto.Location.Result;
import com.incon.connect.dto.registration.AddressDetails;
import com.incon.connect.utils.DeviceLocation;

import java.io.IOException;
import java.util.List;

/**
 * Created by LENOVO on 9/30/2017.
 */

public class RegistrationMapActivity extends BaseActivity implements OnMapReadyCallback
        , LocationListener , RegistrationMapContract.View {
    private DialogRegistrationGooglemapBinding binding;
    public GoogleMap mGoogleMap;
    private Marker marker;
    private RegistrationMapPresenter loginPresenter;
    private List<Result> locationData;
    private List<AddressComponent> completeAddress;
    private Double mLatitude , mLongitude;


    @Override
    protected int getLayoutId() {
        return R.layout.dialog_registration_googlemap;
    }

    @Override
    protected void initializePresenter() {
        loginPresenter = new RegistrationMapPresenter();
        loginPresenter.setView(this);
        setBasePresenter(loginPresenter);
    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.map_monitor, mapFragment).commit();
        mapFragment.getMapAsync(this);
        loginPresenter.doPostalCode("505211");
    }


    ILocationCallbacks iLocationCallbacks = new ILocationCallbacks() {
        @Override
        public void onLocationListener(LatLng latLng) {

            MarkerOptions options = new MarkerOptions()
                    .position(latLng);

            marker = mGoogleMap.addMarker(options);


           /* UiUtils.dismissProgress();

            Context context = edittextTime.getContext();
            if (latLng == null) {
                UiUtils.showToast(context, context.getString(R.string
                        .location_permission_msg));
            }
            String timeString = DateAndTimePickerUtils.convertDate(
            AppConstants.DateFormatterConstants
                    .DD_MM_YYYY_HH_MM, System.currentTimeMillis());
            edittextTime.setText(context.getString(R.string.time_location, timeString, String
                            .valueOf(latLng
                                    .longitude),
                    String.valueOf(latLng.latitude)));

            edittextTime.setVisibility(View.VISIBLE);
            timeNameButton.setVisibility(View.GONE);
        */}
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (mGoogleMap == null) {
            mGoogleMap = googleMap;
            Object localObject = mGoogleMap.getUiSettings();
            ((UiSettings) localObject).setZoomControlsEnabled(true);
            ((UiSettings) localObject).setMyLocationButtonEnabled(true);
            ((UiSettings) localObject).setRotateGesturesEnabled(true);
            ((UiSettings) localObject).setMapToolbarEnabled(true);


            DeviceLocation deviceLocation = new DeviceLocation(this, iLocationCallbacks);

            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String bestProvider = locationManager.getBestProvider(criteria, true);
            Location location = locationManager.getLastKnownLocation(bestProvider);
            if (location != null) {
                onLocationChanged(location);
            }
            mGoogleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    Geocoder geocoder =
                            new Geocoder(RegistrationMapActivity.this);
                    List<Address> list;
                    try {
                        list = geocoder.getFromLocation(latLng.latitude,
                                latLng.longitude, 1);
                    } catch (IOException e) {
                        return;
                    }
                    Address address = list.get(0);
                    if (marker != null) {
                        marker.remove();
                    }
                    AddressDetails loginUserData = new AddressDetails(address.getSubAdminArea()
                            , address.getPostalCode()
                            , address.getAdminArea());
                    binding.setAddressDetails(loginUserData);
                    MarkerOptions options = new MarkerOptions()
                            .title(address.getLocality())
                            .position(new LatLng(latLng.latitude,
                                    latLng.longitude));

                    marker = mGoogleMap.addMarker(options);
                }
            });
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {

            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LatLng coordinate = new LatLng(latitude, longitude);
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 19);
            mGoogleMap.animateCamera(yourLocation);

        }
    }


    @Override
    public void navigateToHomePage(LocationPostData loginResponse) {
        if (loginResponse != null) {
            locationData = loginResponse.getResults();
            for (int i = 0; i < locationData.size(); i++) {
                completeAddress =  locationData.get(i).getAddressComponents();
                mLatitude =  locationData.get(i).getGeometry().getLocation().getLat();
                mLongitude =  locationData.get(i).getGeometry().getLocation().getLng();
                LatLng lt = new LatLng(mLatitude , mLongitude);
                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(lt , 10);
                mGoogleMap.moveCamera(yourLocation);
            }
        }

    }


}
