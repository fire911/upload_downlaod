package com.goldcipher.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

/**
 * Servlet implementation class SmartDownloadServlet
 */
@WebServlet(name = "smartDownloadServlet", urlPatterns = { "/smartDownloadServlet" })
public class SmartDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取文件名
		String filename=request.getParameter("filename");
		//创建SmartUpLoad对象
		SmartUpload su=new SmartUpload();
		//初始化对象
		su.initialize(getServletConfig(), request, response);
		su.setContentDisposition(null);
		try {
			su.downloadFile("uploads/files/"+filename);
		} catch (SmartUploadException e) {
			e.printStackTrace();
		}
	}

}
