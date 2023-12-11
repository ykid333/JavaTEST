package service;

import java.util.ArrayList;
import java.util.HashMap;

import dao.OrderDao;
import dto.Member;
import dto.Order;
import dto.Product;


public class MainService {
	
	private OrderDao odao = new OrderDao();

	public Member memberLogin(String inputMid, String inputMpw) {
		System.out.println("[SERVICE - 로그인 회원정보 조회]");
		/* 1. 회원정보 조회 및 반환*/
		return odao.selectMemberInfo(inputMid, inputMpw);
	}

	public ArrayList<Product> getProductList() {
		System.out.println("[SERVICE - 상품목록 조회]");
		return odao.selectProductList();
	}


	public int orderProduct(Order od) {
		System.out.println("[SERVICE - 상품주문]");
		String maxOdcode = odao.selectMaxOdcode(); // 주문 코드 최대값 조회
		if(maxOdcode == null) {
			return 0;
		}
		String strCode = maxOdcode.substring(0,1); // 문자코드
		int numCode = Integer.parseInt( maxOdcode.substring(1) ) + 1; // 숫자 코드 + 1
		String odcode = strCode + String.format("%04d", numCode) ;
		od.setOdcode(odcode); // 생성된 주문코드 저장
		
		return odao.insertOrderInfo(od);
	}

	public ArrayList<HashMap<String, String>> getOrderList(String loginId) {
		// TODO Auto-generated method stub
		return odao.selectOrderList(loginId);
	}

	public int cancelOrder(String cancelOdcode, String cancelPrcode, int cancelOdcount) {
		return odao.deleteOrder(cancelOdcode, cancelPrcode, cancelOdcount);
	}

	public int memberJoin(Member joinMember) {
		return odao.insertMemberJoin(joinMember);
	}

	public HashMap<String, String> memberInfo(String loginId) {
		
		return odao.selectMember(loginId);
	}

}
