import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadServer implements Runnable {

	DatagramSocket serverSocket;
	DatagramPacket getPacket;

	public ThreadServer(DatagramSocket serverSocket, DatagramPacket getPacket) {
		this.serverSocket = serverSocket;
		this.getPacket = getPacket;
	}

	@Override
	public void run() {
		DatagramPacket sendPacket = null;
		byte[] buffer = getPacket.getData();
		String str = new String(buffer, 0, getPacket.getLength());
		System.out.println(String.format("%s:%d says: ", getPacket.getAddress()
				.toString(), getPacket.getPort())
				+ str);
		if (str.equals("plain")) {
			byte[] helloBytes = "hello".getBytes();
			sendPacket = new DatagramPacket(helloBytes, 0, helloBytes.length,
					getPacket.getAddress(), getPacket.getPort());
			try {
				serverSocket.send(sendPacket);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		} else if (str.equals("end")) {
			return;
		} else {
			byte[] okBytes = "OK".getBytes();
			sendPacket = new DatagramPacket(okBytes, 0, okBytes.length,
					getPacket.getAddress(), getPacket.getPort());
			try {
				serverSocket.send(sendPacket);
				writeToFile(String.format("%s:%d", getPacket.getAddress()
						.toString(), getPacket.getPort()), str);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	synchronized private void writeToFile(String ip, String inputStr) {
		FileOutputStream fos = null;
		PrintWriter pw = null;
		try {
			fos = new FileOutputStream("Memo.txt", true);
			pw = new PrintWriter(fos);
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
