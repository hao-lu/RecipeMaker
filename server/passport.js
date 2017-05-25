var LocalStrategy = require('passport-local').Strategy;
var User = require('./models/User');

module.exports = function(passport) {
  // Use for serializing user instance sessions 
  passport.serializeUser(function(user, done) {
    done(null, user.id);
  });

  passport.deserializeUser(function(id, done) {
    User.findById(id, function(err, user) {
      done(err, user);
    });
  });

  passport.use('local-login', new LocalStrategy({
  	usernameField: 'email',
    passwordField: 'password'
  },
  function(email, password, done) {
    User.findOne({'email': email}, function (err, user) {
      if (err) { return done(err); }
      if (!user) {
        return done(null, false, { message: 'Incorrect email.' });
      }
      if (!user.validPassword(password)) {
        return done(null, false, { message: 'Incorrect password.' });
      }
      return done(null, user);
    });
  }
  ));

  passport.use('local-signup', new LocalStrategy({
    usernameField: 'email',
    passwordField: 'password',
    passReqToCallback: true
  },
  // use passReqToCallback to get the req data to set the name
  function(req, email, password, done) {
    User.findOne({'email': email }, function (err, user) {
      if (err) 
        return done(err); // server exception serror
        // If user exist
        if (user) {
          // pass false wwhen credentials are invalid
          return done(null, false, { message: 'This user already exist.' });
        }
        else {
          var newUser = new User();
          newUser.name = req.body.name;
          newUser.email = email;
          newUser.password = newUser.generateHash(password);

          newUser.save(function(err) {
           if (err)
             throw err
           return done(null, newUser);
         });
        }
      });
  }
  ));
};

module.exports;
