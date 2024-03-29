const mongoose = require('mongoose');
mongoose.set('strictQuery', true);

const url_mongodb = "mongodb://127.0.0.1:27017/AND103";

const connect = async () => {
    try {
        await mongoose.connect(url_mongodb);
        console.log("Connect success");
    } catch (error) {
        console.log("error :" + error);
        console.log('Connect failed');
    }
};

module.exports = { connect };
