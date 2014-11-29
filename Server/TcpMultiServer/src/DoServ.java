import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DoServ extends Thread {

	Socket socket;
	BufferedReader clientReader;
	PrintWriter serverWriter;
	FileOutputStream fos;
	PrintWriter pw;
	
	public DoServ(Socket socket) {
		this.socket = socket;
		try {
			// Reader for reading stream from client.
			clientReader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			// Writer for sending stream to client.
			serverWriter = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream())), true);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				String clientStr = clientReader.readLine();
				System.out.println("Client Say: " + clientStr);
				/*if(clientStr.equals("hello")){
					serverWriter.println("hello");
				}*/
				if (clientStr.equals("plain")) {
					serverWriter.println("hello");
				} else if (clientStr.equals("end")) {
					break;
				} else {
					serverWriter.println("OK");
					writeToFile(socket.getRemoteSocketAddress().toString(),
							clientStr);
				}
				serverWriter.flush();
			} catch (Exception e) {
				try {
					serverWriter.close();
					clientReader.close();
					socket.close();
					System.out.println(socket.getRemoteSocketAddress()
							+ " disconnected.");
					break;
				} catch (Exception e1) {
					System.out.println(e1);
				}
			}
		}
	}

  synchronized private void writeToFile(String ip, String inputStr) {
		
		try {
			fos = new FileOutputStream("Memo.txt",true);
			pw=new PrintWriter(fos);
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			pw.println(String.format("%s %s Says: %s", ip, format.format(date),
					inputStr));
			pw.flush();
			pw.close();
			fos.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
