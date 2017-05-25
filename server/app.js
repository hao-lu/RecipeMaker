require('dotenv').load()

var express = require('express');
var mongoose = require('mongoose');
var passport = require('passport');
var session = require('express-session');
var bodyParser = require('body-parser');
var morgan = require('morgan');
var cookieParser = require('cookie-parser');

var routes = require('./routes/index');
var users = require('./routes/users');
var recipes = require('./routes/recipes');

var port = process.env.PORT || 3000;

var app = express();
// set up our express application
app.use(morgan('dev')); // log every request to the console
app.use(cookieParser()); // read cookies (needed for auth)
// app.use(bodyParser()); // get information from html forms
// Configure body-parser
app.use(bodyParser.urlencoded({ extended:true }));
app.use(bodyParser.json());

// Use native Node promises (mpromise is deprecated)
mongoose.Promise = global.Promise;

// mongoose.connect('mongodb://localhost/recipemakerdb', function(err) {
// 	if (err) throw err;
// });

mongoose.connect(process.env.MONGOLAB_URI, function(err) {
	if (err) throw err;
});

require('./passport')(passport); // pass passport for configuration

// Session for persistent connection 
app.use(session({ secret: 'kiba',
	resave: false,
	saveUninitialized: true}));
// Initialize passport 
app.use(passport.initialize());
// For persistent login sessions
app.use(passport.session());

// Routes - API endpoints
app.use('/', routes);
app.use('/users', users);
app.use('/recipes', recipes);

passport = require('./routes/index');
passport = require('./routes/users');
passport = require('./routes/recipes');

app.listen(port);

console.log('Server running on port ' + port);


