package com.goldcipher.servlet;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.jspsmart.upload.SmartUpload;

@WebServlet(name = "smartUploadServlet", urlPatterns = { "/smartUploadServlet" })
public class SmartUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置文件上传路径
		String path = getServletContext().getRealPath("/uploads/files");
		File uploadPath = new File(path);
		if (!uploadPath.exists()) {
			uploadPath.mkdirs();
		}
		SmartUpload su = new SmartUpload();
		// 初始化组件
		su.initialize(getServletConfig(), request, response);
		// 设置文件的大小
		su.setMaxFileSize(1024 * 1024 * 10);
		// 设置所有文件的大小
		su.setTotalMaxFileSize(1024 * 1024 * 100);
		// 设置允许的文件类型
		su.setAllowedFilesList("jpg,txt,jpeg,gif,png");
		// 设置不允许上传的文件
		// su.setDeniedFilesList("rar,js");
		String result = "上传成功";
		try {
			// 上传文件
			su.upload();
			// 保存文件
			int count = su.save(path);
			System.out.println("上传成功了" + count + "个文件。");
		} catch (Exception e) {
			result = "上传失败";
			if (e.getMessage().indexOf("1015") != -1) {
				result = "上传失败：上传文件类型不正确！";
			} else if (e.getMessage().indexOf("1010") != -1) {
				result = "上传失败：上传文件类型不正确！";
			} else if (e.getMessage().indexOf("1105") != -1) {
				result = "上传失败：上传文件大小超出允许上传文件大小范围！";
			} else if (e.getMessage().indexOf("1015") != -1) {
				result = "上传失败：上传文件总大小超出允许上传文件总大小范围！";
			}
			e.printStackTrace();
		}
		//打印上传文件的一些信息
		for (int i = 0; i < su.getFiles().getCount(); i++) {
			com.jspsmart.upload.File file=su.getFiles().getFile(i);
			System.out.println("--------------------------------------");
			System.out.println("表单中name的值："+file.getFieldName());
			System.out.println("上传文件的文件名："+file.getFileName());
			System.out.println("上传文件的大小："+file.getSize());
			System.out.println("上传文件的后缀："+file.getFileExt());
			System.out.println("上传文件的全名："+file.getFilePathName());
			System.out.println("--------------------------------------");
		}
		request.setAttribute("result", result);
		request.getRequestDispatcher("smartupload.jsp").forward(request, response);
	}

}
