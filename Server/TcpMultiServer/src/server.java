import java.net.ServerSocket;
import java.net.Socket;

public class server {
	static ServerSocket server = null;

	public static void main(String[] args) {
		try {
			server = new ServerSocket(8888);
			System.out.println("Server start.");
			while (true) {
				Socket socket = server.accept();
				System.out.println(socket.getRemoteSocketAddress()
						+ " connected.");
				DoServ doServ = new DoServ(socket);
				doServ.start();
			}
		} catch (Exception e) {
			System.out.println("Cannot start server.");
		}
	}
}
