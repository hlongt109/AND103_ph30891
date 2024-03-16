const mongoose = require('mongoose');

const StudentSchema = mongoose.Schema({
    name : {
        type: String,
        required: true
    },
    tuoi: {
        type: Number,
        required: true
    },
    mssv: {
        type: String
    },
    daRaTruong: {
        type: Boolean
    }
});

const StudentModel = new mongoose.model('student', StudentSchema)
module.exports = StudentModel;

