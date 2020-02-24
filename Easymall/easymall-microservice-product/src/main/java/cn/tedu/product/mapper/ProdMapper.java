package cn.tedu.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.common.pojo.Product;

public interface ProdMapper {

	List<Product> prodPageQuery(@Param("start")Integer start, @Param("rows")Integer rows);

	Integer queryTotal();

	Product queryById(String prodId);

	void productSave(Product product);

	void productUpdate(Product product);

	int deleteProd(String id); 

}
