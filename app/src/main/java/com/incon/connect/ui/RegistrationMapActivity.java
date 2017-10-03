package com.incon.connect.ui;

import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.incon.connect.AppUtils;
import com.incon.connect.R;
import com.incon.connect.apimodel.Location.AddressComponent;
import com.incon.connect.apimodel.Location.Location;
import com.incon.connect.apimodel.Location.LocationPostData;
import com.incon.connect.apimodel.Location.Result;
import com.incon.connect.callbacks.ILocationCallbacks;
import com.incon.connect.databinding.ActivityRegistrationMapBinding;
import com.incon.connect.utils.DeviceLocation;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by LENOVO on 9/30/2017.
 */

public class RegistrationMapActivity extends BaseActivity implements OnMapReadyCallback
        , RegistrationMapContract.View {
    private ActivityRegistrationMapBinding binding;
    public GoogleMap mGoogleMap;
    private RegistrationMapPresenter registrationMapPresenter;
    private Marker marker;
    private String preZipCode;
    private LatLng locationAddress;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_registration_map;
    }

    @Override
    protected void initializePresenter() {
        registrationMapPresenter = new RegistrationMapPresenter();
        registrationMapPresenter.setView(this);
        setBasePresenter(registrationMapPresenter);
    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.map_monitor, mapFragment).commit();
        mapFragment.getMapAsync(this);

        addZipcodeWatcher();
    }

    private void addZipcodeWatcher() {
        Observable<TextViewAfterTextChangeEvent> zipCodeChangeObserver =
                RxTextView.afterTextChangeEvents(binding.edittextPincode);
        DisposableObserver<TextViewAfterTextChangeEvent> observer =
                new DisposableObserver<TextViewAfterTextChangeEvent>() {

                    @Override
                    public void onNext(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) {
                        String zipCode = textViewAfterTextChangeEvent.editable().toString();

                        if (!TextUtils.isEmpty(preZipCode)) {
                            if (zipCode.contains(preZipCode) && zipCode.length() > 5) {
                                AppUtils.hideSoftKeyboard(RegistrationMapActivity.this,
                                        binding.edittextPincode);
                                registrationMapPresenter.doPostalCode(zipCode);
                            }
                        }
                        preZipCode = zipCode;
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                };
        zipCodeChangeObserver.subscribe(observer);
    }


    ILocationCallbacks iLocationCallbacks = new ILocationCallbacks() {
        @Override
        public void onLocationListener(LatLng latLng) {
            displayMarker(latLng, true, true);
        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (mGoogleMap == null) {
            mGoogleMap = googleMap;
            new DeviceLocation(this, iLocationCallbacks);

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
                    displayMarker(latLng, false, true);
                }
            };

    private void displayMarker(LatLng latLng, boolean zoomMap, boolean loadLocationDetails) {

        if (latLng == null) {
            showErrorMessage(getString(R.string
                    .location_permission_msg));
            return;
        }

        this.locationAddress = latLng;
        if (marker == null) {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng);
            marker = mGoogleMap.addMarker(options);
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,
                    GoogleMapConstants.DEFAULT_ZOOM_LEVEL));
        } else {
            marker.setPosition(latLng);
        }
        if (zoomMap) {
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,
                    GoogleMapConstants.DEFAULT_ZOOM_LEVEL));
        }
        if (loadLocationDetails) {
            loadLocationDetailsFromGeocoder();
        }
    }

    private void loadLocationDetailsFromGeocoder() {
        //fetching address using geo coder
        Geocoder geocoder =
                new Geocoder(RegistrationMapActivity.this);
        List<Address> list = null;

        LatLng latLng = locationAddress;
        try {
            list = geocoder.getFromLocation(latLng.latitude,
                    latLng.longitude, GoogleMapConstants.GEOCODER_MAX_ADDRESS_RESULTS);
        } catch (IOException e) {
            e.printStackTrace();

        }

        if (list != null && list.size() > 0) {
            Address address = list.get(0);
            binding.setAddress(address);
            marker.setTitle(address.getLocality());
        } else {
            showErrorMessage(getString(R.string.error_location));
        }
    }

    @Override
    public void moveMarkerToThisLocation(LocationPostData locationResponse) {
        if (locationResponse != null) {
            List<Result> locationData = locationResponse.getResults();
            if (locationData != null && locationData.size() > 0) {
                Result result = locationData.get(0);
                Location location = result.getGeometry().getLocation();
                displayMarker(new LatLng(location.getLat(), location.getLng()), true, false);
                List<AddressComponent> addressComponents = result.getAddressComponents();
            }
        }
    }
}
