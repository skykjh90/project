package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;


public class CheckBoxTable extends JTable {
	private DefaultTableColumnModel dtcm;
	
	
	public CheckBoxTable() {
		super(100, 7);
		setRowHeight(20);
		setRowSelectionAllowed(false);
		
		TableColumnModel dtcm = (DefaultTableColumnModel) getColumnModel();
	
		dtcm.getColumn(0).setHeaderValue("선택");
		dtcm.getColumn(1).setHeaderValue("학  번");
		dtcm.getColumn(2).setHeaderValue("학  과");
		dtcm.getColumn(3).setHeaderValue("이  름");
		dtcm.getColumn(4).setHeaderValue("전화번호");
		dtcm.getColumn(5).setHeaderValue("메일주소");
		dtcm.getColumn(6).setHeaderValue("블랙리스트");
		
	}

	public Class<?> getColumnClass(int column) {

		switch (column) {
		case 0:
			return (Boolean.class); // 논리형 설정
		default:

			break;

		}

		return super.getColumnClass(column);

	}
	
}
