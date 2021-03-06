package com.model2.mvc.service.product.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.ProductDao;


//==> 상품관리 서비스 구현
@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService {
	
	///Field
	@Autowired
	@Qualifier("productDaoImpl")
	private ProductDao productDao;
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
	
	///Constructor
	public ProductServiceImpl() {
		System.out.println(this.getClass());
	}

	//Method
	public void addProduct(Product product) throws Exception {
		System.out.println("<<<<< ProductServiceImpl : addProduct() 실행 >>>>>");
		productDao.addProduct(product);
	}

	
	public Product getProduct(int prodNo) throws Exception {
		System.out.println("<<<<< ProductServiceImpl : getProduct() 실행 >>>>>");
		return productDao.getProduct(prodNo);
	}

	
	public Map<String, Object> getProductList(Search search) throws Exception {
		System.out.println("<<<<< ProductServiceImpl : getProductList() 실행 >>>>>");
		return productDao.getProductList(search);
	}

	
	public void updateProduct(Product product) throws Exception {
		System.out.println("<<<<< ProductServiceImpl : updateProduct() 실행 >>>>>");
		productDao.updateProduct(product);
	}

}//end of class
