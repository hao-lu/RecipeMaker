var mongoose = require('mongoose');
var bcrypt = require('bcrypt-nodejs');
var Schema = mongoose.Schema;

// Create schema
var userSchema = new Schema({
	name: { type: String, required: true},
	email: { type: String, required: true, unique: true},
	password: {type: String, required: true}	

});	

userSchema.methods.generateHash = function(password) {

    return bcrypt.hashSync(password, bcrypt.genSaltSync(8), null);
};

userSchema.methods.validPassword = function(password) {
    return bcrypt.compareSync(password, this.password);
};

// Create model with schema
var User = mongoose.model('User', userSchema);

module.exports = User;
