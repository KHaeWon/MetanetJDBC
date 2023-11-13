import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/*
JDBC

1. Java 언어(APP)를 통해서 Oracle(소프트웨어) 연결해서 CRUD작업
2. Java App : Oracle , My-sql , MS-sql 등등 연결하고 작업(CRUD)
2.1 각각의 제품에 맞는 드라이버를 가지고 있어야 합니다
CASE 1: 삼성 노트북 >> HP 프린터 연결 >> HP프린터 사이트에서 드라이버 다운 >> 삼성 설치 
CASE 2: HP프린터 제조 회사는 ... 삼성, LG 회사마다 적용할 수 있는 드라이버를 별도 제작

각 언어에 맞는 드라이버를 다운로드 해서 제품에 맞게 설치 .... 접속 ...
Oracle (C:\oraclexe\app\oracle\product\11.2.0\server\jdbc\lib)
Mysql (https://dev.mysql.com/downloads/connector/j/)

강사PC : D:\Duzon\DataBase\Connect Utils\OracleJDBC >> ojdbc6.jar

3. 드라이버를 참조 (현재 프로젝트에서 사용) >> Java Project -> 속성 -> build path ->
jar 파일 추가 
3.1 드라이버 사용 준비 완료 >> 메모리에 load 사용 ....
3.2 Class.forName("oracle.jdbc.OracleDriver")..... new 객체 생성 ....

4. JAVA CODE (CRUD) >> JDBC API 제공 받는다
4.1 import java.sql.* >> interface , class 제공
4.2 개발자는 interface 를 통해서 표준화된 DB 작업 수행

POINT) why interface형태로 제공 >> JDBC API >> Oracle, Mysql , ....)

//OracleConnection >> Connection 구현
//MysqlConnection >> Connection 구현 
//다형성 Connection 부모타입 : 유연한 프로그래밍 작성 

>>다형성을 구현 (인터페이스 활용)

import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement 등등 

5. 작업순서
5.1 DB연결 -> 명령생성 -> 명령실행 -> 처리 -> 자원해제
5.1 명령 : DDL (create , alter , drop)

CRUD (insert , select , update , delete)

실행 : 쿼리문을 DB서버에게 전달 
처리 : 결과를 받아서 화면 출력 , 또는 다른 프로그램에 전달 등등
자원해제 : 연결해제 

*/
public class Ex01_Oracle_Connection {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		Class.forName("oracle.jdbc.OracleDriver");//드라이버 로딩
		System.out.println("오라클 드라이버 로딩...");
		
		//로딩된 드라이버를 통해서 오라클 서버에 연결
		//약속된 코드, 오라클 커넥션 객체가 넘어온다. 어쨌든 그것의 부모는 Connection이란 인터페이스이므로 DB가 바뀌어도 똑같다.
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "KOSA", "1004");
		System.out.println(conn.isClosed()+" DB 연결이 됐니? 아니! false가 나오면 연결 성공");
		
		//명령객체
		Statement stmt = conn.createStatement();
		
		//명령객체를 통한 CRUD 구현
		String sql = "select empno, ename, sal from emp";
		
		//executeQuery -> select한 결과를 return, 따라서 ResultSet
		ResultSet rs = stmt.executeQuery(sql); //명령어 실행 (select : insert, update, delete)
		
		/////////////////////////////
		ResultSetMetaData rsmd = rs.getMetaData(); //드라이버 안에 있는 resultSet 안에 이 함수를 사용하면 스키마 정보 볼 수 있음
		//스키마 정보 또는 메타데이터 정보를 확인할 수 있다.
		System.out.println("Total columns : "+rsmd.getColumnCount());//3
		System.out.println("Column Name of 1st column : " + rsmd.getColumnName(1));//컬럼의 인덱스는 1부터 시작, 첫번째 컬럼 이름 : EMPNO
		System.out.println("Column Type Name of 1st column : "+rsmd.getColumnTypeName(1));//첫 번째 컬럼의 타입 : NUMBER
		//////////////////////////////
		
		//실행 처리
		while(rs.next()) {
			System.out.println(rs.getInt("empno") + "/" + rs.getString("ename") + "/" + rs.getInt("sal"));
		}
		
		//자원해제
		stmt.close();
		rs.close();
		conn.close();
		
		System.out.println("DB연결 : "+conn.isClosed());//db연결 끊겼는지 확인해보기, 끊겼으므로 true값 나옴
		
	}

}
