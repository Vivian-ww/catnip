package cn.tedu.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jt.common.pojo.Product;
import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;

import cn.tedu.product.service.ProdService;

@RestController
@RequestMapping("product/manage")
public class ProdController {
	@Autowired
	ProdService prodService;
	
	@RequestMapping("/pageManage")
	public EasyUIResult prodPageQuery(Integer page, Integer rows) {
		EasyUIResult result = prodService.prodPageQuery(page, rows);		
		return result;
	}
	
	@RequestMapping("item/{prodId}")
	public Product queryById(@PathVariable String prodId){
		Product product=prodService.queryById(prodId);
		return product;
	}
	
	//商品新增
	@RequestMapping("/save")
	public SysResult productSave(Product product){
		//使用异常信息来表示成功和失败
		try{
			prodService.productSave(product);
			//没有异常出现,返回ok
			return SysResult.ok();
		}catch(Exception e){
			e.printStackTrace();
			return SysResult.build(201, e.getMessage(), null);
		}
	}
	//商品更新
	@RequestMapping("update")
	public SysResult productUpdate(Product product){
		try{
			prodService.productUpdate(product);
			//没有异常出现,返回ok
			return SysResult.ok();
		}catch(Exception e){
			e.printStackTrace();
			return SysResult.build(201, e.getMessage(), null);
		}
	}	
	
	@RequestMapping("/delete")
	@ResponseBody
	public SysResult deleteProd(String[] ids){
		SysResult result = new SysResult();	
		int n = prodService.deleteProd(ids);
		if(n==0){
			result.setStatus(201);
			return result;
		}else{
			result.setStatus(200);
			return result;
		}
	}
}
