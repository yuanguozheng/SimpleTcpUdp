import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class CClient {

	public static void main(String[] args) {
		DatagramSocket sendSocket = null;
		Scanner scanner = null;
		try {
			sendSocket = new DatagramSocket();
			sendSocket.connect(new InetSocketAddress("127.0.0.1", 8899));
			scanner = new Scanner(System.in);
			String str = scanner.nextLine();
			while (true) {
				byte[] buffer = str.getBytes();
				DatagramPacket dataPacket = new DatagramPacket(buffer,
						buffer.length);
				sendSocket.send(dataPacket);
				dataPacket = new DatagramPacket(buffer, buffer.length);
				sendSocket.receive(dataPacket);
				String reStr = new String(dataPacket.getData(), 0,
						dataPacket.getLength());
				System.out.println("Server: " + reStr);
				str = scanner.nextLine();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
