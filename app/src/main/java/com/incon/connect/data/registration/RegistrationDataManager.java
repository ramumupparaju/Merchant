package com.incon.connect.data.registration;


import com.incon.connect.apimodel.components.registration.RegistrationResponse;

public interface RegistrationDataManager {

    void saveLoginDataRoPrefs(RegistrationResponse registrationResponse);
}
