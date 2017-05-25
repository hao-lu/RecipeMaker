var passport = require('passport');
var express = require('express');
var router = express.Router();

var User = require('../models/User');

// GET /
router.get('/', function(req, res) {
	res.send('HOME');
});

router.post('/login', function(req, res, next) {
	passport.authenticate('local-login', function(err, user, info) {
		console.log("body parsing", req.body);
		if (err)
			return next(err);
		if (!user)
			return res.status(400).json({SERVER_RESPONSE: 0, 
				SERVER_MESSAGE: "Wrong Credentials"});
		// Need to invoke when using custom callback (establishes a login session)
		req.logIn(user, function(err) {
			if (err)
				return next(err);
			if (!err)
				return res.json({SERVER_RESPONSE: 1, 
				SERVER_MESSAGE: "Logged in"});
		});
	})(req, res, next); // Needed for custom callback
});

router.post('/signup', function(req, res, next) {
	passport.authenticate('local-signup', function(err, user, info) {
		console.log("body parsing", req.body);
		if (err)
			return next(err);
		if (!user)
			return res.status(400).json({SERVER_RESPONSE: 0, 
				SERVER_MESSAGE: "This user already exist"});
		else {
			return res.json({SERVER_RESPONSE: 1, 
				SERVER_MESSAGE: "Successfully registered"});
		}
	})(req, res, next); // Needed for custom callback
});


module.exports = router;
