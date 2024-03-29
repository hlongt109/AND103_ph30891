const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const Fruits = new Schema({
    name: {type: String},
    price: {type: Number},
    quantity: {type: Number},
    weight: {type: Number},
    image: {type: String}
})
module.exports = mongoose.model("fruit",Fruits);