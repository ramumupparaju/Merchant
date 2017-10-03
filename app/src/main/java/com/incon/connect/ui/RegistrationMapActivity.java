package com.incon.connect.ui;

import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

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
import com.incon.connect.databinding.ActivityRegistrationMapBinding;
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
        , RegistrationMapContract.View {
    private ActivityRegistrationMapBinding binding;
    public GoogleMap mGoogleMap;
    private Marker marker;
    private RegistrationMapPresenter loginPresenter;
    private List<Result> locationData;
    private List<AddressComponent> completeAddress;
    private Double mLatitude, mLongitude;
    private DeviceLocation deviceLocation;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_registration_map;
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
//        loginPresenter.doPostalCode("505211");
    }


    ILocationCallbacks iLocationCallbacks = new ILocationCallbacks() {
        @Override
        public void onLocationListener(LatLng latLng) {
            displayMarker(latLng);
        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (mGoogleMap == null) {
            mGoogleMap = googleMap;
            deviceLocation = new DeviceLocation(this, iLocationCallbacks);

            UiSettings googlemapSettings = mGoogleMap.getUiSettings();
            googlemapSettings.setZoomControlsEnabled(true);
            googlemapSettings.setMyLocationButtonEnabled(true);
            googlemapSettings.setRotateGesturesEnabled(true);
            googlemapSettings.setMapToolbarEnabled(true);


            mGoogleMap.setOnMapLongClickListener(onMapLongClickListener);
        }
    }

    private GoogleMap.OnMapLongClickListener onMapLongClickListener = new
            GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    displayMarker(latLng);
                }
            };

    private void displayMarker(LatLng latLng) {

        if (latLng == null) {
            showErrorMessage(getString(R.string
                    .location_permission_msg));
            return;
        }

        if (marker == null) {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng);
            marker = mGoogleMap.addMarker(options);
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,
                    GoogleMapConstants.DEFAULT_ZOOM_LEVEL));
        } else {
            marker.setPosition(latLng);
        }

        Geocoder geocoder =
                new Geocoder(RegistrationMapActivity.this);
        List<Address> list;
        try {
            list = geocoder.getFromLocation(latLng.latitude,
                    latLng.longitude, GoogleMapConstants.GEOCODER_MAX_ADDRESS_RESULTS);
        } catch (IOException e) {
            return;
        }

        //checks whether address is found or not from geocoder
        if (list != null && list.size() > 0) {
            Address address = list.get(0);
            AddressDetails loginUserData = new AddressDetails(address.getSubAdminArea()
                    , address.getPostalCode()
                    , address.getAdminArea());
            binding.setAddress(address);
            marker.setTitle(address.getLocality());
        }
    }

    @Override
    public void navigateToHomePage(LocationPostData loginResponse) {
        if (loginResponse != null) {
            locationData = loginResponse.getResults();
            for (int i = 0; i < locationData.size(); i++) {
                completeAddress = locationData.get(i).getAddressComponents();
                mLatitude = locationData.get(i).getGeometry().getLocation().getLat();
                mLongitude = locationData.get(i).getGeometry().getLocation().getLng();
                LatLng lt = new LatLng(mLatitude, mLongitude);
                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(lt, 10);
                mGoogleMap.moveCamera(yourLocation);
            }
        }

    }


}
