package com.model2.mvc.service.purchase.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;


/*
 *	FileName :  UserServiceTest.java
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/context-common.xml", 
										"classpath:config/context-aspect.xml",
										"classpath:config/context-mybatis.xml", 
										"classpath:config/context-transaction.xml" })
public class PurchaseServiceTest {

	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@Test
	public void testAddPurchase() throws Exception {
		
		Purchase purchase = new Purchase();

		purchase.setPurchaseProd(productService.getProduct(10000));
		purchase.setBuyer(userService.getUser("user01"));
		purchase.setPaymentOption("1");
		purchase.setReceiverName("testReceiverName1");
		purchase.setReceiverPhone("010-2222-3333");
		purchase.setDlvyAddr("testAddr1");
		purchase.setDlvyRequest("testRequest1");
		purchase.setDlvyDate("2015-04-05");
		
		purchaseService.addPurchase(purchase);

//		purchase = purchaseService.getPurchase(tranNo);

		//==> console 확인
		System.out.println(purchase);
		
		//==> API 확인
//		Assert.assertEquals(11111, purchase.getPurchaseProd().getProdNo());
//		Assert.assertEquals("TestUser1", purchase.getBuyer().getUserId());
		Assert.assertEquals("1", purchase.getPaymentOption());
		Assert.assertEquals("testReceiverName1", purchase.getReceiverName());
		Assert.assertEquals("010-2222-3333", purchase.getReceiverPhone());
		Assert.assertEquals("testAddr1", purchase.getDlvyAddr());
		Assert.assertEquals("testRequest1", purchase.getDlvyRequest());
		Assert.assertEquals("2015-04-05", purchase.getDlvyDate());
	}
	
	//@Test
	public void testGetPurchase() throws Exception {
		
		Purchase purchase = new Purchase();
		//==> 필요하다면...
//		user.setUserId("testUserId");
//		user.setUserName("testUserName");
//		user.setPassword("testPasswd");
//		user.setSsn("1111112222222");
//		user.setPhone("111-2222-3333");
//		user.setAddr("경기도");
//		user.setEmail("test@test.com");
		
		// 설현
		purchase = purchaseService.getPurchase(10005);

		//==> console 확인
		System.out.println(purchase);
		
		//==> API 확인
		Assert.assertEquals(10004, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals("user10", purchase.getBuyer().getUserId());
		Assert.assertEquals("2  ", purchase.getPaymentOption());
		Assert.assertEquals("서현", purchase.getReceiverName());
		Assert.assertEquals("01054876548", purchase.getReceiverPhone());
		Assert.assertEquals("서현역", purchase.getDlvyAddr());
		Assert.assertEquals("소녀시대", purchase.getDlvyRequest());

		Assert.assertNotNull(purchaseService.getPurchase(10005));
	}
	
	//@Test
		public void testGetPurchase2() throws Exception {
			
			Purchase purchase = new Purchase();
			//==> 필요하다면...
//			user.setUserId("testUserId");
//			user.setUserName("testUserName");
//			user.setPassword("testPasswd");
//			user.setSsn("1111112222222");
//			user.setPhone("111-2222-3333");
//			user.setAddr("경기도");
//			user.setEmail("test@test.com");
			
			// 김연아
			purchase = purchaseService.getPurchase2(10002);

			//==> console 확인
			System.out.println(purchase);
			
			//==> API 확인
			Assert.assertEquals(10003, purchase.getTranNo());

			Assert.assertNotNull(purchaseService.getPurchase2(10002));
		}
	
	
	//@Test
	 public void testUpdatePurchase() throws Exception{
		 
		Purchase purchase = purchaseService.getPurchase(10005);
		Assert.assertNotNull(purchase);
		
		Assert.assertEquals("user10", purchase.getBuyer().getUserId());
		Assert.assertEquals("2  ", purchase.getPaymentOption());
		Assert.assertEquals("서현", purchase.getReceiverName());
		Assert.assertEquals("01054876548", purchase.getReceiverPhone());
		Assert.assertEquals("서현역", purchase.getDlvyAddr());
		Assert.assertEquals("소녀시대", purchase.getDlvyRequest());
		Assert.assertEquals("2  ", purchase.getTranCode());

		purchase.setPaymentOption("1  ");
		purchase.setReceiverName("티파니");
		purchase.setReceiverPhone("01066666666");
		purchase.setDlvyAddr("동두천");
		purchase.setDlvyRequest("티파니에서 아침을");
		purchase.setDlvyDate("2017-11-18");
		
		purchaseService.updatePurchase(purchase);
		
		purchase = purchaseService.getPurchase(10005);
		Assert.assertNotNull(purchase);
		
		//==> console 확인
		System.out.println(purchase);
			
		//==> API 확인
		Assert.assertEquals("user10", purchase.getBuyer().getUserId());
		Assert.assertEquals("1  ", purchase.getPaymentOption());
		Assert.assertEquals("티파니", purchase.getReceiverName());
		Assert.assertEquals("01066666666", purchase.getReceiverPhone());
		Assert.assertEquals("동두천", purchase.getDlvyAddr());
		Assert.assertEquals("티파니에서 아침을", purchase.getDlvyRequest());

	 }
	 
	// @Test
	public void testGetPurchaseListAll() throws Exception {

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		Purchase purchase = new Purchase();

		purchase.setBuyer(userService.getUser("user10"));
		String buyerId = purchase.getBuyer().getUserId();

		Map<String, Object> map = purchaseService.getPurchaseList(search, buyerId);

		List<Object> list = (List<Object>) map.get("list");
		Assert.assertEquals(3, list.size());

		// ==> console 확인
		System.out.println(list);

		Integer totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);

		System.out.println("=======================================");

		search.setCurrentPage(1);
		search.setPageSize(3);
		map = purchaseService.getPurchaseList(search, buyerId);

		list = (List<Object>) map.get("list");
		Assert.assertEquals(3, list.size());

		// ==> console 확인
		// System.out.println(list);

		totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);
	}
	 
}