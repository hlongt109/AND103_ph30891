const http = require('node:http');
const fs = require('node:fs')
const path = require('path');

const hostname = '127.0.0.1'; // localhost
const port = 3002;

const server = http.createServer((req, res) => {
    res.statusCode = 200;
    res.setHeader('Content-Type', 'text/html; charset=UTF-8'); // sửa cú pháp đúng
    // fs.readFile(path.join(__dirname,'views', 'index.html'), (err, data) => {
    fs.readFile(path.join(__dirname,'index.html'), (err, data) => { // sử dụng fs.readFile
        if (err) {
            res.statusCode = 500;
            res.end("Server Error");
            return;
        }
        res.end(data); // đặt res.end bên trong callback của fs.readFile
    });
});
server.listen(port, hostname, () => {
    console.log(`Server running at http://${hostname}:${port}/`);
});
 