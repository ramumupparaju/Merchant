package com.incon.connect;

import java.util.Locale;

public interface AppConstants {

    String TERMS_CONDITIONS_URL = "https://www.google.co.in"; //TODO have to change

    String BUILD_FLAVOR = "moonz_dev";
    int VALIDATION_SUCCESS = 0;
    int VALIDATION_FAILURE = -1;
    int VALIDATION_ZIPCODE_LENGTH = 5;
    String DELIMITOR = "-";
    int DEAULT_VALUE = Integer.MAX_VALUE;

    interface SyncConstants {
        int STATE_INCOMPLETE = 0;
        int STATE_COMPLETED = 1;
    }
    interface LoginValidation {
        int EMAIL_REQ = 1;
        int EMAIL_NOTVALID = 2;
        int PASSWORD_REQ = 3;
    }

    interface RegistrationValidation {
        int FIRSTNAME_REQ = 1;
        int LASTNAME_REQ = 2;
        int PHONE_REQ = 3;
        int PHONE_TYPE_REQ = 4;
        int EMAIL_REQ = 5;
        int EMAIL_NOTVALID = 6;
        int PASSWORD_REQ = 7;
        int TIMEZONE_REQ = 8;
        int ZIPCODE_REQ = 9;
        int PHONE_MIN_DIGITS = 10;
        int PASSWORD_PATTERN_REQ = 11;
        int ZIPCODE_INVALID = 12;
        int DOB_REQ = 13;
        int DOB_FUTURE_DATE = 14;
        int DOB_PERSON_LIMIT = 15;
        int DOB_CHILD_LIMIT = 16;
        int GENDER_REQ = 17;
        int RE_ENTER_PASSWORD_REQ = 18;
        int RE_ENTER_PASSWORD_DOES_NOT_MATCH = 19;
    }


    interface RoleConstants {
        int ROLE_INDIVIDUAL = 0;
        int ROLE_PARENT = 1;
    }
    interface PasswordValidation {
        int NEWPWD_REQ = 1;
        int NEWPWD_PATTERN_REQ = 11;
        int CONFIRMPWD_REQ = 2;
        int PWD_NOTMATCH = 3;
    }

    interface ChangeEmailValidation {
        int CURRENT_PWD_REQ = 1;
        int CURRENT_PWD_PATTERN_REQ = 2;
        int EMAIL_REQ = 3;
        int EMAIL_NOTVALID = 4;
    }

    interface ChangeCurrentPasswordValidation {
        int CURRENT_PWD_REQ = 1;
        int NEW_PWD_REQ = 2;
        int NEW_PWD_PATTERN_REQ = 3;
        int CONFIRM_PWD_REQ = 4;
        int CONFIRM_PWD_PATTERN_REQ = 5;
        int NEW_CONFIRM_PWD_NOT_MATCHED = 6;
    }

    interface IntentConstants {
        String EMAIL_ADDRESS = "emailAddress";
        String ALERT_ID_KEY = "alertId";
        String RESPOND_TO_ALERT_RESPONSE = "respondToAlertResponse";
        String WRITE_REASON_RESULT = "writeReasonResult";
        String ALERT_MESSAGE_RESPONSE = "alertMessageResponse";
        String APPOINTMENT_ID = "appointmentId";
        String APPOINTMENT_CREATED_DATE = "appointmentCreatedDate";
        String APPOINTMENT_LAUNCHED_SCREEN = "appointmentLaunchedScreen";
        String APPOINTMENT_CANCELLED = "appointmentCancelled";
        String TOPIC_NAME = "topicName";
        String TOPIC_EXTERNAL_ID = "topicExternalId";
        String TOPIC_REVISION = "topicRevision";
        String TOPIC_LANGUAGE = "topicLanguage";
        String TOPIC_LIBRARY_ID = "topicLibraryId";
        String TOPIC_READ = "topicRead";
        String SURVEYS_OF_PATIENT_RESPONSE = "surveysOfPatientResponse";
        String TAKE_NEW_SURVEY_RESPONSE = "newSurveyOfPatientResponse";
        String CALCULATE_SCORE_RESPONSE = "calculateScoreResponse";
        String PAST_SCORE_RESPONSE = "pastScoreResponse";
        String POST_SURVEY_REQUEST_BODY = "postSurveyRequestBody";
        String TAKE_NEW_SURVEY_LAUNCHED_FROM = "newSurveyLaunchedFrom";
        String AAP_IMAGE_URI = "aapImageUri";
        String PUSH_MESSAGE = "pushMessage";
        String PUSH_TYPE = "pushType";
        String PUSH_ALERT_ID = "pushAlertId";
    }

    interface BundleConstants {
        String SELECTED_DATE = "selectedDate";
        String SYMPTOM_DATA = "symptomDate";
        String ALERT_CAMERA_IMAGE_PATH = "alertCameraImagePath";
    }

