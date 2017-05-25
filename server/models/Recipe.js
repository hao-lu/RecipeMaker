var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var recipeSchema = new Schema ({
	name: String,
	recipeOwner: String,
	imageUrl: String,
	rating: Number,
	ingredients: [String],
	steps: [String]
});

// {"_id":"","name":"","recipeOwner":"","imageUrl":"","rating":5,"ingredients":[],"steps":[]}

var Recipe = mongoose.model('Recipe', recipeSchema);

module.exports = Recipe;