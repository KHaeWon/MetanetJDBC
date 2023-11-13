import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import kr.or.kosa.utils.ConnectionHelper;

public class Ex03_Oracle_DML_update_Helper {

	public static void main(String[] args) throws SQLException {

//		Connection conn = null;
//		conn = ConnectionHelper.getConnection("oracle");
//		System.out.println(conn.toString());//oracle.jdbc.driver.T4CConnection@71c8becc
//		System.out.println(conn.getMetaData().getDatabaseProductName());//Oracle
//		System.out.println(conn.getMetaData().getDatabaseProductVersion());//Oracle Database 11g Express Edition Release 11.2.0.2.0 - 64bit Production
//		System.out.println(conn.isClosed());//닫혔니? false가 나오길 의도
//		ConnectionHelper.close(conn);
//		System.out.println(conn.isClosed());//true값이 나오길 의도
		
		//개인별 jdbc 문제
		//String sql = "update dmlemp set ename=?, sal=?, job=?, deptno=? where empno=?";
		//ename=홍길동, sal=1000, job=IT, deptno=10, empno=100
		//으로 변경하는 dml 수행
		//단, parameter 처리는 PreparedStatement 사용
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		//PreparedStatement -> 해킹에 대한 보안 가능.. 좋다는 건데 왜?
		//PreparedStatement는 받기 전에 sql을 서버에 보내서 컴파일 보내서 객체 생성
		
		try {
			conn = ConnectionHelper.getConnection("oracle");
			String sql = "update dmlemp set ename=?, sal=?, job=?, deptno=? where empno=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "홍길동");
			pstmt.setInt(2, 1000);
			pstmt.setString(3, "IT");
			pstmt.setInt(4, 10);
			pstmt.setInt(5, 100);
			
			//executeUpdate()로 객체에 파라미터만 보내서 업데이트 >> 해커가 가로채도 뭔지 모름
			int row = pstmt.executeUpdate();
			if(row>0) {
				System.out.println("row : "+row);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			ConnectionHelper.close(pstmt);
			ConnectionHelper.close(conn);
		}
	}

}
