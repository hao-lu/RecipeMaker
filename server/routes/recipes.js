var express = require('express');
var router = express.Router();

var Recipe = require('../models/Recipe');

// GET /recipes
router.get('/', function(req, res, next) {
	Recipe.find({'rating': {$gt:4}}, function(err, found) {
		if (err) return next(err);
		res.json({"results":found});
	});
});

// GET /recipe/Search?term=name
router.get('/Search', function(req, res, next) {
	console.log(req.query.term);
	Recipe.find({'name':  req.query.term}, function(err, found) {
		if (err) return next(err);
		res.json({"results":found});
	});	
});

// The methods are identical when an object or array is passed, but res.json() 
// will also convert non-objects, such as null and undefined, which are not valid JSON.

module.exports = router;