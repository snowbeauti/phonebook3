package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.PersonVo;


public class PhoneDao {

	// 0. import java.sql.*;
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	private String drive = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "phonedb";
	private String pw = "phonedb";

	// DB입력
	private void getConnection() {

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(drive);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

			System.out.println("[접속성공]");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	private void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// 저장
	public int personInsert(PersonVo personVo) {
		int count = 0;

		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " insert into person values(seq_person_id.nextval, ?, ?, ?) ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, personVo.getName());
			pstmt.setString(2, personVo.getHp());
			pstmt.setString(3, personVo.getCompany());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 저장되었습니다.");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		// 자원정리
		close();

		return count;
	}

	//수정
	public int personUpdate(PersonVo personVo) {

		getConnection();
		int count = 0;

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " UPDATE person ";
			query += " set name = ?, ";
			query += "        hp = ?, ";
			query += "        company = ? ";
			query += " where person_id = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, personVo.getName());
			pstmt.setString(2, personVo.getHp());
			pstmt.setString(3, personVo.getCompany());
			pstmt.setInt(4, personVo.getPersonId());

			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 수정되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		// 자원정리
		close();

		return count;
	}
	
	//사람 1명 가져오기
	
	public PersonVo getPerson(int personId) {
		PersonVo pvo = null;
		getConnection();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " SELECT person_id, ";
			query += "        name, ";
			query += "        hp, ";
			query += "        company ";
			query += " FROM person ";
			query += " where person_id = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, personId);
			
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			
			while(rs.next()) {
				int personID = rs.getInt("person_id");
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");
				
				pvo = new PersonVo(personID, name, hp, company);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		// 자원정리
		close();
		return pvo;
	}
	
	//삭제
	public int PersonDelete(int personId) {

		getConnection();
		int count = 0;

		try {// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " delete from person ";
			query += " where person_id =? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, personId);

			count = pstmt.executeUpdate();
			// 4.결과처리
			System.out.println(count + "건 삭제되었습니다.");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}

	// 조회
	public List<PersonVo> getPersonList() {

		List<PersonVo> personList = new ArrayList<PersonVo>();

		getConnection();

		try {// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " SELECT person_id, ";
			query += "        name, ";
			query += "        hp, ";
			query += "        company ";
			query += " FROM person ";

			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int personId = rs.getInt("person_id");
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");

				PersonVo vo = new PersonVo(personId, name, hp, company);
				personList.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return personList;

	}
	
	//검색
	public void personSearch(String search) {
	
		List<PersonVo> personList = new ArrayList<PersonVo>();

		getConnection();
		
	try {// 3. SQL문 준비 / 바인딩 / 실행
	    String query = "";
	    query += " SELECT person_id, ";
	    query += "        name, ";
	    query += "        hp, ";
	    query += "        company ";
	    query += " FROM person ";
	    query += " where name like ? ";
	    query += " or hp like ? ";
	    query += " or company like ? ";
	    
	    
	    pstmt = conn.prepareStatement(query);
	    
	    String word = "%" + search + "%";
	    pstmt.setString(1, word);
	    pstmt.setString(2, word);
	    pstmt.setString(3, word);
	    rs = pstmt.executeQuery();
	    
	    while(rs.next()) {
	    	int personId = rs.getInt("person_id");
	    	String name = rs.getString("name");
	    	String hp = rs.getString("hp");
	    	String company = rs.getString("company");
	    	
	    	System.out.println(personId + ".\t" + name + "\t" + hp + "\t" + company);
	    }
	    
	     // 4.결과처리

	
	
} catch (SQLException e) {
    System.out.println("error:" + e);
}

}
	

}
