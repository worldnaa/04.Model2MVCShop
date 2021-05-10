package com.model2.mvc.view.product;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class GetProductAction extends Action {//상품상세보기 요청

	//1. 판매상품관리 or 상품검색 메뉴 클릭
	//2. 판매상품관리의 상품명 ==> http://192.168.0.96:8080/getProduct.do?prodNo=prodNo&menu=manage 이동
	//3. 상품검색의 상품명    ==> http://192.168.0.96:8080/getProduct.do?prodNo=prodNo&menu=search 이동
	//4. GetProductAction.java의 execute() 실행
	//5. menu=manage 일 경우 ==> UpdateProductViewAction.java 이동
	//6. menu=search 일 경우 ==> getProduct.jsp 이동
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< GetProductAction : execute() 시작 >>>>>");
		
		String history = null;
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length > 0) {
			for(int i=0; i<cookies.length; i++) {
				Cookie cookie = cookies[i];
				if(cookie.getName().equals("history")) {
					history = cookie.getValue();
					history += "," + request.getParameter("prodNo");
				}
			}
		}
		
		//쿠키 생성
		Cookie cookie = new Cookie("history", history);
		//음수값은 쿠키가 영구적으로 저장되지 않고, 웹브라우저가 종료될 때 삭제
		cookie.setMaxAge(-1);
		//response에 지정된 쿠키를 추가
		response.addCookie(cookie);
		System.out.println("저장된 쿠키 이름 : " + cookie.getName());
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		System.out.println("받은 prodNo : " + prodNo);
		
		String menu = request.getParameter("menu");
		System.out.println("받은 menu : " + menu);
		
		//menu의 값이 manage일 경우
		if(menu != null && menu.equals("manage")) {
			return "forward:/updateProductView.do?prodNo="+prodNo+"&menu="+menu;
		}
		
		ProductService service = new ProductServiceImpl();
		Product product = service.getProduct(prodNo);
		System.out.println("product 셋팅완료 : " + product);
		
		request.setAttribute("product", product);
		
		System.out.println("<<<<< GetProductAction : execute() 종료 >>>>>");
		
		return "forward:/product/getProduct.jsp";
		
	}
}
