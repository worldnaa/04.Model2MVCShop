package com.model2.mvc.service.purchase.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDao;

public class PurchaseServiceImpl implements PurchaseService {
	//Field
	private PurchaseDao purchaseDao;
	
	//Constructor
	public PurchaseServiceImpl() {
		purchaseDao = new PurchaseDao();
	}

	//Method
	//구매를 위한 비즈니스를 수행
	public void addPurchase(Purchase purchase) throws Exception {
		System.out.println("<<<<< PurchaseServiceImpl : addPurchase() 실행 >>>>>");
		purchaseDao.insertPurchase(purchase);
	}

	//구매 정보 상세조회를 위한 비즈니스를 수행
	public Purchase getPurchase(int tranNo) throws Exception {
		System.out.println("<<<<< PurchaseServiceImpl : getPurchase() 실행 >>>>>");
		return purchaseDao.findPurchase(tranNo);
	}
	
	//물건 재고 여부를 파악하기 위한 비즈니스를 수행
	public Purchase getPurchase2(int ProdNo) throws Exception {
		System.out.println("<<<<< PurchaseServiceImpl : getPurchase2() 실행 >>>>>");
		return purchaseDao.findPurchase2(ProdNo);
	}

	//구매 목록 보기를 위한 비즈니스를 수행
	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
		System.out.println("<<<<< PurchaseServiceImpl : getPurchaseList() 실행 >>>>>");
		return purchaseDao.getPurchaseList(search, buyerId);
	}
	
	//판매 목록 보기를 위한 비즈니스를 수행
	public Map<String, Object> getSaleList(Search search) throws Exception {
		System.out.println("<<<<< PurchaseServiceImpl : getSaleList() 실행 >>>>>");
		return purchaseDao.getSaleList(search);
	}
	
	//구매 정보 수정을 위한 비즈니스 수행
	public void updatePurcahse(Purchase purchase) throws Exception {
		System.out.println("<<<<< PurchaseServiceImpl : updatePurcahse() 실행 >>>>>");
		purchaseDao.updatePurchase(purchase);
	}

	//구매 상태 코드 수정을 위한 비즈니스 수행
	public void updateTranCode(Purchase purchase) throws Exception {
		System.out.println("<<<<< PurchaseServiceImpl : updateTranCode() 실행 >>>>>");
		purchaseDao.updateTranCode(purchase);
	}
	
	
	
	
	
}//end of class
