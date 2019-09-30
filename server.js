var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var mysql = require('mysql');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));

app.get('/', function (req, res) {
    return res.send({ error: true, message: 'Test Student Web API' })
});

var dbConn = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'lab_connect_mysql'
});

dbConn.connect();

app.get('/allstd', function (req, res) {
    dbConn.query('SELECT * FROM student', function (error, results, fields) {
        if(error) throw error;
        return res.send(results);
    });
});

app.post('/std', function (req, res) {
    var std = req.body

    if (!std) {
        return res.status(400).send({ error:true, message: 'Please provide student '});
    }

    dbConn.query("INSERT INTO student SET ? ", std, function (error, result, fields) {
        if (error) throw error;
        return res.send(results);
    });
});

app.listen(3000, function () {
    console.log('Node app is running on port 3000');
});

module.exports = app;