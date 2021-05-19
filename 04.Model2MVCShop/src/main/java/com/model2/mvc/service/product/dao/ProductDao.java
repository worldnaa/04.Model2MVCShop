package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

///////////////////////////////// 사용 안 함 //////////////////////////////////////////////////////
public class ProductDao {
	//Constructor
	public ProductDao() {
	}
	
	//Method
	//상품등록을 위한 DBMS를 수행
	public void insertProduct(Product product) throws Exception {
		System.out.println("<<<<< ProductDAO : insertProduct() 시작 >>>>>");
		System.out.println("받은 product : " + product);
		
		Connection con = DBUtil.getConnection();
		
		String sql = "INSERT INTO product VALUES (seq_product_prod_no.nextval,?,?,?,?,?,sysdate)";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, product.getProdName());
		pStmt.setString(2, product.getProdDetail());
		pStmt.setString(3, product.getManuDate());
		pStmt.setInt(4, product.getPrice());
		pStmt.setString(5, product.getFileName());
		pStmt.executeUpdate();
		System.out.println("insert 완료 : " + sql);
		
		pStmt.close();
		con.close();	
		
		System.out.println("<<<<< ProductDAO : insertProduct() 종료 >>>>>");
	}
	
	
	//상품정보 조회를 위한 DBMS를 수행
	public Product findProduct(int prodNo) throws Exception {
		System.out.println("<<<<< ProductDAO : findProduct() 시작 >>>>>");
		System.out.println("받은 prodNo : " + prodNo);
		
		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM product WHERE prod_no=?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, prodNo);
		ResultSet rs = pStmt.executeQuery();
		System.out.println("sql 전송완료 : " + sql);

		Product product = null;
		while (rs.next()) {
			product = new Product();
			
			product.setProdNo(rs.getInt("prod_no"));
			product.setProdName(rs.getString("prod_name"));
			product.setProdDetail(rs.getString("prod_detail"));
			product.setManuDate(rs.getString("manufacture_day"));
			product.setPrice(rs.getInt("price"));
			product.setFileName(rs.getString("image_file"));
			product.setRegDate(rs.getDate("reg_date"));
		}
		System.out.println("product 셋팅완료 : " + product);
		
		rs.close();
		pStmt.close();
		con.close();
		
		System.out.println("<<<<< ProductDAO : findProduct() 종료 >>>>>");
		
		return product;
	}
	
	
	//상품목록 조회를 위한 DBMS를 수행
	public Map<String,Object> getProductList(Search search) throws Exception {
		System.out.println("<<<<< ProductDAO : getProductList() 시작 >>>>>");
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
				
			if(service.getPurchase2(product.getProdNo()) != null) {
				product.setProTranCode("재고없음"); 
			}else {
				product.setProTranCode("판매중");	
			}
				
			list.add(product);
//			if (!rs.next()) {
//				break;
//			}
			System.out.println("product 셋팅완료 : " + product);	
		}
		
		//totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount));
		System.out.println("map에 totalCount 추가 : " + map);
		
		//currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list);
		System.out.println("map에 list 추가 : " + map);
		
		System.out.println("list.size() : " + list.size()); 
		System.out.println("map.size() : " + map.size()); 
		
		rs.close();
		pStmt.close();
		con.close();
		
		System.out.println("<<<<< ProductDAO : getProductList() 종료 >>>>>");
		
		return map;
	}
	
	
	//상품정보 수정을 위한 DBMS를 수행
	public void updateProduct(Product product) throws Exception {
		System.out.println("<<<<< ProductDAO : updateProduct() 시작 >>>>>");
		System.out.println("받은 product : " + product);
		
		Connection con = DBUtil.getConnection();

		String sql = "UPDATE product "
				    + "SET prod_name=?, prod_detail=?, manufacture_day=?, "
				    + "price=?, image_file=? WHERE prod_no=?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, product.getProdName());
		pStmt.setString(2, product.getProdDetail());
		pStmt.setString(3, product.getManuDate());
		pStmt.setInt(4, product.getPrice());
		pStmt.setString(5, product.getFileName());
		pStmt.setInt(6, product.getProdNo());
		pStmt.executeUpdate();
		System.out.println("update 완료 : " + sql);
		
		pStmt.close();
		con.close();
		
		System.out.println("<<<<< ProductDAO : updateProduct() 종료 >>>>>");
	}
	
	
	// 게시판 Page 처리를 위한 전체 Row(totalCount)  return
	private int getTotalCount(String sql) throws Exception {
		System.out.println("<<<<< ProductDAO : getTotalCount() 시작 >>>>>");
		
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
		
		System.out.println("<<<<< ProductDAO : getTotalCount() 종료 >>>>>");
		
		return totalCount;
	}
	
	
	// 게시판 currentPage Row 만  return
	private String makeCurrentPageSql(String sql, Search search){
		System.out.println("<<<<< ProductDAO : makeCurrentPageSql() 시작 >>>>>");
		
		sql = 	"SELECT * "+ 
				"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
								" 	FROM (	"+sql+" ) inner_table "+
								"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
				"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
	
		System.out.println("make SQL은? "+ sql);//디버깅	
		
		System.out.println("<<<<< ProductDAO : makeCurrentPageSql() 종료 >>>>>");
		
		return sql;
	}

}//end of class
