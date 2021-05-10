package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class PurchaseDao {
	//Constructor
	public PurchaseDao() {
	}
	
	//Method
	//구매를 위한 DBMS를 수행
	public void insertPurchase(Purchase purchase) throws Exception {	
		System.out.println("<<<<< PurchaseDAO : insertPurchase() 시작 >>>>>");
		System.out.println("받은 purchase : " + purchase);
		
		Connection con = DBUtil.getConnection();
		
		String sql = "INSERT INTO transaction VALUES (seq_product_prod_no.nextval,?,?,?,?,?,?,?,?,sysdate,?)";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, purchase.getPurchaseProd().getProdNo());
		pStmt.setString(2, purchase.getBuyer().getUserId());
		pStmt.setString(3, purchase.getPaymentOption());
		pStmt.setString(4, purchase.getReceiverName());
		pStmt.setString(5, purchase.getReceiverPhone());
		pStmt.setString(6, purchase.getDivyAddr());
		pStmt.setString(7, purchase.getDivyRequest());
		pStmt.setString(8, purchase.getTranCode());
		pStmt.setString(9, purchase.getDivyDate());
		pStmt.executeUpdate();
		System.out.println("insert 완료 : " + sql);
		
		pStmt.close();
		con.close();
		
		System.out.println("<<<<< PurchaseDAO : insertProduct() 종료 >>>>>");
	}
	
	
	//구매정보 상세 조회를 위한 DBMS를 수행 ==> tranNo
	public Purchase findPurchase(int tranNo) throws Exception {
		System.out.println("<<<<< PurchaseDAO : findPurchase() 시작 >>>>>");
		System.out.println("받은 tranNo : " + tranNo);
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM transaction WHERE tran_no=? ";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, tranNo);
		ResultSet rs = pStmt.executeQuery();
		System.out.println("sql 전송완료 : " + sql);
		
		Purchase purchase = null;
		Product product = null;
		User user = null;
		
		while (rs.next()) {	
			purchase = new Purchase();
			product = new Product();
			user = new User();
			
			purchase.setPaymentOption(rs.getString("payment_option"));
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setDivyAddr(rs.getString("demailaddr"));
			purchase.setDivyRequest(rs.getString("dlvy_request"));
			purchase.setDivyDate(rs.getString("dlvy_date"));
			purchase.setOrderDate(rs.getDate("order_data"));
			purchase.setTranNo(rs.getInt("tran_no"));
			purchase.setTranCode(rs.getString("tran_status_code"));
			
			product.setProdNo(rs.getInt("prod_no"));
			purchase.setPurchaseProd(product);
			
			user.setUserId(rs.getString("buyer_id"));
			purchase.setBuyer(user);
			
			System.out.println("purchase 셋팅완료 : " + purchase);
		}
		
		rs.close();
		pStmt.close();
		con.close();
		
		System.out.println("<<<<< PurchaseDAO : findPurchase() 종료 >>>>>");
		
		return purchase; 
	}
	
	
	//구매정보 상세 조회를 위한 DBMS를 수행 ==> prodNo
	public Purchase findPurchase2(int prodNo) throws Exception {
		System.out.println("<<<<< PurchaseDAO : findPurchase2() 시작 >>>>>");
		System.out.println("받은 prodNo : " + prodNo);
			
		Connection con = DBUtil.getConnection();
			
		String sql = "SELECT * FROM transaction WHERE prod_no=? ";
			
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, prodNo);
		ResultSet rs = pStmt.executeQuery();
		System.out.println("sql 전송완료 : " + sql);	
		
		Purchase purchase = null;
		while (rs.next()) {
			purchase = new Purchase();
			
			purchase.setPaymentOption(rs.getString("payment_option"));
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setDivyAddr(rs.getString("demailaddr"));
			purchase.setDivyRequest(rs.getString("dlvy_request"));
			purchase.setDivyDate(rs.getString("dlvy_date"));
			purchase.setOrderDate(rs.getDate("order_data"));
			purchase.setTranNo(rs.getInt("tran_no"));
			purchase.setTranCode(rs.getString("tran_status_code"));		
		
			System.out.println("purchase 셋팅완료 : " + purchase);
			System.out.println("findPurchase2 tran_no : " + purchase.getTranNo());
			System.out.println("findPurchase2 tran_status_code : " + purchase.getTranCode());
		}		
		rs.close();
		pStmt.close();
		con.close();
			
		System.out.println("<<<<< PurchaseDAO : findPurchase2() 종료 >>>>>");
		
		return purchase; 
	}
		
	
	//구매목록 보기를 위한 DBMS를 수행
	public Map<String,Object> getPurchaseList(Search search, String buyerId) throws Exception {
		System.out.println("<<<<< PurchaseDAO : getPurchaseList() 시작 >>>>>");
		System.out.println("받은 search : " + search);
		System.out.println("받은 buyerId : " + buyerId);
		
		String sql = "SELECT * FROM transaction WHERE buyer_id='" + buyerId +"'";

		//getTotalCount() 메소드 실행 (this. 생략가능)
		int totalCount = this.getTotalCount(sql);
		System.out.println("totalCount : " + totalCount);
				
		//CurrentPage 게시물만 받도록 Query 다시구성
		//makeCurrentPageSql() 메소드 실행 (this. 생략가능)
		sql = this.makeCurrentPageSql(sql, search);
		
		Connection con = DBUtil.getConnection();
		
		Statement stmt = con.createStatement();
		
		ResultSet rs = stmt.executeQuery(sql);
		System.out.println("sql 전송완료 : " + sql);
		
		Map<String,Object> map = new HashMap<String,Object>();
		List<Purchase> list = new ArrayList<Purchase>();
		
		Purchase purchase = null;
		UserService service = new UserServiceImpl();
		
		while (rs.next()) {
			purchase = new Purchase();
			
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setTranNo(rs.getInt("tran_no"));
			purchase.setTranCode(rs.getString("tran_status_code"));
			purchase.setBuyer(service.getUser(rs.getString("buyer_id")));
				
			list.add(purchase);
			
//			if (!rs.next()) {
//				break;
//			}
			System.out.println("purchase 셋팅완료 : " + purchase);
		}
		map.put("totalCount", totalCount);
		map.put("list", list);
		System.out.println("map에 totalCount 추가 : " + map);
		System.out.println("map에 list 추가 : " + map);
		
		System.out.println("list.size() : " + list.size()); 
		System.out.println("map.size() : " + map.size());
		
		rs.close();
		stmt.close();
		con.close();
		
		System.out.println("<<<<< PurchaseDAO : getPurchaseList() 종료 >>>>>");
		
		return map;
	}
	
	
	//판매목록 보기를 위한 DBMS를 수행
	public Map<String,Object> getSaleList(Search search) throws Exception {
		System.out.println("<<<<< PurchaseDAO : getSaleList() 시작 >>>>>");
		System.out.println("받은 search : " + search);
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM product ";
		
		//SearchCondition에 값이 있을 경우
		if (search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("0")) {
				sql += " WHERE prod_no LIKE '%" + search.getSearchKeyword() + "%'";
			} else if (search.getSearchCondition().equals("1")) {
				sql += " WHERE prod_name LIKE '%" + search.getSearchKeyword() + "%'";
			} else if (search.getSearchCondition().equals("2")) {
				sql += " WHERE price LIKE '%" + search.getSearchKeyword() + "%'";
			}
		}
		sql += " ORDER BY prod_no";

		//getTotalCount() 메소드 실행 (this. 생략가능)
		int totalCount = this.getTotalCount(sql);
		System.out.println("totalCount : " + totalCount);
				
		//CurrentPage 게시물만 받도록 Query 다시구성
		//makeCurrentPageSql() 메소드 실행 (this. 생략가능)
		sql = this.makeCurrentPageSql(sql, search);
				
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		System.out.println("sql 전송완료 : " + sql);

		//HashMap<String,Object> , ArrayList<Product> 인스턴스 생성
		Map<String,Object> map = new HashMap<String,Object>();
		List<Product> list = new ArrayList<Product>();
				
		PurchaseService service = new PurchaseServiceImpl();
		
		while (rs.next()) {
			Product product = new Product();
			
			product.setProdNo(rs.getInt("prod_no"));
			product.setProdName(rs.getString("prod_name"));
			product.setProdDetail(rs.getString("prod_detail"));
			product.setManuDate(rs.getString("manufacture_day"));
			product.setPrice(rs.getInt("price"));
			product.setFileName(rs.getString("image_file"));
			product.setRegDate(rs.getDate("reg_date"));
				
			if(findPurchase2(product.getProdNo()) != null) {
				product.setProTranCode(findPurchase2(product.getProdNo()).getTranCode());
			}
				
			list.add(product);
			
//			if (!rs.next()) {
//				break;
//			}				
			System.out.println("product 셋팅완료 : " + product);
		}
		
		map.put("totalCount", new Integer(totalCount));
		System.out.println("map에 totalCount 추가 : " + map);
		
		map.put("list", list);
		System.out.println("map에 list 추가 : " + map);
		
		System.out.println("list.size() : " + list.size()); 
		System.out.println("map.size() : " + map.size()); 
		
		rs.close();
		pStmt.close();
		con.close();
		
		System.out.println("<<<<< PurchaseDAO : getSaleList() 종료 >>>>>");
		
		return map;
	}
	
	
	//구매정보 수정을 위한 DBMS를 수행
	public void updatePurchase(Purchase purchase) throws Exception {
		System.out.println("<<<<< PurchaseDAO : updatePurchase() 시작 >>>>>");
		System.out.println("받은 purchase : " + purchase);
		
		Connection con = DBUtil.getConnection();
		
		String sql = "UPDATE transaction "
					+ "SET payment_option=?, receiver_name=?, receiver_phone=?,"
					+ "demailaddr=?, dlvy_request=?, dlvy_date=? "
					+ "WHERE tran_no=? ";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, purchase.getPaymentOption());
		pStmt.setString(2, purchase.getReceiverName());
		pStmt.setString(3, purchase.getReceiverPhone());
		pStmt.setString(4, purchase.getDivyAddr());
		pStmt.setString(5, purchase.getDivyRequest());
		pStmt.setString(6, purchase.getDivyDate());
		pStmt.setInt(7, purchase.getTranNo());
		pStmt.executeUpdate();
		System.out.println("update 완료 : " + sql);
		
		pStmt.close();
		con.close();
		
		System.out.println("<<<<< PurchaseDAO : updatePurchase() 종료 >>>>>");
	}
	
	
	//구매 상태코드 수정을 위한 DBMS를 수행
	public void updateTranCode(Purchase purchase) throws Exception {
		System.out.println("<<<<< PurchaseDAO : updateTranCode() 시작 >>>>>");
		System.out.println("받은 purchase : " + purchase);
		
		Connection con = DBUtil.getConnection();
		
		String sql = "UPDATE transaction SET tran_status_code=? WHERE tran_no=?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, purchase.getTranCode());
		pStmt.setInt(2, purchase.getTranNo());
		pStmt.executeUpdate();
		System.out.println("update 완료 : " + sql);
		System.out.println("update한 tranCode : " + purchase.getTranCode());
		System.out.println("update한 tranNo : " + purchase.getTranNo());
		
		pStmt.close();
		con.close();
		
		System.out.println("<<<<< PurchaseDAO : updateTranCode() 종료 >>>>>");
	}
	
	
	// 게시판 Page 처리를 위한 전체 Row(totalCount)  return
	private int getTotalCount(String sql) throws Exception {
		System.out.println("<<<<< PurchaseDAO : getTotalCount() 시작 >>>>>");
		
		sql = "SELECT COUNT(*) FROM ( " +sql+ " ) countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		System.out.println("<<<<< PurchaseDAO : getTotalCount() 종료 >>>>>");
		
		return totalCount;
	}
	
	
	// 게시판 currentPage Row 만  return
	private String makeCurrentPageSql(String sql, Search search){
		System.out.println("<<<<< PurchaseDAO : makeCurrentPageSql() 시작 >>>>>");
		
		sql = 	"SELECT * "+ 
				"FROM ( SELECT inner_table. * ,  ROWNUM AS row_seq " +
				"FROM (	"+sql+" ) inner_table "+
				"WHERE ROWNUM <= "+search.getCurrentPage()*search.getPageSize()+" ) " +
				"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +
				" AND "+search.getCurrentPage()*search.getPageSize();
	
		System.out.println("make SQL은? "+ sql);//디버깅	
		
		System.out.println("<<<<< PurchaseDAO : makeCurrentPageSql() 종료 >>>>>");
		
		return sql;
	}

}//end of class
