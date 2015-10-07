package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class MainFrame extends JFrame {
	private JPanel contentPane;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame("Group Study Matching!!");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// create frame
	public MainFrame(String title) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 877, 800);
		ImageIcon icon = new ImageIcon("icon.png");
		setIconImage(icon.getImage());
		setResizable(false); // 창크기 변화 고정
		contentPane = new MainPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	
		// 메뉴바 생성
        JMenuBar bar = new JMenuBar(); //메뉴바 생성
        setJMenuBar(bar);//적용
        //파일 메뉴 만들기 ---------------------------------
        JMenu filemenu = new JMenu("파일(F)");//1차 메뉴탭 설정
        filemenu.setMnemonic('F'); //단축키를 Alf + F 로 설정

        JMenuItem newfile = new JMenuItem("새파일(N)");//2차 메뉴 탭(아이템) 설정
        newfile.setMnemonic('N'); //단축키를 Alf + N 으로 설정
        filemenu.add(newfile);//JMenuItem 인 newfile 을 JMenu의 filemenu에 저장

        JMenuItem open = new JMenuItem("열기(O)");
        open.setMnemonic('O');
        filemenu.add(open);

        JMenuItem save = new JMenuItem("저장(S)");
        save.setMnemonic('S');
        filemenu.add(save);

        JMenuItem close = new JMenuItem("닫기(C)");
        close.setMnemonic('C');
        filemenu.add(close);
        bar.add(filemenu); //JMenuBar에 JMenu 부착

        //도움말 메뉴 만들기--------------------------------

        JMenu helpmenu = new JMenu("도움말(D)");
        helpmenu.setMnemonic('D'); //단축키를 Alf + D 로 설정

        JMenuItem help = new JMenuItem("Help(H)");
        help.setMnemonic('H');
        helpmenu.add(help);
        bar.add(helpmenu);
        // 메뉴바 생성
        }
	
	}

