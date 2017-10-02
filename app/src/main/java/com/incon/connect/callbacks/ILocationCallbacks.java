package com.incon.connect.callbacks;

import com.google.android.gms.maps.model.LatLng;

public interface ILocationCallbacks {
    void onLocationListener(LatLng latLng);
}