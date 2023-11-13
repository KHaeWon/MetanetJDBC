import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*
JDBC 작업

1. select
1.1 결과 집합 생성


2. insert , update , delete
2.1 결과 집합 생성 없어요(x) >> resultSet (x)
2.2 반영결과 return (반영된 행의 수 ) >> update  5건 수정 >> return 5;

ex)
update emp set sal=0; >> 실행 >> update 14건 >> return 14
update emp set sal=100 where empno=9999 >> update 0건 >> return 0

결과를 받아서 자바코드 로직처리

Today key Point
1. Oracle DML (developer , Cmd (sqlplus), Tool) 작업 commit or rollback  강제
2. JDBC API 사용해서 Oracle DML 작업을 수행하면 >> default >> auto commit >> 실반영
3. JDBC API 사용해서 JAVA 코드에서 delete from emp 실행 >> JDBC 자동 commit >> 실반영
4. 그래서 JDBC API 옵션을 통해서 commit , rollback  강제하는 방법 제공

begin
	A계좌 인출 (update...
	
	B계좌 입금 (update ...
end
논리적인 단위로 transaction
전체 성공 아니면 실패
commit or rollback

>>업무 처리 >> JDBC >> autocommit 옵션 >> false 전환
>>반드시  java code에서 commit , rollback 강제 


실습쿼리 ^^
create table dmlemp
as
  select * from emp;
  
select * from user_constraints where table_name='DMLEMP';

alter table dmlemp
add constraint pk_dmlemp_empno primary key(empno);

select * from dmlemp;

*/
public class Ex02_Oracle_DML_Insert {

	public static void main(String[] args) {

		Connection conn = null;
		Statement stmt = null;
		
		try {
			//드라이버 로딩 코드 생략해도 ㄱㅊ아서 생략
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "KOSA", "1004");
			stmt = conn.createStatement(); //명령객체 얻어내기
			
			//insert
			String sql = "insert into dmlemp(empno,ename,deptno) values (100,'홍길동',10)";
			//update >> 결과집합이 필요 없으므로
			int rowcount = stmt.executeUpdate(sql);
			//실반영(commit)
			if(rowcount>0) {
				System.out.println("반영된 행의 수 : "+rowcount);//리턴된 rowcount로 반영된 행의 수를 알 수 있음
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}
