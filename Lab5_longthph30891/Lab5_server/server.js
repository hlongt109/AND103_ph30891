const express = require('express');
const port = 3000;
const app = express();

const mongoose = require('mongoose');
const RES = require('./Common_res');
const uri = RES.uri;

const DistributorsModal = require('./distributors');

const apiMobile = require('./api');
const { log } = require('console');
app.use('/api', apiMobile);
app.use(express.json());


app.get('/', async (req, res) => {
    try {
        await mongoose.connect(uri);
        let distributor = await DistributorsModal.find();

        console.log(`fetch data success`)
        res.send(distributor);
    } catch (error) {
       console.log(error)
    }

})

app.post('/add-distributor', async (req, res) => {
    try {
        await mongoose.connect(uri);

        const data = req.body;
        const newDistributor = new DistributorsModal({
            name: data.name
        });

        const result = await newDistributor.save();

        if (result) {
            res.json({
                "status": 200,
                "message": "Thêm thành công",
                "data": result
            });
            
        } else {
            res.json({
                "status": 400,
                "message": "Lỗi, thêm không thành công",
                "data": []
            });
        }
    } catch (error) {
        console.log("Lỗi: " + error);
        res.status(500).json({
            "status": 500,
            "message": "Lỗi máy chủ"
        });
    }
});

app.get('/search-distributor', async (req, res) => {
    try {
        await mongoose.connect(uri);

        const key = req.query.key;
        const data = await DistributorsModal.find({ name: { "$regex": key, "$options": "i" } }).sort({ createdAt: -1 });
        
        if (!data || data.length === 0) {
            res.status(404).send("Resource not found");
        } else {
            res.status(200).send(data);
        }

        // if (data) {
        //     res.json({
        //         "status": 200,
        //         "message": "Tìm thành công",
        //         "data": data
        //     })
        // } else {
        //     res.json({
        //         "status": 400,
        //         "message": "Không tìm thấy kết quả",
        //         "data": []
        //     });
        // }

    } catch (error) {
        console.log("Error :" + error)
    }
})

app.delete('/delete-distributor/:id', async (req, res) => {
    try {
        const { id } = req.params
        const result = await DistributorsModal.findByIdAndDelete(id);
        if (result) {
            res.json({
                "status": 200,
                "message": "Tìm thành công",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "message": "Lỗi, tìm thành công",
                "data": []
            });
        }
    } catch (error) {
        console.log("Error :" + error)
    }
})

app.put('/update-distributor/:id', async (req, res) => {
    try {
        const { id } = req.params
        const data = req.body;
        const result = await DistributorsModal.findByIdAndUpdate(id, { name: data.name })
        if (result) {
            res.json({
                "status": 200,
                "message": "Cap nhat thành công",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "message": "Lỗi cap nhat",
                "data": []
            });
        }
    } catch (error) {
        console.log("Error :" + error)
    }
})

app.listen(port, () => {
    console.log("Connect success port :" + port);
})

module.exports = app;