package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.Member;
import dto.Order;
import dto.Product;
import service.MainService;

/**
 * Servlet implementation class MainController
 */
@WebServlet( { "/home","/memberLoginForm","/memberJoinForm","/idCheck" ,"/memberJoin","/memberLogin", "/myInfo","/orderPage", "/order","/memberLogout", "/orderList","/cancelOrder" })
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String path = request.getContextPath();
		String url = request.getServletPath();
		System.out.println("요청URL : " + url);
		MainService msvc = new MainService();
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher;
		String redirectPage = "";
		String sendMsg = "";
		String afterUrl = "";
		String loginId = (String)session.getAttribute("loginId");
		switch(url) {
		case "/home":
			response.sendRedirect(path+"/Home.jsp");
			break;
		case "/memberLoginForm":
			System.out.println("로그인페이지 이동요청");
			response.sendRedirect(path+"/LoginForm.jsp");
			break;
		case "/memberJoinForm":
			System.out.println("회원가입페이지 이동요청");
			response.sendRedirect(path+"/JoinForm.jsp");
			break;
		case "/idCheck":
			System.out.println("아이디 중복 확인 요청");
			String inputId = request.getParameter("inputId");
			Member checkInfo = msvc.memberLogin(inputId, null);
			if(checkInfo == null) {
				response.getWriter().print("Y");
			} else {
				response.getWriter().print("N");
			}
			break;
		case "/memberJoin":
			System.out.println("회원가입요청");
			Member joinMember = new Member();
			joinMember.setMid( request.getParameter("mid") );
			joinMember.setMpw( request.getParameter("mpw") );
			joinMember.setMname( request.getParameter("mname") );
			joinMember.setMbirth( request.getParameter("mbirth") );
			int joinResult = msvc.memberJoin(joinMember);
			if(joinResult > 0) {
				redirectPage = "/Success.jsp";
				sendMsg = "회원가입 되었습니다.";
				afterUrl = "/home";
			} else {
				redirectPage = "/Fail.jsp";
				sendMsg = "회원가입에 실패 하였습니다.";
			}
			response.sendRedirect(path+redirectPage
					+"?msg="+ URLEncoder.encode(sendMsg, "UTF-8") 
					+"&afterUrl="+ URLEncoder.encode(afterUrl, "UTF-8"));
			break;	
		case "/memberLogin":
			System.out.println("로그인처리 요청");
			String inputMid = request.getParameter("mid");
			String inputMpw = request.getParameter("mpw");
			System.out.println("입력한 아이디 : " + inputMid);
			System.out.println("입력한 비밀번호 : " + inputMpw);
			Member loginInfo = msvc.memberLogin(inputMid, inputMpw);

			if(loginInfo == null) {
				// 로그인 실패 아이디 또는 비밀번호 가 일치하지 않음
				redirectPage = "/Fail.jsp";
				sendMsg = "아이디 또는 비밀번호 가 일치하지 않습니다.";
			} else {
				if(loginInfo.getMstate().equals("사용")) {
					redirectPage = "/Success.jsp";
					sendMsg = "로그인 되었습니다.";
					afterUrl = "/home";
					session.setAttribute("loginId", loginInfo.getMid());
				} else {
					redirectPage = "/Fail.jsp";
					sendMsg = "이용 정지된 계정입니다.";
				}
			}
			response.sendRedirect(path+redirectPage
					+"?msg="+ URLEncoder.encode(sendMsg, "UTF-8") 
					+"&afterUrl="+ URLEncoder.encode(afterUrl, "UTF-8"));
			break;
		case "/myInfo":
			System.out.println("내 정보 페이지 이동요청");
			if(loginId == null) {
				response.sendRedirect(path+"/Success.jsp"
						+"?msg="+ URLEncoder.encode("로그인 후 이용가능합니다.", "UTF-8") 
						+"&afterUrl="+ URLEncoder.encode("/memberLoginForm", "UTF-8")); 
				break;
			}
			
			HashMap<String, String> myInfo = msvc.memberInfo(loginId);
			request.setAttribute("myInfo", myInfo);
			dispatcher = request.getRequestDispatcher("MyInfo.jsp");
			dispatcher.forward(request, response);
			break;
		case "/orderPage":
			if(loginId == null) {
				response.sendRedirect(path+"/Success.jsp"
						+"?msg="+ URLEncoder.encode("로그인 후 이용가능합니다.", "UTF-8") 
						+"&afterUrl="+ URLEncoder.encode("/memberLoginForm", "UTF-8")); 
				break;
			}
			
			System.out.println("상품주문페이지 이동요청");
			ArrayList<Product> prList = msvc.getProductList();
			request.setAttribute("prList", prList);
			dispatcher = request.getRequestDispatcher("OrderPage.jsp");
			dispatcher.forward(request, response);
			break;
			
		case "/order":
			if(loginId == null) {
				response.sendRedirect(path+"/Success.jsp"
						+"?msg="+ URLEncoder.encode("로그인 후 이용가능합니다.", "UTF-8") 
						+"&afterUrl="+ URLEncoder.encode("/memberLoginForm", "UTF-8")); 
				break;
			}
			System.out.println("상품 주문 처리 요청");
			Order od = new Order();
			od.setPrcode(request.getParameter("prcode"));
			int odcount = Integer.parseInt( request.getParameter("odcount") );
			od.setOdcount(odcount);
			
			od.setMid(loginId);
			int orderResult = msvc.orderProduct(od);
			if(orderResult > 0) {
				redirectPage = "/Success.jsp";
				sendMsg = "상품이 주문 되었습니다.";
				afterUrl = "/orderList";
			} else {
				redirectPage = "/Fail.jsp";
				sendMsg = "상품 주문에 실패하였습니다.";
			}
			response.sendRedirect(path+redirectPage
					+"?msg="+ URLEncoder.encode(sendMsg, "UTF-8") 
					+"&afterUrl="+ URLEncoder.encode(afterUrl, "UTF-8"));
			break;
		case "/memberLogout":
			session.removeAttribute("loginId");
			response.sendRedirect(path+"/Success.jsp"
					+"?msg="+ URLEncoder.encode("로그아웃 되었습니다.", "UTF-8") 
					+"&afterUrl="+ URLEncoder.encode("/home", "UTF-8"));
			break;
		case "/orderList":
			if(loginId == null) {
				response.sendRedirect(path+"/Success.jsp"
						+"?msg="+ URLEncoder.encode("로그인 후 이용가능합니다.", "UTF-8") 
						+"&afterUrl="+ URLEncoder.encode("/memberLoginForm", "UTF-8")); 
				break;
			}
			System.out.println("주문내역 페이지 이동요청");
			ArrayList< HashMap<String, String> > orderList
				= msvc.getOrderList(loginId);
			request.setAttribute("odList", orderList);
			dispatcher = request.getRequestDispatcher("OrderList.jsp");
			dispatcher.forward(request, response);
			break;
			
		case "/cancelOrder":
			System.out.println("주문 취소 처리 요청");
			String cancelOdcode = request.getParameter("odcode");
			String cancelPrcode = request.getParameter("prcode");
			int cancelOdcount = Integer.parseInt( request.getParameter("odcount") );
			int cancelResult =  msvc.cancelOrder(cancelOdcode, cancelPrcode, cancelOdcount);
			if( cancelResult == 2 ) {
				redirectPage = "/Success.jsp";
				sendMsg = "주문이 취소 되었습니다.";
				afterUrl = "/orderList";
			} else {
				redirectPage = "/Fail.jsp";
				sendMsg = "주문 취소에 실패하였습니다.";
			}
			response.sendRedirect(path+"/Success.jsp"
					+"?msg="+ URLEncoder.encode(sendMsg, "UTF-8") 
					+"&afterUrl="+ URLEncoder.encode(afterUrl, "UTF-8") );
			break;
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
