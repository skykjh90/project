package webserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Web_Server {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		Work_Thread serverThread;

		try {
			serverSocket = new ServerSocket(26127);
			System.out.println(getTime() + " 서버가 준비되었습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		} // try - catch
		Socket socket = null;
		try {
			System.out.println(getTime() + " 연결요청을 기다립니다.");
			// 서버소켓은 클라이언트의 연결요청이 올 때까지 실행을 멈추고 계속 기다린다.
			// 클라이언트의 연결요청이 오면 클라이언트 소켓과 통신할 새로운 소켓을 생성한다.
			while ((socket = serverSocket.accept()) != null) {
				System.out.println(getTime() + socket.getInetAddress()
						+ " 로부터 연결요청이 들어왔습니다.");
				// 서버 쓰레드를 생성하여 실행한다.
				serverThread = new Work_Thread(socket);
				serverThread.start();
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	static String getTime() {
		SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
		return f.format(new Date());
	} // getTime
}
