package gui;

import java.awt.event.*;
import javax.swing.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertPopup {
	private JDialog dialog;
	private JTextField snumber_input;
	private JTextField major_input;
	private JTextField name_input;
	private JTextField phone_input;
	private JTextField email_input;
	private JTextField subject_input;
	private JTextField blacklist_input;
	private JLabel major;
	private JLabel name;
	private JLabel phone;
	private JLabel email;
	private JLabel subject;
	private JLabel blacklist;
	private JLabel snumber;
	private JButton sendButton;
	private JButton cancelButton;

	public InsertPopup() {

		// Create the JDialog popup
		dialog = new JDialog(new JFrame(), "데이터 추가");

		// Create the JPanel 
		int panelWidth = 280;
		int panelHeight = 220;
		JPanel panel = new JPanel(null);
		panel.setPreferredSize(new java.awt.Dimension(panelWidth, panelHeight));

		// Create the components
		snumber_input = new JTextField();
		major_input = new JTextField();
		name_input = new JTextField();
		phone_input = new JTextField();
		email_input = new JTextField();
		subject_input = new JTextField();
		blacklist_input = new JTextField();
		
		});

		panel.add(phone);
		panel.add(phone_input);
		panel.add(email);
		panel.add(email_input);
		panel.add(name);
		panel.add(name_input);
		panel.add(major);
		panel.add(major_input);
		panel.add(snumber);
		panel.add(snumber_input);
		panel.add(blacklist);
		panel.add(blacklist_input);
		panel.add(sendButton);
		panel.add(cancelButton);

		int startingX =	10;
		int startingY = 10;

		snumber.setBounds(startingX, startingY, 45, 25);
		snumber_input.setBounds(startingX + 60, startingY, 200, 25);

		major.setBounds(startingX, startingY + 30, 45, 25);
		major_input.setBounds(startingX + 60, startingY + 30, 200, 25);

		name.setBounds(startingX, startingY + 60, 45, 25);
		name_input.setBounds(startingX + 60, startingY + 60, 200, 25);

		phone.setBounds(startingX, startingY + 90, 45, 25);
		phone_input.setBounds(startingX + 60, startingY + 90, 200, 25);

		email.setBounds(startingX, startingY + 120, 45, 25);
		email_input.setBounds(startingX + 60, startingY + 120, 200, 25);
		
		blacklist.setBounds(startingX, startingY + 150, 45, 25);
		blacklist_input.setBounds(startingX + 60, startingY + 150, 200, 25);

		dialog.add(panel);
		dialog.pack();
		dialog.setAlwaysOnTop(true);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
		dialog.setResizable(false); // 창크기 변화 고정
	}

	public void insert() {
		Connection con = null;
		Statement stmt = null;
		try {
			String url = "jdbc:mysql://localhost/studentdb?useUnicode=true&characterEncoding=utf8"; // 사용하려는 데이터베이스명을
																// 포함한 URL 기술
			String id = "root"; // 사용자 계정
			String pw = "1234"; // 사용자 계정의 패스워드

			Class.forName("com.mysql.jdbc.Driver"); // 데이터베이스와 연동하기 위해
													// DriverManager에 등록한다.
			con = DriverManager.getConnection(url, id, pw); // DriverManager
															// 객체로부터 Connection
															// 객체를 얻어온다.
			stmt = con.createStatement();

			String[] insert = { snumber_input.getText(), major_input.getText(),
					name_input.getText(), phone_input.getText().substring(1),
					email_input.getText(), blacklist_input.getText() };

			String sql = "insert into student(student_number, phone_number, student_name, student_major, mail_address, black_list, push_key, password) values("
					+ Integer.parseInt(insert[0]) + ", "
					+ Integer.parseInt(insert[3]) + ", '" + insert[2] + "', '"
					+ insert[1] + "', '" + insert[4] + "', '" + insert[5]+ "', 'empty','12345678')";

			System.out.println(sql);
			stmt.executeUpdate(sql);
		} catch (Exception e) { // 예외가 발생하면 예외 상황을 처리한다.
			e.printStackTrace();
		} finally { // 쿼리가 성공 또는 실패에 상관없이 사용한 자원을 해제 한다. (순서중요)
			if (con != null) {
				try {	
					con.close();// Connection 해제
				} catch (SQLException sqle) {
				}
			}

		}
	}
}
