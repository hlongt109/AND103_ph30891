const express = require('express');
const router = express.Router();

const mongoose = require("mongoose");
const RES = require('./Common_res');
const uri = RES.uri;

const DistributorsModal = require('./distributors');

router.get('/', async(req, res) =>{
    await mongoose.connect(uri);
    let distributor = await DistributorsModal.find();
    console.log(distributor)
    res.send(distributor);
})


module.exports = router