package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import dto.Member;
import dto.Order;
import dto.Product;

public class OrderDao {
	/* DB접속 - Connection */
	private Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			con = DriverManager.getConnection
					("jdbc:oracle:thin:@//localhost:1521/xe", "JDBC_DBA", "1111");
			//System.out.println("DB접속");
		} catch (ClassNotFoundException e) {
			/* ojdbc */
			System.out.println("드라이버 예외");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("DB 접속 예외");
			e.printStackTrace();
		}
		return con;
	}
	
	/* SELECT - 로그인 정보 조회 */
	public Member selectMemberInfo(String inputMid, String inputMpw) {
		Member loginMember = null;
		/* 0. DB 접속 */
		Connection con = getConnection();
		if(con == null) {
			return loginMember;
		}
		/* 1. 쿼리문 작성 */
		String sql = "SELECT * FROM MEMBERS "
				   + " WHERE MID = ? ";
		if(inputMpw != null) {
			sql += "AND MPW = ?";
		}
		try {
			/* 2. 쿼리문 준비 */
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, inputMid);
			if(inputMpw != null) {
				pstmt.setString(2, inputMpw);
			}
			/* 3. 쿼리문 실행 및 결과 반환 */
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				loginMember = new Member();
				loginMember.setMid( rs.getString("mid") );
				loginMember.setMpw(rs.getString("mpw"));
				loginMember.setMname(rs.getString("mname"));
				loginMember.setMbirth(rs.getString("mbirth"));
				loginMember.setMdate(rs.getString("mdate"));
				loginMember.setMgrade(rs.getString("mgrade"));
				loginMember.setMstate( rs.getString("mstate") );
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return loginMember;
	}

	public ArrayList<Member> selectMemberList() {
		ArrayList<Member> memberList = new ArrayList<Member>();
		Connection con = getConnection();
		String sql = "SELECT * FROM MEMBERS";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Member member = new Member();
				member.setMid(rs.getString("mid"));
				member.setMpw(rs.getString("mpw"));
				member.setMname(rs.getString("mname"));
				member.setMbirth(rs.getString("mbirth"));
				member.setMdate(rs.getString("mdate"));
				member.setMstate(rs.getString("mstate"));
				memberList.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return memberList;
	}

	public int insertMemberJoin(Member joinInfo) {
		int result = 0;
		/* 0. DB 접속 */
		Connection con = getConnection();
		if(con == null) {
			return result;
		}
		/* 1. 쿼리문 작성 */
		String sql = "INSERT INTO MEMBERS(MID, MPW, MNAME, MBIRTH, MDATE, MSTATE ) "
				   + " VALUES(?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), SYSDATE, '사용' )";
		try {
			/* 2. 쿼리문 준비 */
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, joinInfo.getMid());
			pstmt.setString(2, joinInfo.getMpw());
			pstmt.setString(3, joinInfo.getMname());
			pstmt.setString(4, joinInfo.getMbirth());
			result = pstmt.executeUpdate();
			/* 3. 쿼리문 실행 및 결과 반환 */
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<Product> selectProductList() {
		ArrayList<Product> prList = new ArrayList<Product>();
		/* 0. DB 접속*/
		Connection con = getConnection();
		/* 1. 쿼리문 작성 */
		String sql = "SELECT * FROM PRODUCTS ";
		try {
			/* 2. 쿼리문 준비 */
			PreparedStatement pstmt = con.prepareStatement(sql);
			/* 3. 쿼리문 실행 및 결과반환 */
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Product pr = new Product();
				pr.setPrcode( rs.getString("prcode") );
				pr.setPrname( rs.getString("prname") );
				pr.setPrprice( rs.getInt("prprice") );
				pr.setPrstock( rs.getInt("prstock") );
				pr.setPrcom( rs.getString("prcom") );
				pr.setPrtype( rs.getString("prtype") );
				prList.add(pr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prList;
	}

	public String selectMaxOdcode() {
		String maxCode = null;
		Connection con = getConnection();
		if(con == null) {
			return maxCode;
		}
		String sql = "SELECT NVL( MAX(ODCODE), 'O0000' ) FROM ORDERS ";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				maxCode = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return maxCode;
	}

	public int insertOrderInfo(Order odInfo) {
		int result = 0;
		Connection con = getConnection();
		String updateSql = "UPDATE PRODUCTS "
				         + " SET PRSTOCK = PRSTOCK - ? "
				         + " WHERE PRCODE = ? ";
		
		String insertSql = "INSERT INTO ORDERS( ODCODE, MID, PRCODE, ODCOUNT, ODDATE ) "
				         + " VALUES( ?, ?, ?, ?, SYSDATE )";
		
		try {
			con.setAutoCommit(false);// 수동 커밋 변경
			PreparedStatement pstmt = con.prepareStatement(updateSql);
			pstmt.setInt(1, odInfo.getOdcount());
			pstmt.setString(2, odInfo.getPrcode());
			result = pstmt.executeUpdate(); // 성공 : 1, 실패 : 0
			
			pstmt = con.prepareStatement(insertSql);
			pstmt.setString(1, odInfo.getOdcode());
			pstmt.setString(2, odInfo.getMid());
			pstmt.setString(3, odInfo.getPrcode());
			pstmt.setInt(4, odInfo.getOdcount());
			result += pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if(result == 2) {
				con.commit();
			} else {
				con.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<HashMap<String, String>> selectOrderList(String loginId) {
		ArrayList<HashMap<String, String>> orderList = new ArrayList<HashMap<String, String>>();
		Connection con = getConnection();
		// 상품이름  상품가격  주문수량  주문금액  주문일
		String sql = "SELECT PR.PRCODE, OD.ODCODE, PR.PRNAME, PR.PRPRICE, "
				+ "        OD.ODCOUNT, (PR.PRPRICE * OD.ODCOUNT) AS ORDERPRICE, OD.ODDATE "
				+ " FROM ORDERS OD "
				+ "  INNER JOIN PRODUCTS PR"
				+ "  ON OD.PRCODE = PR.PRCODE "
				+ " WHERE OD.MID = ? "
				+ " ORDER BY OD.ODDATE DESC ";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, loginId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				HashMap<String, String> odInfo = new HashMap<String, String>();
				odInfo.put("prcode", rs.getString("PRCODE"));
				odInfo.put("odcode", rs.getString("ODCODE"));
				odInfo.put("prname", rs.getString("PRNAME"));
				odInfo.put("prprice", rs.getString("PRPRICE"));
				odInfo.put("odcount", rs.getString("ODCOUNT"));
				odInfo.put("orderprice", rs.getString("ORDERPRICE"));
				odInfo.put("oddate", rs.getString("ODDATE"));
				orderList.add(odInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderList;
	}

	public int deleteOrder(String odcode, String prcode, int odcount) {
		int result = 0;
		Connection con = getConnection();
		String deleteSql = "DELETE FROM ORDERS "
				         + " WHERE ODCODE = ? ";
		
		String updateSql = "UPDATE PRODUCTS "
				         + " SET PRSTOCK = PRSTOCK + ? "
				         + " WHERE PRCODE = ? ";
		try {
			con.setAutoCommit(false);// 수동 커밋 변경
			PreparedStatement pstmt = con.prepareStatement(deleteSql);
			pstmt.setString(1, odcode);
			result = pstmt.executeUpdate(); // 성공 : 1, 실패 : 0
			
			pstmt = con.prepareStatement(updateSql);
			pstmt.setInt(1, odcount);
			pstmt.setString(2, prcode);

			result += pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if(result == 2) {
				con.commit();
			} else {
				con.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public HashMap<String, String> selectMember(String loginId) {
		HashMap<String, String> memInfo = null; // 조회된 정보를 저장할 변수
		/* 0. DB접속 */
		Connection con = getConnection();
		/* 1. 쿼리문 작성*/
		String sql = "SELECT M.MID, M.MPW, M.MNAME, TO_CHAR(M.MBIRTH, 'YYYY-MM-DD') AS MBIRTH,"
				+ "          M.MDATE, M.MGRADE, M.MSTATE, OD.* "
				+ " FROM MEMBERS M "
				+ "  LEFT OUTER JOIN (SELECT OD.MID, COUNT(*) AS TOTALORDERS, "
				+ "                          SUM( OD.ODCOUNT * PR.PRPRICE ) AS TOTALPRICE "
				+ "                   FROM ORDERS OD "
				+ "                     INNER JOIN PRODUCTS PR "
				+ "                     ON OD.PRCODE = PR.PRCODE "
				+ "                   GROUP BY OD.MID) OD "
				+ "  ON M.MID = OD.MID "
				+ " WHERE M.MID = ? ";
		/* 2. 쿼리문 준비 */
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, loginId);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				memInfo = new HashMap<String, String>();
				memInfo.put("mid", rs.getString("mid"));
				memInfo.put("mpw", rs.getString("mpw"));
				memInfo.put("mname", rs.getString("mname"));
				memInfo.put("mbirth", rs.getString("mbirth"));
				memInfo.put("mdate", rs.getString("mdate"));
				memInfo.put("mgrade", rs.getString("mgrade"));
				memInfo.put("mstate", rs.getString("mstate"));
				memInfo.put("totalprice", rs.getString("totalprice"));
				memInfo.put("totalorders", rs.getString("totalorders"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return memInfo;
	}

}











