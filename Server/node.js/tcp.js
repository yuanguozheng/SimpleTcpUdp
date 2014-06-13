/**
 * Created by 国正 on 14-2-19.
 */
var clients = [];

var net = require('net');

net.createServer(function (socket) {
    console.log('Conntected ' + socket.remoteAddress + ':' + socket.remotePort);
    socket.on('data', function (data) {
        console.log(socket.remoteAddress + ' ' + data);
		
		socket.write('Got message. You said: '+ data);
    });

    socket.on('error', function (err) {
        console.log("Error:", err.message);
    });

    socket.on('close', function () {
        console.log('Connection is closed.');
        var index = clients.indexOf(socket);
        clients.splice(index, 1);
    });

    /*socket.on('listening',function(){
     console.log('Server is listening 5006.');
     });*/
}).listen(5006);