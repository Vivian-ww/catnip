package cn.tedu.pic.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.utils.UUIDUtil;
import com.jt.common.utils.UploadUtil;
import com.jt.common.vo.PicUploadResult;

@Service
public class PicService {

	@Value("${pic.pathDirPrefix}")
	public String pathDirPrefix;
	@Value("${pic.urlPreparePrefix}")
	public String urlPreparePrefix;
	
	public PicUploadResult picUpload(MultipartFile pic) {
		PicUploadResult result = new PicUploadResult();
		System.out.println(pic);
		//1.判断后缀
		String orgname = pic.getOriginalFilename();
		String suffixname = orgname.substring(orgname.lastIndexOf("."));
		if(!suffixname.matches("^.(jpg|png|gif|JPG|PNG|GIF)$")){
			result.setError(1);
			return result;
		}
		//2.检查是否为木马-->判断该图片是否有宽高
		try {
			BufferedImage bufImage = ImageIO.read(pic.getInputStream());
			bufImage.getHeight();
			bufImage.getWidth();
		} catch (IOException e) {
			e.printStackTrace();
			result.setError(1);
			return result;
		}
		//3.生成路径   D://Easymall/upload/4/c/4/1/3/4/5/sdhishfaois793fds.jpg
		
		String dir = UploadUtil.getUploadPath(orgname, "upload")+"/";//upload/4/c/4/1/3/4/5/
		String pathdir = pathDirPrefix + dir;//D://Easymall/ upload/4/c/4/1/3/4/5/
		File filepath = new File(pathdir);
		if(!filepath.exists()){
			filepath.mkdirs();
		}
		System.out.println(dir);
		System.out.println(pathdir);
		//4.存盘
		String filename  = UUIDUtil.getUUID() + suffixname;
		String url = urlPreparePrefix + dir + filename;
		System.out.println(url);
		try {//D://Easymall/ upload/4/c/4/1/3/4/5/sdhishfaois793fds.jpg
			pic.transferTo(new File(pathdir+filename));
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(1);
			return result;
		} 
		//5.返回url（路径+文件名）
		//http://image.jt.com/ upload/4/c/4/1/3/4/5/  sdhishfaois793fds.jpg
		result.setUrl(url);
		System.out.println(result.getUrl()+":"+result.getError());
		return result;
	}

}
