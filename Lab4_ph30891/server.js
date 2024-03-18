const express = require('express')

const multer = require('multer')

var fs = require('fs')

const app = express();

const port = 3000;

app.listen(port, () => {
   console.log("Server dang chay cong :"+port);

})

app.get('/', (req, res) =>{
    res.sendFile(__dirname + "/upload.html")
})

// api

// SET STORAGE
var storage = multer.diskStorage({
    destination: function (req, file, cb) {

        var dir = './uploads';

        if (!fs.existsSync(dir)) {
            fs.mkdirSync(dir, { recursive: true });
        }

        cb(null, 'uploads')
    },
    filename: function (req, file, cb) {

        let fileName = file.originalname;
        arr = fileName.split('.');

        let newFileName = '';

        for (let i =0; i< arr.length; i++) {
            if (i != arr.length - 1) {
                newFileName += arr[i];
            } else {
                newFileName += ('-' + Date.now() + '.' + arr[i]);
            }
        }

        cb(null, newFileName)
    }
})

var upload = multer({ storage: storage })

//Uploading multiple files
app.post('/uploadmultiple', upload.array('myfiles', 5), (req, res, next) => {
    const files = req.files
    if (!files) {
        const error = new Error('Please choose files')
        error.httpStatusCode = 400
        return next(error)
    }
    res.send(files)
})


app.post("/uploadfile", upload.single('myfile'),(req, res, next) =>{
    const file = req.file
    if(!file){
        const err = new Error("Chon file de upload")
        err.htttpStatusCode =400;
        return next(err)
    }
    res.send(file);
})