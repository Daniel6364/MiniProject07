package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.product.ProductService;


//==> ȸ������ Controller
@Controller
@RequestMapping("/purchase/*")
public class PurchaseController {
	
	///Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	//setter Method ���� ����
		
	public PurchaseController(){
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	
	@RequestMapping( value="addPurchase", method=RequestMethod.GET)
	public String addPurchase(@ModelAttribute("product") Product product,
			@RequestParam("prod_no") String prodNo, Model model) throws Exception {

		System.out.println("/purchase/addPurchase : GET");
		
		product = productService.getProduct(Integer.parseInt(prodNo));
		
		model.addAttribute("product", product);
		
		return "forward:/purchase/addPurchaseView.jsp";
	}
	
	@RequestMapping( value="addPurchase", method=RequestMethod.POST)
	public String addPurchase( @ModelAttribute("purchase") Purchase purchase,  
			@RequestParam("prodNo") String prodNo, @RequestParam("buyerId") String buyerId ) throws Exception {

		System.out.println("/purchase/addPurchase : POST");
		//Business Logic
		
		purchase.setPurchaseProd(productService.getProduct(Integer.parseInt(prodNo)));
		purchase.setBuyer(userService.getUser(buyerId));
		purchaseService.addPurchase(purchase);
		
		return "forward:/purchase/addPurchase.jsp";
	}
	
	@RequestMapping( value="getPurchase", method=RequestMethod.GET)
	public String getPurchase( @RequestParam("tranNo") String tranNo , Model model ) throws Exception {
		
		System.out.println("/purchase/getPurchase : GET");
		//Business Logic
		Purchase purchase = purchaseService.getPurchase(Integer.parseInt(tranNo));
		// Model �� View ����
		model.addAttribute("purchase", purchase);
		
		return "forward:/purchase/getPurchase.jsp";
	}
	 
	@RequestMapping( value="updatePurchase", method=RequestMethod.GET)
	public String updatePurchase( @RequestParam("tranNo") String tranNo , Model model ) throws Exception{

		System.out.println("/purchase/updatePurchase : GET");
		//Business Logic
		Purchase purchase = purchaseService.getPurchase(Integer.parseInt(tranNo));
		// Model �� View ����
		model.addAttribute("purchase", purchase);
		
		return "forward:/purchase/updatePurchaseView.jsp";
	}
	
	@RequestMapping( value="updatePurchase", method=RequestMethod.POST)
	public String updatePurchase( @ModelAttribute("purchase") Purchase purchase, 
			@RequestParam("tranNo") String tranNo, Model model) throws Exception{

		System.out.println("/purchase/updatePurchase : POST");
		//Business Logic
		
		purchase.setTranNo(Integer.parseInt(tranNo));
		
		purchaseService.updatePurchase(purchase);
		
		purchase = purchaseService.getPurchase(Integer.parseInt(tranNo));

		model.addAttribute("purchase", purchase);
		
		return "forward:/purchase/updatePurchase.jsp";
	}
	
	@RequestMapping( value="/listPurchase" )
	public String listPurchase( @ModelAttribute("search") Search search, 
			HttpSession session, Model model ) throws Exception{
		
		System.out.println("/purchase/listPurchase : GET / POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		User buyerId = (User) session.getAttribute("user");
		
		// Business logic ����
		Map<String, Object> map = purchaseService.getPurchaseList(search, buyerId.getUserId());
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model �� View ����
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		return "forward:/purchase/listPurchase.jsp";
	}
	
	@RequestMapping( value="updateTranCode", method=RequestMethod.POST)
	public String updateTranCode( @ModelAttribute("purchase") Purchase purchase, 
			@RequestParam("tranNo") String tranNo, @RequestParam("tranCode") String tranCode, Model model ) throws Exception {

		System.out.println("/purchase/updateTranCode : GET");
		
		purchase.setTranNo(Integer.parseInt(tranNo));
		purchase.setTranCode(tranCode);
		
		purchaseService.updateTranCode(purchase);
		model.addAttribute("purchase", purchase);
		
		return "forward:/purchase/listPurchase.do";
	}
	
	@RequestMapping( value="updateTranCodeByProd", method=RequestMethod.GET)
	public String updateTranCodeByProd( @ModelAttribute("purchase") Purchase purchase, 
			@RequestParam("prodNo") String prodNo, @RequestParam("tranCode") String tranCode, Model model ) throws Exception {
		
		System.out.println("/purchase/updateTranCodeByProd : GET");

		purchase = purchaseService.getPurchase2(Integer.parseInt(prodNo));
		
		purchase.setTranNo(purchase.getTranNo());
		purchase.setTranCode(tranCode);
		
		System.out.println("[1]"+purchase.getTranNo());
		System.out.println("[2]"+purchase.getTranCode());
		
		purchaseService.updateTranCode(purchase);
		model.addAttribute("purchase", purchase);
		
		return "forward:/product/listProduct?menu=manage";
	}
	
	
}