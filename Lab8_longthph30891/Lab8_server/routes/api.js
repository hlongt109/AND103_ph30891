var express = require('express')
var router = express.Router();

// 
const Orders = require("../config/models/Order")
const Fruits = require("../config/models/Fruit")

router.post("/add-order", async (req, res) => {
    try {
        const data = req.body;
        const newOrder = new Orders({
            order_code: data.order_code,
            id_user: data.id_user
        })
        const result = await newOrder.save();
        if (result) {
            res.json({
                "status": 200,
                "messenger": "Them thanh cong",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "messenger": "Loi, them khong thanh cong",
                "data": null
            })
        }
    } catch (error) {
        console.log(error);
    }
})

router.get("/get_list_order", async (req, res) => {
    try {
        const { id_user } = req.query
        const result = await Orders.find({ id_user: id_user });
        if(result){
            res.json({
                "status": 200,
                "messenger": "Lay danh sach thanh cong",
                "data": result
            })
        }else{
            res.json({
                "status": 400,
                "messenger": "Loi lay danh sach",
                "data": null
            })
        }
    } catch (error) {
        console.log(error);
    }
});

router.delete("/delete-order/:order_code", async(req, res) =>{
    try {
        const {order_code} = req.params
        const result = await Orders.findOneAndDelete({order_code: order_code});
        console.log(order_code);
        console.log(result)
        if(result){
            res.json({
                "status": 200,
                "messenger": "Xoa thanh cong",
                "data": result
            })
        }else{
            res.json({
                "status": 400,
                "messenger": "Loi xoa",
                "data": null
            })
        }
    } catch (error) {
         console.log(error);
    }
})

router.get("/get-list-fruit", async(req, res) =>{
    try {
        const data = await Fruits.find();

        if (!data || data.length === 0) {
            res.status(404).send("Resource not found");
        } else {
            res.status(200).send(data);
        }
        // res.json({
        //     "status": 200,
        //     "messenger": "Danh sach fruit",
        //     "data": data
        // })
    } catch (error) {
        console.log(error);
    }
})

router.post('/add-fruit', async (req, res) => {
    try {
        const data = req.body; 
        const newfruit = new Fruits({
            name: data.name,
            price: data.price,
            quantity: data.quantity,
            weight: data.weight,
            image: data.image
        });
        const result = await newfruit.save(); 
        if (result) {
            res.json({
                "status": 200,
                "messenger": "Them thanh cong",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "messenger": "Loi, them khong thanh cong",
                "data": []
            })
        }
    } catch (error) {
        console.log(error);
    }
});


module.exports = router;