    interface CachePrefs {
        String LEARNING_FIRST_TIME_HIT_BACK = "learningFirstTimeHitBack";
        String IS_ACCESS_CODE_VERIFIED = "isAccessCodeVerified";
        String IS_AAP_OFFLINE_IMAGE = "isAapOfflineImage";
        String EXTRACT_ZIP = "extractZip";
    }

    interface LoginPrefs {
        String EMAIL_ID = "email_id";
        String PUSH_TOKEN_STATUS = "pushTokenStatus";
        String ACCESS_TOKEN = "accesstoken";
        String S_THREE_SECRET_KEY = "s3SecretKey";
        String S_THREE_ACCESS_KEY = "s3AccessKey";
        String PATIENT_ID = "patientId";
        String PATIENT_DOB = "patientDob";
        String ACCOUNT_ID = "id";
        String FIRST_NAME = "firstName";
        String LAST_NAME = "lastName";
        String PHONE_NUMBER = "phoneNumber";
        String ADDRESS = "address";
        String CITY = "city";
        String STATE = "state";
        String POSTAL_CODE = "postalCode";
        String TIMEZONE_ID = "timezone_id";
        String LOGGED_IN = "isLoggedIn";
        String IS_REGISTERED = "isRegistered";
    }

    interface ApiRequestKeyConstants {
        String BODY_FIRST_NAME = "firstName";
        String BODY_EMAIL = "email";
        String BODY_PASSWORD = "password";
        String BODY_OTP = "otp";
        String BODY_MODULE_ID = "moduleId";
        String HEADER_AUTHORIZATION = "Authorization";
        String HEADER_API_KEY = "api_key";
        String QUERY_EMAIL = "email";
        String BODY_CURRENT_PASSWORD = "currentPassword";
        String BODY_NEW_PASSWORD = "newPassword";
        String BODY_NEW_EMAIL = "newEmail";
    }

    interface RequestCodes {
        int TAKE_PHOTO = 100;
        int PICK_FROM_GALLERY = 101;
        int ALERT_WRITE_REASON = 102;
        int TOPIC_READ = 103;
        int FORGOT_PASSWORD = 104;
        int FRAGMENT_LOGSYMPTOMS = 105;
        int LEARNING_TOPIC = 107;
        int ALERT_ACTIVITIES = 108;
        int REGISTRATION = 109;
        int CHANGE_EMAIL = 110;
        int TERMS_AND_CONDITIONS = 111;
    }

    interface TimeConstants {
        String AM_LOWER_CASE = "am";
        String AM_UPPER_CASE = "AM";
        String PM_LOWER_CASE = "pm";
        String PM_UPPER_CASE = "PM";
    }

    interface DateFormatterConstants {
        String FROM_API_MILLIS = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        String FROM_API = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        String LOCAL_DATE_DD_MM_YYYY_HH_MM_SS =
                "dd-MM-yyyy HH:mm:ss"; //14-08-1987 18:30:00
        String MMMM_YYYY = "MMMM - yyyy"; //June - 2017
        String DD_E_MMMM_YYYY = "dd-E-MMMM-yyyy"; //14-Thursday-Jun-1987
        String MM_DD_YYYY = "MM/dd/yyyy"; //14-Thursday-Jun-1987
        String DD_MM_YYYY = "dd-MM-yyyy"; //14-08-1987
        String MMMM_DD = "MMMM-dd"; //14-08-1987
        String DD_MMMM = "dd MMMM"; //14 July
        String DD_SLASH_MM_SLASH_YYYY = "dd/MM/yyyy"; //14/07/2017
        //Mon, 10 Jul 2017 10:08:20 GM
        String DDMMMM_H_MMA = "ddMMMM, h:mma"; //14July, 2:30PM
        String YYYY_MM_DD = "yyyy-MM-dd"; //2017-01-15
        String MMMM_SPACE_DD = "MMMM dd"; // March 24 at 11:26am
        String HH_MM_A = "hh:mma"; // March 24 at 11:26am
        String YYYY_MMMM_DD = "yyyy-MMMM-dd"; //2017-01-15
        String YYYY_MMM_DD = "yyyy-MMM-dd"; //2017-Jan-15
        String MMM_SPACE_DD = "MMM dd"; //Mar 28
        String MMMM_DD_YYYY = "MMMM dd, yyyy"; //Oct-21-2017
        String DD_NOSPACE_MMMM = "ddMMMM"; //28March

        String TIME_HH_MM = "HH:mm";

        String UTC = "UTC";
        Locale DATE_FORMAT_LOCALE = Locale.getDefault();
    }


    interface ErrorCodes {
        int UNKNOWN = 0;
        int NETWORK_ERROR = 1;
        int TIMEOUT = 2;
        int NO_NETWORK = 3;
    }

    interface MenuConstants {
        int PROFILE = 0;
        int MONITORED_PERSON_INFO = 1;
        int PHYSICIAN_INFO = 2;
        int SENSOR = 3;
        int FAQ = 4;
        int SUPPORT = 5;
        int CHANGE_PWD = 6;
        int LOGOUT = 7;
    }

    interface ActivityResult {
        String IS_REGISTRATION_SUCCESS = "is_registration_success";
    }
}
