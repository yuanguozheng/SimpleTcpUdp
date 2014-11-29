import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class client {
	public static void main(String[] args) {
		Socket socket = null;
		BufferedReader serverReader = null;
		PrintWriter clientWriter = null;
		try {
			socket = new Socket("127.0.0.1", 8888);
			// Reader for reading stream from Server.
			serverReader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			// Writer for sending stream to server.
			clientWriter = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream())), true);
			System.out.println("Conntected with server.");

		} catch (Exception e) {
			System.out.println("Cannot connect server.");
		}
		Scanner scanner = null;
		try {
			scanner = new Scanner(System.in);
			String input = scanner.nextLine();
			clientWriter.println(input);
			while (true) {
				String readLine = serverReader.readLine();
				System.out.println("Server say: " + readLine);
				input = scanner.nextLine();
				clientWriter.println(input);
				clientWriter.flush();
			}
		} catch (Exception e) {
			try {
				scanner.close();
				serverReader.close();
				clientWriter.close();
				System.out.println("Disconnected with server.");
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}
	}
}
