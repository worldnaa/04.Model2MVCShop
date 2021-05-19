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

///////////////////////////////// ��� �� �� //////////////////////////////////////////////////////
public class ProductDao {
	//Constructor
	public ProductDao() {
	}
	
	//Method
	//��ǰ����� ���� DBMS�� ����
	public void insertProduct(Product product) throws Exception {
		System.out.println("<<<<< ProductDAO : insertProduct() ���� >>>>>");
		System.out.println("���� product : " + product);
		
		Connection con = DBUtil.getConnection();
		
		String sql = "INSERT INTO product VALUES (seq_product_prod_no.nextval,?,?,?,?,?,sysdate)";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, product.getProdName());
		pStmt.setString(2, product.getProdDetail());
		pStmt.setString(3, product.getManuDate());
		pStmt.setInt(4, product.getPrice());
		pStmt.setString(5, product.getFileName());
		pStmt.executeUpdate();
		System.out.println("insert �Ϸ� : " + sql);
		
		pStmt.close();
		con.close();	
		
		System.out.println("<<<<< ProductDAO : insertProduct() ���� >>>>>");
	}
	
	
	//��ǰ���� ��ȸ�� ���� DBMS�� ����
	public Product findProduct(int prodNo) throws Exception {
		System.out.println("<<<<< ProductDAO : findProduct() ���� >>>>>");
		System.out.println("���� prodNo : " + prodNo);
		
		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM product WHERE prod_no=?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, prodNo);
		ResultSet rs = pStmt.executeQuery();
		System.out.println("sql ���ۿϷ� : " + sql);

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
		System.out.println("product ���ÿϷ� : " + product);
		
		rs.close();
		pStmt.close();
		con.close();
		
		System.out.println("<<<<< ProductDAO : findProduct() ���� >>>>>");
		
		return product;
	}
	
	
	//��ǰ��� ��ȸ�� ���� DBMS�� ����
	public Map<String,Object> getProductList(Search search) throws Exception {
		System.out.println("<<<<< ProductDAO : getProductList() ���� >>>>>");
		System.out.println("���� search : " + search);
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM product ";
		
		//SearchCondition�� ���� ���� ���
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
		
		//getTotalCount() �޼ҵ� ���� (this. ��������)
		int totalCount = this.getTotalCount(sql);
		System.out.println("totalCount : " + totalCount);
		
		//CurrentPage �Խù��� �޵��� Query �ٽñ���
		//makeCurrentPageSql() �޼ҵ� ���� (this. ��������)
		sql = this.makeCurrentPageSql(sql, search);
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		System.out.println("sql ���ۿϷ� : " + sql);

		//HashMap<String,Object> , ArrayList<Product> �ν��Ͻ� ����
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
				product.setProTranCode("������"); 
			}else {
				product.setProTranCode("�Ǹ���");	
			}
				
			list.add(product);
//			if (!rs.next()) {
//				break;
//			}
			System.out.println("product ���ÿϷ� : " + product);	
		}
		
		//totalCount ���� ����
		map.put("totalCount", new Integer(totalCount));
		System.out.println("map�� totalCount �߰� : " + map);
		
		//currentPage �� �Խù� ���� ���� List ����
		map.put("list", list);
		System.out.println("map�� list �߰� : " + map);
		
		System.out.println("list.size() : " + list.size()); 
		System.out.println("map.size() : " + map.size()); 
		
		rs.close();
		pStmt.close();
		con.close();
		
		System.out.println("<<<<< ProductDAO : getProductList() ���� >>>>>");
		
		return map;
	}
	
	
	//��ǰ���� ������ ���� DBMS�� ����
	public void updateProduct(Product product) throws Exception {
		System.out.println("<<<<< ProductDAO : updateProduct() ���� >>>>>");
		System.out.println("���� product : " + product);
		
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
		System.out.println("update �Ϸ� : " + sql);
		
		pStmt.close();
		con.close();
		
		System.out.println("<<<<< ProductDAO : updateProduct() ���� >>>>>");
	}
	
	
	// �Խ��� Page ó���� ���� ��ü Row(totalCount)  return
	private int getTotalCount(String sql) throws Exception {
		System.out.println("<<<<< ProductDAO : getTotalCount() ���� >>>>>");
		
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
		
		System.out.println("<<<<< ProductDAO : getTotalCount() ���� >>>>>");
		
		return totalCount;
	}
	
	
	// �Խ��� currentPage Row ��  return
	private String makeCurrentPageSql(String sql, Search search){
		System.out.println("<<<<< ProductDAO : makeCurrentPageSql() ���� >>>>>");
		
		sql = 	"SELECT * "+ 
				"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
								" 	FROM (	"+sql+" ) inner_table "+
								"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
				"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
	
		System.out.println("make SQL��? "+ sql);//�����	
		
		System.out.println("<<<<< ProductDAO : makeCurrentPageSql() ���� >>>>>");
		
		return sql;
	}

}//end of class
