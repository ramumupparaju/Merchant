package com.incon.connect.callbacks;

/**
 * PageListener implemented by RegistrationActivity fragments, which helps to communicate
 * activity with fragment
 * fragments.
 */
public interface IViewPager {
    void onBack();
    void onNext();
    void pagePosition(int pos);
}
