var express = require('express');
// Creates modular, mountable route handlers 
var router = express.Router();
var passport = require('passport');

// var User = require('../models/User');

// GET /user
router.get('/', function(req, res, next) {
	res.send('Logged in ');
});

// Logged in 
// router.post('/', function(req, res, next) {
// 	passport.authenticate('local-login', function(err, user, info) {
// 		if (err)
// 			return next(err);
// 		if (!user)
// 			return res.status(400).json({SERVER_RESPONSE: 0, 
// 				SERVER_MESSAGE: "Wrong Credentials"});
// 		req.logIn(user, function(err) {
// 			if (err)
// 				return next(err);
// 			if (!err)
// 				return res.json({SERVER_RESPONSE: 1, 
// 				SERVER_MESSAGE: "Logged in"});
// 		});
// 	})(req, res, next);
// });

module.exports = router;