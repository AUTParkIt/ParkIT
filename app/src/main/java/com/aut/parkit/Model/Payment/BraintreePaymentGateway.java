package com.aut.parkit.Model.Payment;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;

public class BraintreePaymentGateway {

    private BraintreeGateway gateway;

    public BraintreePaymentGateway() {
        gateway = new BraintreeGateway(
                Environment.SANDBOX,
                "nqhd7msq7bkx89wm",
                "vmp7mmx4b4xp6cyw",
                "3d66224d4559111d2e5303fa5497c2d9"
        );
    }

    public BraintreeGateway getGateway() {
        return gateway;
    }
}
