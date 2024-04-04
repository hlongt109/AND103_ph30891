var express = require('express');
var router = express.Router();

// them model
const Distributors = require('../models/distributors')
const Fruits = require('../models/fruits')

// api them distributor
router.post('/add-distributor', async (req, res) => {
    try {
        const data = req.body;
        const newDistributor = new Distributors({
            name: data.name
        });

        const result = await newDistributor.save();
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
        console.log("Error: " + error);
    }
});

// api them fruit
router.post('/add-fruit', async (req, res) => {
    try {
        const data = req.body;
        const newfruit = new Fruits({
            name: data.name,
            quantity: data.quantity,
            price: data.price,
            status: data.status,
            image: data.image,
            description: data.description,
            id_distributor: data.id_distributor
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

router.get("/get-list-distributor", async (req, res) => {
    try {
        const data = await Distributors.find();
        if (data) {
            res.json({
                "status": 200,
                "messenger": "get thanh cong",
                "data": data
            })
        } else {
            res.json({
                "status": 400,
                "messenger": "not found",
                "data": []
            })
        }
    } catch (error) {

    }
})
// 
router.get('/get-list-fruit', async (req, res) => {
    const authHeader = req.headers['authorization']
    const token = authHeader && authHeader.split(" ")[1]
    if (token == null) return res.sendStatus(401);
    let payload;

    JWT.verify(token, SECRETKEY, (err, _payload) => {
        // kiem tra token, neu token ko dung , hoac het han
        // tra status code 403
        // tra status het han 401 khi token het han
        if (err instanceof JWT.TokenExpiredError) return res.sendStatus(401)
        if (err) return res.sendStatus(403)

        payload = _payload
    })
    console.log(payload)

    try {
        const data = await Fruits.find().populate("id_distributor");
        if (data) {
            res.json({
                "status": 200,
                "messenger": "Danh sach fruit",
                "data": data
            })
        } else {
            res.json({
                "status": 400,
                "messenger": "Not found",
                "data": []
            })
        }
        // console.log(data)

    } catch (error) {
        console.log(error);
    }
});
// lab 7
router.get("/get-page-fruit", async (req, res) => {
    const authHeader = req.headers['authorization']
    const token = authHeader && authHeader.split(" ")[1]
    if (token == null) return res.sendStatus(401);
    let payload;

    JWT.verify(token, SECRETKEY, (err, _payload) => {
        if (err instanceof JWT.TokenExpiredError) return res.sendStatus(401)
        if (err) return res.sendStatus(403)
        payload = _payload
    })

    let perPage = 4; // so luong san pham hien tren 1 page
    let page = req.query.page || 1; //Page truyen len
    let skip = (perPage * page) - perPage; // phan trang
    let count = await Fruits.find().count(); // Lay tong so phan tu

    // filtering
    // loc theo name
    const name = { "$regex": req.query.name ?? "", "$options": "i" }
    // loc theo gia lon hon hoac bang gia truyen vao
    const price = { $gte: req.query.price ?? 0 }
    // loc sap xep theo gia
    // const sort = {price: req.query.sort ?? 1}
    // loc sap xep theo gia
    let sort = { price: 1 }; // Sắp xếp mặc định tăng dần
    // Kiểm tra giá trị của req.query.sort và gán lại nếu giá trị không hợp lệ
    if (req.query.sort === "1" || req.query.sort === "-1") {
        sort.price = parseInt(req.query.sort);
    }

    try {
        const data = await Fruits.find({ name: name, price: price })
            .populate("id_distributor")
            .sort(sort)
            .skip(skip)
            .limit(perPage)

        if (data) {
            res.json({
                "status": 200,
                "messenger": "Danh sach fruit",
                "data": {
                    "data": data,
                    "currentPage": Number(page),
                    "totalPage": Math.ceil(count / page)
                }
            })
        } else {
            res.json({
                "status": 400,
                "messenger": "Not found",
                "data": []
            })
        }
        // console.log(data)
    } catch (error) {
        console.log(error)
    }
})

router.get('/get-fruit-by-id/:id', async (req, res) => {
    try {
        const { id } = req.params
        const data = await Fruits.findById(id).populate('id_distributor');
        res.json({
            "status": 200,
            "messenger": "Danh sach fruit",
            "data": data
        })
    } catch (error) {
        console.log(error);
    }
});

router.get('/get-list-fruit-in-price', async (req, res) => {
    try {
        const { price_start, price_end } = req.query

        const query = { price: { $gte: price_start, $lte: price_end } }

        const data = await Fruits.find(query, 'name quantity price id_distributor')
            .populate('id_distributor')
            .sort({ quantity: -1 })
            .skip(0)
            .limit(2)
        res.json({
            "status": 200,
            "messenger": "Danh sach fruit",
            "data": data
        })
    } catch (error) {
        console.log(error);
    }
});

router.get('/get-list-fruit-have-name-a-or-x', async (req, res) => {
    try {
        const query = {
            $or: [
                { name: { $regex: "T" } },
                { name: { $regex: "X" } },
            ]
        }
        const data = await Fruits.find(query, 'name quantity price id_distributor')
            .populate('id_distributor')

        res.json({
            "status": 200,
            "messenger": "Danh sach fruit",
            "data": data
        })
    } catch (error) {
        console.log(error);
    }
});

// api cap nhat fruit
// router.put('/update-fruit-by-id/:id', async (req, res) => {
//     try {
//         const { id } = req.params
//         const data = req.body;
//         const updatefruit = await Fruits.findById(id)
//         let result = null;
//         if (updatefruit) {
//             updatefruit.name = data.name ?? updatefruit.name;
//             updatefruit.quantity = data.quantity ?? updatefruit.quantity,
//                 updatefruit.price = data.price ?? updatefruit.price,
//                 updatefruit.status = data.status ?? updatefruit.status,
//                 updatefruit.image = data.image ?? updatefruit.image,
//                 updatefruit.description = data.description ?? updatefruit.description,
//                 updatefruit.id_distributor = data.id_distributor ?? updatefruit.id_distributor
//             result = await updatefruit.save();
//         }

//         if (result) {
//             res.json({
//                 "status": 200,
//                 "messenger": "Cap nhat thanh cong",
//                 "data": result
//             })
//         } else {
//             res.json({
//                 "status": 400,
//                 "messenger": "Loi, cap nhat khong thanh cong",
//                 "data": []
//             })
//         }
//     } catch (error) {
//         console.log(error);
//     }
// });
// xoa mot fruit
router.delete('/destroy-fruit-by-id/:id', async (req, res) => {
    try {
        const { id } = req.params
        const result = await Fruits.findByIdAndDelete(id);
        if (result) {
            res.json({
                "status": 200,
                "messenger": "Xoa thanh cong",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "messenger": "Loi, xoa khong thanh cong",
                "data": []
            })
        }
    } catch (error) {
        console.log(error);
    }
});

const Upload = require('../config/common/upload');
router.post('/add-fruit-with-file-image', Upload.array('image', 5), async (req, res) => {

    try {
        const data = req.body; // lay du lieu tu body
        const { files } = req  // files neu upload nhieu, file neu up load 1 anh
        const urlsImage = files.map((file) => `${req.protocol}://${req.get("host")}/uploads/${file.filename}`)
        // url anh se duoc luu duoi dang : http://localhost:3000/upload/filename
        const newFruit = new Fruits({
            name: data.name,
            quantity: data.quantity,
            price: data.price,
            status: data.status,
            image: urlsImage, // them url hinh
            description: data.description,
            id_distributor: data.id_distributor
        });
        const result = ((await newFruit.save()).populate("id_distributor")); // them vao database
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
router.put('/update-fruit-by-id/:id', Upload.array('image', 5), async (req, res) => {
    try {
        const { id } = req.params
        const data = req.body;
        const { files } = req;

        let urlImg;
        const updatefruit = await Fruits.findById(id)
        if (files && files.length > 0) {
            urlImg = files.map((file) => `${req.protocol}://${req.get("host")}/uploads/${file.filename}`);

        }
        if (urlImg == null) {
            urlImg = updatefruit.image;
        }

        let result = null;
        if (updatefruit) {
            updatefruit.name = data.name ?? updatefruit.name,
                updatefruit.quantity = data.quantity ?? updatefruit.quantity,
                updatefruit.price = data.price ?? updatefruit.price,
                updatefruit.status = data.status ?? updatefruit.status,

                updatefruit.image = urlImg,

                updatefruit.description = data.description ?? updatefruit.description,
                updatefruit.id_distributor = data.id_distributor ?? updatefruit.id_distributor,
                result = (await updatefruit.save()).populate("id_distributor");;
        }
        if (result) {
            res.json({
                'status': 200,
                'messenger': 'Cập nhật thành công',
                'data': result
            })
        } else {
            res.json({
                'status': 400,
                'messenger': 'Cập nhật không thành công',
                'data': []
            })
        }
    } catch (error) {
        console.log(error);
    }
})

const Users = require('../models/users');
const Transporter = require('../config/common/mail')
router.post('/register-send-email', Upload.single('avatar'), async (req, res) => {
    try {
        const data = req.body;
        const { file } = req
        const newUser = Users({
            username: data.username,
            password: data.password,
            email: data.email,
            name: data.name,
            avatar: `${req.protocol}://${req.get("host")}/uploads/${file.filename}`,
        })
        const reuslt = await newUser.save();
        if (reuslt) {
            const mailOptions = {
                from: "longthph30891@fpt.edu.vn", // email gui di
                to: reuslt.email,  // email nhan
                subject: "Dang ky thanh cong", // subject
                text: "Thank you for register", // noi dung mail
            };
            await Transporter.sendMail(mailOptions);
            res.json({
                "status": 200,
                "messenger": "Them thanh cong",
                "data": reuslt
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

// api login
const JWT = require('jsonwebtoken');
const e = require('express');
const { token } = require('morgan');

const SECRETKEY = "FPTPOLYTECHNIC"

router.post('/login', async (req, res) => {
    try {
        const { username, password } = req.body;

        const user = await Users.findOne({ username, password });

        if (user) {
            // token nfuoi dung se du dung gui len header moi lan muon goi api
            const token = JWT.sign({ id: user._id }, SECRETKEY, { expiresIn: '1h' });
            // khi token het han , nguoi dung se call 1 api khac de lay token moi
            // truyen refeshToken len de lat ve 1 cap token , refeshToken moi
            // neu ca hai het han nguoi dung phai dang nhap lai
            const refeshToken = JWT.sign({ id: user._id }, SECRETKEY, { expiresIn: '1h' });
            // expiresIn thoi gian token
            res.json({
                "status": 200,
                "messenger": "Dang nhap thanh cong",
                "data": user,
                "token": token,
                "refeshToken": refeshToken
            })
        } else {

            res.json({
                "status": 400,
                "messenger": "Loi dang nhap",
                "data": [],
            })
        }
        console.log(token)
    } catch (error) {
        console.log(error)
    }
})
module.exports = router;