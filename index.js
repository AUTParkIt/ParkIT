const functions = require('firebase-functions');
var braintree = require("braintree");

//Create Braintree gateway
var gateway = braintree.connect({
    environment: braintree.Environment.Sandbox,
    merchantId: "nqhd7msq7bkx89wm",
    publicKey: "vmp7mmx4b4xp6cyw",
    privateKey: "3d66224d4559111d2e5303fa5497c2d9"
    });

//Get client token
exports.getClientToken = functions.https.onRequest((req,res) => {
    gateway.clientToken.generate({
      customerId: req.body.customerId
    }, function (err, response) {
      res.send(response.clientToken);
    });
  });

//Create transaction
exports.createTransaction = functions.https.onRequest((req, res) => {
  gateway.transaction.sale({
    amount: req.body.amount,
    paymentMethodNonce: req.body.payment_method_nonce,
    options: {
      submitForSettlement: true
    }}, function (err, result) {
      res.send(result);
  });
});
