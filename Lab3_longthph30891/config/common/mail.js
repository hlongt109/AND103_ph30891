var nodemailer = require('nodemailer');
const transporter = nodemailer.createTransport({
    service: 'gmail',
    auth:{
        user : "longthph30891@fpt.edu.vn", // email gui di
        pass: "Devdeptrai109@", // mat khau email gui di
    }
});
module.exports = transporter;