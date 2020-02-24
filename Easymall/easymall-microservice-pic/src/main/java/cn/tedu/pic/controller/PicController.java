package cn.tedu.pic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;

import cn.tedu.pic.service.PicService;

@RestController
public class PicController {
	@Autowired
	PicService picService;
	
	@RequestMapping("/pic/upload")
	public PicUploadResult picUpload(MultipartFile pic) {
		PicUploadResult result= picService.picUpload(pic);
		return result;
	}
}
