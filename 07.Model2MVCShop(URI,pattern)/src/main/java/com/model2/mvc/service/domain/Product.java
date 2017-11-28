package com.model2.mvc.service.domain;

import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class Product {
	
	private String fileName;
	private String manuDate;
	private int price;
	private String prodDetail;
	private String prodName;
	private int prodNo;
	private Date regDate;
	private String proTranCode;
	
	// SpringFramework FileUpload
	private List<MultipartFile> files;
	private String upDir;
	
	
	public Product(){
	}
	
	// Application Test
	public Product(String prodName, String prodDetail, String manuDate, int price, String fileName) {
			this.prodName = prodName;
			this.prodDetail = prodDetail;
			this.manuDate = manuDate;
			this.price = price;
			this.fileName = fileName;
	}
	
	public String getProTranCode() {
		return proTranCode;
	}
	public void setProTranCode(String proTranCode) {
		if (proTranCode == null) {
			this.proTranCode = proTranCode;
		} else {
			this.proTranCode = proTranCode.trim();
		}
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getManuDate() {
		return manuDate;
	}
	public void setManuDate(String manuDate) {
		this.manuDate = manuDate.replace("-", "");
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getProdDetail() {
		return prodDetail;
	}
	public void setProdDetail(String prodDetail) {
		this.prodDetail = prodDetail;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public int getProdNo() {
		return prodNo;
	}
	public void setProdNo(int prodNo) {
		this.prodNo = prodNo;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
	// Spring Framework FileUpload ¸¦ À§ÇÑ Getter/Setter Method
	public List<MultipartFile> getFiles() {
		return files;
	}
	
	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}
	
	public String getUpDir() {
		return upDir;
	}
	
	public void setUpDir(String upDir) {
		this.upDir = upDir;
	}	
	

	@Override
	public String toString() {
		return "ProductVO [fileName=" + fileName + ", manuDate=" + manuDate + ", price=" + price + ", prodDetail="
				+ prodDetail + ", prodName=" + prodName + ", prodNo=" + prodNo + ", regDate=" + regDate
				+ ", proTranCode=" + proTranCode + ", files=" + files + ", upDir=" + upDir + "]";
	}

	
	
	
	
	
	
}