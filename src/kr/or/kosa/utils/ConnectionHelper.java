package kr.or.kosa.utils;
//ConnectionHelper를 통해 반복되는 코드를 줄여볼까

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectionHelper {
	//함수 >> 
	//많이 쓰니까 static >> 
	//이 함수가 뭘 반환하면 될까? 연결에 대한 정보. 인터페이스 타입을 넘겨주는 게 편함
	//그럼 리턴타입은 Connection 인터페이스 타입
	public static Connection getConnection(String dsn) {//String dsn : 오라클인지 mysql인지.. dataSourceName
		Connection conn = null;
		try {
			if(dsn.equals("oracle")) {
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "KOSA", "1004");
			}else if(dsn.equals("mariadb")) {
				conn= DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/kosadb","kosa","1004");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}
	
	//이름은 같은데 파라미터 개수나 타입을 다르게 >> 오버로딩 >> 확장하자
	public static Connection getConnection(String dsn, String id, String pwd) {//String dsn : 오라클인지 mysql인지.. dataSourceName
		Connection conn = null;
		try {
			if(dsn.equals("oracle")) {
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", id, pwd);//파라미터 값으로 세팅
			}else if(dsn.equals("mariadb")) {
				conn= DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/kosadb", id, pwd);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}
	
	public static void close(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	public static void close(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	public static void close(Statement stmt) {
		if(stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	//PreparedStatement 가장 중요!
	public static void close(PreparedStatement pstmt) {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
