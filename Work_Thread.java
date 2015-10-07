package webserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Work_Thread extends Thread {

	// 클라이언트와의 접속 소켓
	private Socket connectionSocket;
	private int target_id;

	public Work_Thread(Socket connectionSocket) {
		this.connectionSocket = connectionSocket;
	}

	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					this.connectionSocket.getInputStream()));
			String str = in.readLine();
			System.out.println("S: Received: '" + str + "'");

			PrintWriter out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(
							this.connectionSocket.getOutputStream())), true);

			Connection con = null;
			Statement stmt = null;
			String sql;
			ResultSet rs;
			String url = "jdbc:mysql://localhost/messagedb";
			String id = "root"; // 사용자 계정
			String pw = "1234"; // 사용자 계정의 패스워드
			Class.forName("com.mysql.jdbc.Driver");

			// 메세지 전달
			if (str.contains("requsetDB")) {
				String Temp = str.substring(10);
				con = DriverManager.getConnection(url, id, pw);
				System.out.println("데이터베이스 인증");
				stmt = con.createStatement();
				sql = "select * from message where target_id=" + Temp + "";
				rs = stmt.executeQuery(sql);
				rs.next();
				sql = "" + rs.getInt("category_id");
				sql += "///" + rs.getString("message") + "///";
				while (rs.next()) {
					sql += rs.getInt("category_id") + "///"
							+ rs.getString("message") + "///";
				}
				out.println(sql);
				out.flush();
			} else {
				con = DriverManager.getConnection(url, id, pw);
				System.out.println("데이터베이스 인증");
				stmt = con.createStatement();
				// 인증
				try {
					// login[0]에는 학번이 [1]에는 비밀번호 [2]에는 푸쉬키값이 저장된다
					String[] login = str.split("/");
					System.out.println("인증실행");

					sql = "select s_id, push_key from student where student_number="
							+ Integer.parseInt(login[0])
							+ " and phone_number="
							+ login[1].substring(1);
					// 쿼리를 실행한다.
					rs = stmt.executeQuery(sql);
					rs.next();
					// 만약 아이디와 비밀번호가 맞다면 계속 진행되고, 아니라면 catch로 넘어간다
					// 조회된 결과, 맞다면 access를 전송

					out.println("access/" + rs.getInt("s_id"));
					out.flush();
					if (rs.getString("push_key").equals("empty")) {
						sql = "update student set push_key='" + login[2]
								+ "' where s_id=" + rs.getInt("s_id");
						stmt.executeUpdate(sql);
					}
					System.out.println("access");
				} catch (Exception e) {
					e.printStackTrace();
					// 조회된 결과, 아니라면 denied를 전송
					out.println("denied");
					out.flush();

				} finally { // 쿼리가 성공 또는 실패에 상관없이 사용한 자원을 해제 한다. (순서중요)
					if (con != null)
						try {
							con.close();// Connection 해제
						} catch (SQLException sqle) {
						}
				}
			}
		} catch (Exception e) {
			System.out.println("Server Error");
			e.printStackTrace();
		}
		try {
			this.connectionSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static String getTime() {
		SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
		return f.format(new Date());
	} // getTime
}
