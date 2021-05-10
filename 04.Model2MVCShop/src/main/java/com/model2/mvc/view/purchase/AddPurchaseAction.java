package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class AddPurchaseAction extends Action {//구매 요청

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< AddPurchaseAction : execute() 시작 >>>>>");
		
		//addPurchaseView.jsp에서 가져온 값을 DB에 쉽게 전달하기 위해 Purchase 인스턴스를 생성하여, 가져온 값을 저장
		Purchase purchase = new Purchase();
		purchase.setPaymentOption(request.getParameter("paymentOption")); //구매방법
		purchase.setReceiverName(request.getParameter("receiverName"));   //구매자이름
		purchase.setReceiverPhone(request.getParameter("receiverPhone")); //구매자연락처
		purchase.setDivyAddr(request.getParameter("receiverAddr"));       //구매자주소
		purchase.setDivyRequest(request.getParameter("receiverRequest")); //구매요청사항
		purchase.setDivyDate(request.getParameter("receiverDate"));       //배송희망일자
		purchase.setTranCode("1");
		
		//2. Product의 값을 Purchase에 저장
		//방법1)
		ProductService productService = new ProductServiceImpl();
		Product product = productService.getProduct((Integer.parseInt(request.getParameter("prodNo"))));
		purchase.setPurchaseProd(product);                                //상품정보
		//방법2)
//		Product product = new Product();
//		product.setProdNo(Integer.parseInt(request.getParameter("prodNo"))); 
//		purchase.setPurchaseProd(product);
		
		//3. User의 값을 Purchase에 저장
		//방법1)
		HttpSession session = request.getSession();
		purchase.setBuyer((User)session.getAttribute("user"));		      //구매자아이디
		//방법2)
//		User user = new User();
//		user.setUserId(request.getParameter("userId"));
//		purchase.setBuyer(user);	
		
		System.out.println("purchase 셋팅완료 : " + purchase);
		
		//purchaseVO를 DB에 저장하기 위해 PurchaseServiceImpl 인스턴스 생성
		PurchaseService purchaseService = new PurchaseServiceImpl();
		purchaseService.addPurchase(purchase);
		
		request.setAttribute("purchase", purchase);
		
		System.out.println("<<<<< AddPurchaseAction : execute() 종료 >>>>>");
		
		return "forward:/purchase/addPurchase.jsp";		
	}
}
