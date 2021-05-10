package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeByProdAction extends Action {//구매상태코드 수정요청 (인자: prodNo)

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< UpdateTranCodeByProdAction : execute() 시작 >>>>>");
		System.out.println("prodNo 는? " + request.getParameter("prodNo"));
		System.out.println("tranCode 는? " + request.getParameter("tranCode"));
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		
		//listProduct.jsp에서 배송하기 버튼 클릭 시 'prodNo' 가져옴
		//updateTranCode()에서 'tranNo'가 필요하므로 getPurchase2() 실행
		Purchase purchase = purchaseService.getPurchase2(Integer.parseInt(request.getParameter("prodNo")));
		System.out.println("받은 tranNo : " + purchase.getTranNo());	
		
		//listProduct.jsp에서 배송하기 버튼 클릭 시 'tranCode = 2' 가져옴 ==> 배송중 상태
		purchase.setTranCode(request.getParameter("tranCode"));
		System.out.println("받은 tranCode : " + purchase.getTranCode());
		
		purchaseService.updateTranCode(purchase);
		
		System.out.println("<<<<< UpdateTranCodeByProdAction : execute() 종료 >>>>>");
		
		return "forward:/listSale.do?menu=manage&page="+request.getParameter("page");
		
	}
}
