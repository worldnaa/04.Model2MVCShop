package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeAction extends Action {//구매상태코드 수정요청 (인자: tranNo)

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< UpdateTranCodeAction : execute() 시작 >>>>>");
		
		Purchase purchase = new Purchase();
		
		//listPurchase.jsp에서 물건도착 버튼 클릭 시 'tranNo' 가져옴
		purchase.setTranNo(Integer.parseInt(request.getParameter("tranNo")));
		System.out.println("받은 tranNo : " + purchase.getTranNo());	
		
		//listPurchase.jsp에서 물건도착 버튼 클릭 시 'tranCode = 3' 가져옴 ==> 배송완료 상태
		purchase.setTranCode(request.getParameter("tranCode")); 
		System.out.println("받은 tranCode : " + purchase.getTranCode());
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		purchaseService.updateTranCode(purchase);
		
		System.out.println("<<<<< UpdateTranCodeAction : execute() 종료 >>>>>");
		
		return "redirect:/listPurchase.do?menu=manage&page="+request.getParameter("page");
	}

}
