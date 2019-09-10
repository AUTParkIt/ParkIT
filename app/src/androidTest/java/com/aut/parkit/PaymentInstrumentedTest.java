package com.aut.parkit;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import com.aut.parkit.Model.Payment.BraintreeTransaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static com.lukekorth.deviceautomator.AutomatorAction.click;
import static com.lukekorth.deviceautomator.AutomatorAction.setText;
import static com.lukekorth.deviceautomator.AutomatorAssertion.text;
import static com.lukekorth.deviceautomator.DeviceAutomator.onDevice;
import static com.lukekorth.deviceautomator.UiObjectMatcher.*;
import static org.hamcrest.core.StringEndsWith.endsWith;

@RunWith(AndroidJUnit4ClassRunner.class)
public class PaymentInstrumentedTest {

    @Before
    public void setup() {
        onDevice().onHomeScreen().launchApp("com.aut.parkit");
    }

    @Test(timeout = 60000)
    public void successfulCardPayment() {
        BraintreeTransaction.testAmount = "10.00";
        onDevice(withText("Pay Now")).waitForExists().waitForEnabled().perform(click());
        onDevice(withText("Credit or Debit Card")).perform(click());
        onDevice(withText("Card Number")).perform(setText("4111111111111111"));
        onDevice(withText("12")).perform(click());
        onDevice(withText("2019")).perform(click());
        onDevice().pressBack();
        onDevice(withTextContaining("Add Card")).perform(click());
        onDevice(withTextStartingWith("Payment")).check(text(endsWith("Success!")));
    }

    @Test(timeout = 60000)
    public void failedCardPayment() {
        BraintreeTransaction.testAmount = "2000.00";
        onDevice(withText("Pay Now")).waitForExists().waitForEnabled().perform(click());
        onDevice(withText("Credit or Debit Card")).perform(click());
        onDevice(withText("Card Number")).perform(setText("4000111111111115"));
        onDevice(withText("12")).perform(click());
        onDevice(withText("2019")).perform(click());
        onDevice().pressBack();
        onDevice(withTextContaining("Add Card")).perform(click());
        onDevice(withTextStartingWith("Payment")).check(text(endsWith("Failed!")));
    }

    @Test(timeout = 60000)
    public void successfulPaypalPayment() {
        BraintreeTransaction.testAmount = "10.00";
        onDevice(withText("Pay Now")).waitForExists().waitForEnabled().perform(click());
        onDevice(withText("PayPal")).perform(click());
        //If testing with customer with no existing PayPal payment method stored, uncomment following line:
        //onDevice(withText("Proceed with Sandbox Purchase")).waitForExists().perform(click());
        onDevice(withTextStartingWith("Payment")).check(text(endsWith("Success!")));
    }
}
