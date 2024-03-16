const express = require('express');
const port = 3000;
const app = express();

const mongoose = require('mongoose');
const uri = 'mongodb+srv://hlong109:mESUqfMThzZx1zwj@cluster0.ryli2j3.mongodb.net/AND103';

const svModel = require('./studentModel');

app.get('/', async (req, res) =>{
   await mongoose.connect(uri);

   let sinhViens = await svModel.find();

   console.log(sinhViens);

   res.send(sinhViens);

});

app.get('/add_sv' , async(req, res) =>{
    await mongoose.connect(uri);

    arrNewSv = [];

    arrNewSv.push({
        name: 'Tran Thu Hien',
        tuoi: 24,
        mssv: 'PH14523',
        daRaTruong: true
    });

    let kq = await svModel.insertMany(arrNewSv);

    console.log(kq);

    let sinhViens = await svModel.find();

    res.send(sinhViens);
});

app.listen(port, () =>{
    console.log(`Example app listening on port ${port}`)
})
