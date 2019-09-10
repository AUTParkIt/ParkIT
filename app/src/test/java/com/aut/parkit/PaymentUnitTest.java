package com.aut.parkit;

import com.aut.parkit.Model.Payment.*;
import com.braintreegateway.*;
import com.loopj.android.http.SyncHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class PaymentUnitTest {

    private BraintreeGateway gateway;
    private String expectedResponse, actualResponse, cFirstName, cLastName, cUserID;

    @Rule
    public MockWebServer mockWebServer = new MockWebServer();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        expectedResponse = "";
        actualResponse = "";
        cFirstName = "Test";
        cLastName = "User";
        cUserID = "TestUserID";
    }

    @After
    public void tearDownAfterClass(){
        if(gateway != null){
            gateway.customer().delete(cUserID);
            gateway = null;
        }
    }

    @Test
    public void customerSuccessfulCreation() {
        gateway = new BraintreePaymentGateway().getGateway();
        boolean isSuccessful = new BraintreeCustomer()
                .createBraintreeCustomer(cFirstName, cLastName, cUserID);

        assertTrue(isSuccessful == true);
    }

    @Test
    public void clientTokenRetrieved() {
        BraintreeClientToken token = new BraintreeClientToken();
        token.setUrl(mockWebServer.url("/").toString());
        token.setClient(new SyncHttpClient());
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("test_client_token"));
        token.generateClientTokenfromServer();

        expectedResponse = "test_client_token";
        actualResponse = token.getClientToken();
        assertEquals(expectedResponse,actualResponse);

    }

    @Test
    public void validPaymentSuccess() throws InterruptedException {
        BraintreeTransaction transaction = new BraintreeTransaction("nonce", "1:00", new BraintreeInterface() {
            @Override
            public void onSuccess(String responseMain) {
                actualResponse = responseMain;
            }

            @Override
            public void onFailure(String responseMain, String responseDesc) {
                actualResponse = responseMain;
            }
        });

        transaction.setUrl(mockWebServer.url("/").toString());
        transaction.setClient(new SyncHttpClient());
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("success"));
        transaction.createTransaction();

        expectedResponse = "Payment Success!";
        assertEquals(expectedResponse,actualResponse);
    }

    @Test
    public void invalidPaymentFailure() throws InterruptedException {
        BraintreeTransaction transaction = new BraintreeTransaction("nonce", "1.00", new BraintreeInterface() {
            @Override
            public void onSuccess(String responseMain) {
                actualResponse = responseMain;
            }

            @Override
            public void onFailure(String responseMain, String responseDesc) {
                actualResponse = responseMain;
            }
        });

        transaction.setUrl(mockWebServer.url("/").toString());
        transaction.setClient(new SyncHttpClient());
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("\"success\":false"));
        transaction.createTransaction();

        expectedResponse = "Payment Failed!";
        assertEquals(expectedResponse,actualResponse);
    }
}
