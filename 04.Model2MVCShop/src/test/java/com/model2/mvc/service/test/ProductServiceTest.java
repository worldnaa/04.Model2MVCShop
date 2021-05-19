package com.model2.mvc.service.test;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class ProductServiceTest {

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//==> ProductService productService = new productServiceImpl(); 과 같은 의미
	
	//@Test
	public void testAddProduct() throws Exception {
		
		Product product = new Product();
		product.setProdNo(40000);
		product.setProdName("testProdName");
		product.setProdDetail("testProdDetail");
		product.setManuDate("20210501");
		product.setPrice(5000);
		product.setFileName("testFileName");
		
		productService.addProduct(product);
		product = productService.getProduct(40000);
		System.out.println(product);
		
		Assert.assertEquals(40000, product.getProdNo());
		Assert.assertEquals("testProdName", product.getProdName());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals("20210501", product.getManuDate());
		Assert.assertEquals(5000, product.getPrice());
		Assert.assertEquals("testFileName", product.getFileName());		
	}
	
	//@Test
	public void testGetProduct() throws Exception {
		
		Product product = new Product();
		product = productService.getProduct(40000);
		System.out.println(product);
		
		Assert.assertEquals(40000, product.getProdNo());
		Assert.assertEquals("testProdName", product.getProdName());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals("20210501", product.getManuDate());
		Assert.assertEquals(5000, product.getPrice());
		Assert.assertEquals("testFileName", product.getFileName());

		Assert.assertNotNull(productService.getProduct(20000));
		Assert.assertNotNull(productService.getProduct(30000));
	}
	
	//@Test
	public void testUpdateUser() throws Exception{
		 
		Product product = productService.getProduct(40000);
		Assert.assertNotNull(product);
		
		Assert.assertEquals("testProdName", product.getProdName());
		Assert.assertEquals("testProdDetail", product.getProdDetail());
		Assert.assertEquals("20210501", product.getManuDate());
		Assert.assertEquals(5000, product.getPrice());
		Assert.assertEquals("testFileName", product.getFileName());

		product.setProdName("changeName");
		product.setProdDetail("changeDetail");
		product.setManuDate("20210101");
		product.setPrice(10000);
		product.setFileName("changeFileName");
		
		productService.updateProduct(product);
		
		product = productService.getProduct(40000);
		Assert.assertNotNull(product);
		System.out.println(product);
			
		product.setProdName("changeName");
		product.setProdDetail("changeDetail");
		product.setManuDate("20210101");
		product.setPrice(10000);
		product.setFileName("changeFileName");
	 }
	
	//@Test
	public void testGetProductListAll() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map = productService.getProductList(search);//productServiceImpl 메서드 실행
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	//Assert.assertEquals(3, list.size());
	 	
		//==> console 확인
	 	System.out.println("list 값 : "+list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	//Assert.assertEquals(3, list.size());
	 	
	 	//==> console 확인
	 	System.out.println("list 값 : "+list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	
	@Test
	public void testGetProductListByProdNo() throws Exception{
		System.out.println("=================== ProdNo() 테스트1 ====================");	 
		Search search = new Search(); 	  //Search 클래스 인스턴스 생성
		search.setCurrentPage(1); 		  //현재 페이지에 1 셋팅
		search.setPageSize(3); 			  //페이지 사이즈에 3 셋팅
		search.setSearchCondition("0");   //검색조건 0번 ==> ProdNo
		search.setSearchKeyword("10002"); //검색 키워드 "10002" 셋팅
		
		//productServiceImpl.getProductList 실행 후, 결과값(리턴값)을 대입
		//결과값(리턴값) - list, totalCount 담긴 map
		Map<String,Object> map = productService.getProductList(search); //
		System.out.println("map size : " + map.size());
		
		//map에 담긴 list를 List타입의 list에 대입
		List<Object> list = (List<Object>) map.get("list");
		System.out.println("list : " + list);
		System.out.println("list size : " + list.size());
		
		Assert.assertEquals(1, list.size());
		
		//map에 담긴 totalCount를 Integer타입의 totalCount에 대입
		Integer totalCount = (Integer)map.get("totalCount");
		System.out.println("totalCount : " + totalCount);
		 	
		System.out.println("=================== ProdNo() 테스트2 ====================");
		 	
		search.setSearchCondition("0"); 						//검색조건 0번 ==> ProdNo
		search.setSearchKeyword(""+System.currentTimeMillis()); //currentTimeMillis() : 현재 시간 (밀리 초)을 반환
		map = productService.getProductList(search);	
		System.out.println("map size : " + map.size());
		
		list = (List<Object>)map.get("list");
		System.out.println("list : " + list);
		System.out.println("list size : " + list.size());
		
		Assert.assertEquals(0, list.size());
		 	 	
		totalCount = (Integer)map.get("totalCount");
		System.out.println("totalCount : " + totalCount);
	}
		 
	//@Test
	public void testGetProductListByProdName() throws Exception{
		System.out.println("=================== ProdName() 테스트1 ====================");	 
		Search search = new Search(); 	//Search 클래스 인스턴스 생성
		search.setCurrentPage(1); 		//현재 페이지에 1 셋팅
		search.setPageSize(3); 			//페이지 사이즈에 3 셋팅
		search.setSearchCondition("1"); //검색조건 1번 ==> ProdName
		search.setSearchKeyword("빨대"); //검색 키워드 "빨대" 셋팅
		
		//productServiceImpl.getProductList 실행 후, 결과값(리턴값)을 대입
		//결과값(리턴값) - list, totalCount 담긴 map
		Map<String,Object> map = productService.getProductList(search); //
		System.out.println("map size : " + map.size());
		
		//map에 담긴 list를 List타입의 list에 대입
		List<Object> list = (List<Object>) map.get("list");
		System.out.println("list : " + list);
		System.out.println("list size : " + list.size());
		
		Assert.assertEquals(1, list.size());
		
		//map에 담긴 totalCount를 Integer타입의 totalCount에 대입
		Integer totalCount = (Integer)map.get("totalCount");
		System.out.println("totalCount : " + totalCount);
		 	
		System.out.println("=================== ProdName() 테스트2 ====================");
		 	
		search.setSearchCondition("1"); 						//검색조건 1번 ==> ProdName
		search.setSearchKeyword(""+System.currentTimeMillis()); //currentTimeMillis() : 현재 시간 (밀리 초)을 반환
		map = productService.getProductList(search);	
		System.out.println("map size : " + map.size()); 
		
		list = (List<Object>)map.get("list");
		System.out.println("list : " + list);
		System.out.println("list size : " + list.size());
		
		Assert.assertEquals(0, list.size());
		 	 	
		totalCount = (Integer)map.get("totalCount");
		System.out.println("totalCount : " + totalCount);
	}
	
	//@Test
	public void testGetProductListByPrice() throws Exception{
		System.out.println("=================== Price() 테스트1 ====================");	 
		Search search = new Search(); 	 //Search 클래스 인스턴스 생성
		search.setCurrentPage(1); 		 //현재 페이지에 1 셋팅
		search.setPageSize(3); 			 //페이지 사이즈에 3 셋팅
		search.setSearchCondition("2");  //검색조건 2번 ==> Price
		search.setSearchKeyword("5000"); //검색 키워드 "5000" 셋팅
		
		//productServiceImpl.getProductList 실행 후, 결과값(리턴값)을 대입
		//결과값(리턴값) - list, totalCount 담긴 map
		Map<String,Object> map = productService.getProductList(search); //
		System.out.println("map size : " + map.size());
		
		//map에 담긴 list를 List타입의 list에 대입
		List<Object> list = (List<Object>) map.get("list");
		System.out.println("list : " + list);
		System.out.println("list size : " + list.size());
		
		Assert.assertEquals(3, list.size());
		
		//map에 담긴 totalCount를 Integer타입의 totalCount에 대입
		Integer totalCount = (Integer)map.get("totalCount");
		System.out.println("totalCount : " + totalCount);
		 	
		System.out.println("=================== Price() 테스트2 ====================");
		 	
		search.setSearchCondition("2"); 						//검색조건 2번 ==> Price
		search.setSearchKeyword(""+System.currentTimeMillis()); //currentTimeMillis() : 현재 시간 (밀리 초)을 반환
		map = productService.getProductList(search);	
		System.out.println("map size : " + map.size());
		
		list = (List<Object>)map.get("list");
		System.out.println("list : " + list);
		System.out.println("list size : " + list.size());
		
		Assert.assertEquals(0, list.size());
		 	 	
		totalCount = (Integer)map.get("totalCount");
		System.out.println("totalCount : " + totalCount);
	}
	
}//end of class
