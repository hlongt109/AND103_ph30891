
const express = require('express')
const app = express()
const port = 3001
const path = require('path')

app.use(express.static(path.join(__dirname, 'views')))

app.get('/', (req, res) => {
  res.send('Hello Express!');
  res.sendFile(path.join(__dirname,'views','index.html'))
})

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
})