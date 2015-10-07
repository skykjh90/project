package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public class MainPanel extends JPanel {

	private JScrollPane tablePane;
	private JTable table;
	private EditPopup editpop;
	private InsertPopup insertpop;
	private DeletePopup deletepop;
	private SearchPopup searchpop;
	private MessagePopup messagepop;
	private int[] sid = new int[100];
	private String[] pushid = new String[100];

	public MainPanel() {
		initComponents();
	}

	public void initComponents() {
	
		JButton insertbutton = new JButton(new ImageIcon("insert.png"));
		JButton deleteButton = new JButton(new ImageIcon("delete.png"));
		JButton updateButton = new JButton(new ImageIcon("update.png"));
		JButton searchButton = new JButton(new ImageIcon("search.png"));
		JButton refreshButton = new JButton(new ImageIcon("refresh.png"));
	
		add(insertbutton);
		insertbutton.setBounds(10, 72, 70, 30);
		add(deleteButton);
		deleteButton.setBounds(90, 72, 70, 30);
		add(updateButton);
		updateButton.setBounds(170, 72, 70, 30);
		add(searchButton);
		searchButton.setBounds(250, 72, 70, 30);
		add(refreshButton);
		refreshButton.setBounds(330, 72, 70, 30);
	
		table = new CheckBoxTable();
		tablePane = new JScrollPane(table);
		
		add(tablePane);
		tablePane.setBounds(10, 110, 850, 800);
			
		setRow();
		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//
				DefaultTableModel dtm = (DefaultTableModel) table.getModel();
				int[] temp = sid.clone();
				int check = 0;
				for (int i = 0; i < dtm.getRowCount(); i++) 
				{
					// 체크 박스가 선택되어 있으면
					if(dtm.getValueAt(i, 0) != null && (Boolean) dtm.getValueAt(i, 0) == true) 
					{
						System.out.print(i + " : ");
						for (int j = 0; j < dtm.getColumnCount(); j++) 
						{
							System.out.print(dtm.getValueAt(i, j) + "  ");

						}
						System.out.println();
						check++;
					}
					else
					{
						temp[i] = -1;
					}
				}
				if(check == 0)
				{
					JOptionPane.showMessageDialog(new JFrame(), "데이터를 수정할 사람을 선택해주세요", "데이터 수정 에러", JOptionPane.ERROR_MESSAGE);
				}
				else if(check == 1)
				{
					editpop = new EditPopup(temp);
				}
				else 
				{
					JOptionPane.showMessageDialog(new JFrame(), "데이터를 수정할 사람을 1명만 선택해주세요", "데이터 수정 에러", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		insertbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				insertpop = new InsertPopup();
			}
		});
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int check=0;
		///////////created/////////////
						DefaultTableModel dtm = (DefaultTableModel) table.getModel();
						int[] temp = sid.clone();
						for (int i = 0; i < dtm.getRowCount(); i++) 
						{
							// 체크 박스가 선택되어 있으면
							if(dtm.getValueAt(i, 0) != null
									&& (Boolean) dtm.getValueAt(i, 0) == true) {
								System.out.print(i + " : ");
								for (int j = 0; j < dtm.getColumnCount(); j++) {
									System.out.print(dtm.getValueAt(i, j) + "  ");
									check++;
								}
								System.out.println();
							}else{
								temp[i] = -1;
							}
						}
						if(check == 0)
						{
							JOptionPane.showMessageDialog(new JFrame(), "데이터를 삭제할 사람을 선택해주세요", "데이터 삭제 에러", JOptionPane.ERROR_MESSAGE);
						}
						else
						{
							deletepop = new DeletePopup(temp);
						}
					
			}
		});
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				searchpop = new SearchPopup();
			}
		});

		refreshButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setRow();
			}
		});

	}
	
	public void setRow() {
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
			System.out.println("학생 목록을 받아옵니다"); // 커넥션이 제대로 연결되면 수행된다.
			stmt = con.createStatement();

			String sql = "select s_id, student_number, student_major, student_name, phone_number, mail_address, black_list, push_key from student";
		    //pstmt.executeUpdate(); // 쿼리를 실행한다.
			ResultSet rs = stmt.executeQuery(sql);
			// STEP 5: Extract data from result set
			int index = 0;
			while(true) {
				if(index==100){
					break;
				}
				table.setValueAt(false, index, 0);
				table.setValueAt("", index, 1);
				table.setValueAt("", index, 2);
				table.setValueAt("", index, 3);
				table.setValueAt("", index, 4);
				table.setValueAt("", index, 5);
				table.setValueAt("", index, 6);
				index++;
			}
			index=0;
			while (rs.next()) {
				if(index==100){
					break;
				}
				// Retrieve by column name
				sid[index] = rs.getInt("s_id");
				pushid[index] = rs.getString("push_key");
				table.setValueAt(rs.getInt("student_number"), index, 1);
				table.setValueAt(rs.getString("student_major"), index, 2);
				table.setValueAt(rs.getString("student_name"), index, 3);
				table.setValueAt("0" + rs.getInt("phone_number"), index, 4);
				table.setValueAt(rs.getString("mail_address"), index, 5);
				table.setValueAt(rs.getString("black_list"), index, 6);
				index++;
			}
		} catch (Exception e) { // 예외가 발생하면 예외 상황을 처리한다.
			e.printStackTrace();
		} finally { // 쿼리가 성공 또는 실패에 상관없이 사용한 자원을 해제 한다. 
			if (con != null)
				try {
					con.close();// Connection 해제
				} catch (SQLException sqle) {
				} 
		}
	}
}
