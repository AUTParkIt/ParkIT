package com.aut.parkit;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.lukekorth.deviceautomator.AutomatorAction.click;
import static com.lukekorth.deviceautomator.AutomatorAction.setText;
import static com.lukekorth.deviceautomator.DeviceAutomator.onDevice;
import static com.lukekorth.deviceautomator.UiObjectMatcher.withText;

@RunWith(AndroidJUnit4ClassRunner.class)

public class AccountCreationInstrimentalTest {

    FirebaseAuth mAuth;

    @Before
    public void setup() {
        onDevice().onHomeScreen().launchApp("com.aut.parkit");
        onDevice(withText("Sign Up")).waitForExists().waitForEnabled().perform(click());
        mAuth = FirebaseAuth.getInstance();
    }

    @Test(timeout = 60000)
    public void noText(){
        Assert.assertNull(mAuth.getUid());
        enterText(null, null, null, null, null, null);
        signUp();
        Assert.assertNull(mAuth.getUid());
    }

    @Test(timeout = 60000)
    public void fNameText(){
        Assert.assertNull(mAuth.getUid());
        enterText("Unit", null, null, null, null, null);
        signUp();
        Assert.assertNull(mAuth.getUid());
    }

    @Test(timeout = 60000)
    public void lNameText(){
        Assert.assertNull(mAuth.getUid());
        enterText(null, "Test", null, null, null, null);
        signUp();
        Assert.assertNull(mAuth.getUid());
    }

    @Test(timeout = 60000)
    public void nameText(){
        Assert.assertNull(mAuth.getUid());
        enterText("Unit", "Test", null, null, null, null);
        signUp();
        Assert.assertNull(mAuth.getUid());
    }

    @Test(timeout = 60000)
    public void nameNotEmailText(){
        Assert.assertNull(mAuth.getUid());
        enterText("Unit", "Test", "unitTest.com", "ABC123", "123456", "123456");
        signUp();
        Assert.assertNull(mAuth.getUid());
    }

    @Test(timeout = 60000)
    public void nameEmailText(){
        Assert.assertNull(mAuth.getUid());
        enterText("Unit", "Test", "unitTest@Test.com", null, null, null);
        signUp();
        Assert.assertNull(mAuth.getUid());
    }

    @Test(timeout = 60000)
    public void nameEmailLPlateText(){
        Assert.assertNull(mAuth.getUid());
        enterText("Unit", "Test", "unitTest@Test.com", "ABC123", null, null);
        signUp();
        Assert.assertNull(mAuth.getUid());
    }

    @Test(timeout = 60000)
    public void nameEmailLPlateNotPasswordText(){
        Assert.assertNull(mAuth.getUid());
        enterText("Unit", "Test", "unitTest@Test.com", "ABC123", "12345", null);
        signUp();
        Assert.assertNull(mAuth.getUid());
    }

    @Test(timeout = 60000)
    public void nameEmailLPlatePasswordText(){
        Assert.assertNull(mAuth.getUid());
        enterText("Unit", "Test", "unitTest@Test.com", "ABC123", "123456", null);
        signUp();
        Assert.assertNull(mAuth.getUid());
    }

    @Test(timeout = 60000)
    public void nameEmailLPlatePasswordNotConText(){
        Assert.assertNull(mAuth.getUid());
        enterText("Unit", "Test", "unitTest@Test.com", "ABC123", "123456", "213456");
        signUp();
        Assert.assertNull(mAuth.getUid());
    }

    @Test(timeout = 60000)
    public void nameEmailLPlatePasswordConText(){
        Assert.assertNull(mAuth.getUid());
        enterText("Unit", "Test", "unitTest@Test.com", "ABC123", "123456", "123456");
        signUp();
        Assert.assertNotNull(mAuth.getUid());
    }

    public void signUp(){
        onDevice(withText("Sign Up")).waitForExists().waitForEnabled().perform(click());
    }

    public void enterText(String fName, String lName, String email, String lPlate, String password, String confirm){
        onDevice(withText("FIRST NAME")).waitForExists().waitForEnabled().perform(setText(fName));
        onDevice(withText("LAST NAME")).perform(setText(lName));
        onDevice(withText("EMAIL")).perform(setText(email));
        onDevice(withText("VEHICLE REGISTRATION")).perform(setText(lPlate));
        onDevice(withText("PASSWORD")).perform(setText(password));
        onDevice(withText("CONFIRM PASSWORD")).perform(setText(confirm));
        onDevice(withText("I agree with the Terms & Conditions")).perform(click());
        onDevice(withText("X")).waitForExists().waitForEnabled().perform(click());
    }

}
