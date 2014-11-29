import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class MultiServer {

	public static void main(String[] args) {
		DatagramSocket serverSocket = null;
		try {
			serverSocket = new DatagramSocket(8899);
			while (true) {
				byte[] buffer = new byte[256];
				DatagramPacket getPacket = new DatagramPacket(buffer,
						buffer.length);
				serverSocket.receive(getPacket);
				ThreadServer threadServer = new ThreadServer(serverSocket,
						getPacket);
				threadServer.run();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
