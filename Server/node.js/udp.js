var dgram = require('dgram');

var server = dgram.createSocket('udp4');

var port = 11500;

server.on('message',function(data, clientInfo){
	console.log('Server got message: ' + data);
	server.send(
		data,0,data.length,
		clientInfo.port,clientInfo.address);
});

server.on('listening',function(){
	var address = server.address();
	console.log('Server listening on ' + address.address + ':' + address.port);
});

server.bind(port);