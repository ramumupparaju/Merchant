package com.incon.connect.utils;

import android.support.design.widget.TextInputEditText;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public class ValidationUtils {

    private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-zA-Z]).{8,})";

    public static boolean isPasswordValid(String password) {

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

    String preZipCode = "";
    public DisposableObserver<TextViewAfterTextChangeEvent> zipCodeFormatter(
            final TextInputEditText zipCodeEt) {
        Observable<TextViewAfterTextChangeEvent> zipCodeChangeObserver =
                RxTextView.afterTextChangeEvents(zipCodeEt);
        DisposableObserver<TextViewAfterTextChangeEvent> observer =
                new DisposableObserver<TextViewAfterTextChangeEvent>() {

                    @Override
                    public void onNext(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) {
                        String zipCode = textViewAfterTextChangeEvent.editable().toString()
                                .replace("-", "");
                        preZipCode = preZipCode.replace("-", "");
                        if (!preZipCode.equals(zipCode)) {
                            preZipCode = zipCode;
                            if (zipCode.length() >= 9) {
                                zipCodeEt.setText(zipCode.substring(0, 5) + "-"
                                        + zipCode.substring(5, 9));
                            } else {
                                zipCodeEt.setText(preZipCode);
                            }
                            zipCodeEt.setSelection(zipCodeEt.length());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                };
//        zipCodeEt.setTag(observer);
        zipCodeChangeObserver.subscribe(observer);
        return observer;
    }

    public DisposableObserver<TextViewAfterTextChangeEvent> wordCapitalizationObserver(
            final TextInputEditText firstLastNameEt) {
        Observable<TextViewAfterTextChangeEvent> fnChangeObserver =
                RxTextView.afterTextChangeEvents(firstLastNameEt);

        DisposableObserver<TextViewAfterTextChangeEvent> observer =
                new DisposableObserver<TextViewAfterTextChangeEvent>() {
                    @Override
                    public void onNext(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) {
                        if (firstLastNameEt.hasFocus()) {
                            String charSequence =
                                    textViewAfterTextChangeEvent.editable().toString();
                            if (charSequence.toString().length() > 0) {
                                String s1 = charSequence.toString().substring(0, 1);
                                if (!Validator.isNameValid(s1)) {
                                    firstLastNameEt.setText("");
                                } else if (!Character.isUpperCase(charSequence.toString()
                                        .codePointAt(0))) {
                                    firstLastNameEt.setText(s1.toUpperCase());
                                    firstLastNameEt.setSelection(1);
                                }
                            }
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                    }
                };

        fnChangeObserver.subscribe(observer);
//        firstLastNameEt.setTag(observer);
        return observer;
    }

    String formattedNo;
    public void phoneNumberFormatter(final TextInputEditText phoneNumberEt) {
        /*final PhoneNumberUtil instance = PhoneNumberUtil.createInstance(
                TueoApplication.getAppContext());

        Observable<TextViewAfterTextChangeEvent> phoneNumberChangeObserver =
                RxTextView.afterTextChangeEvents(phoneNumberEt);
        phoneNumberChangeObserver.subscribe(new Observer<TextViewAfterTextChangeEvent>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onNext(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) {

                String phoneNumber = textViewAfterTextChangeEvent.editable().toString()
                        .replace("+", "")
                        .replace("-", "")
                        .replace("(", "")
                        .replace(")", "")
                        .replace(" ", "");
                try {
                    Phonenumber.PhoneNumber number = instance.parse(phoneNumber,
                            Locale.getDefault().getCountry());
                    String formattedNumber = instance.format(number,
                            PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
                    Logger.e("onNext", "onNext=" + phoneNumber + "=" + formattedNo
                            + "=" + formattedNumber + "=" + Locale.getDefault().getCountry());

                    if (phoneNumber.length() > 0 && formattedNumber != null) {
                        formattedNumber = formattedNumber.replace(" ", "-");
                        if (!phoneNumber.equals(formattedNo)) {
                            formattedNo = phoneNumber;
                            phoneNumberEt.setText(formattedNumber);
                        } else if (formattedNumber.endsWith("-")) {
                            int index = formattedNumber.lastIndexOf("-");
                            formattedNo = phoneNumber;
                            phoneNumberEt.setText(formattedNumber.substring(0, index));
                        }
                        phoneNumberEt.setSelection(phoneNumberEt.length());
                    }
                } catch (NumberParseException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable e) {

            }
            @Override
            public void onComplete() {

            }
        });*/
    }

    public static boolean isFutureDate(Calendar dobDate) {
        // current date
        Calendar currentDate = Calendar.getInstance();
        if (dobDate.after(currentDate)) {
            return true;
        }
        return false;
    }

    public static int calculateAge(Calendar selectedDate) {
        // current date
        Calendar currentDate = Calendar.getInstance();
        //finding age difference
        int years = currentDate.get(Calendar.YEAR) - selectedDate.get(Calendar.YEAR);
        //checks whether date is preceded or not in the current year
        if (currentDate.get(Calendar.DAY_OF_YEAR) < selectedDate.get(Calendar.DAY_OF_YEAR)) {
            years--;
        }
        return years;
    }
}
