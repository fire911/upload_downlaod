package com.goldcipher.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * smartUpload批量下载文件
 * @author lene
 *
 */
@WebServlet("/batchDownloadServlet")
public class BatchDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("applicatoin/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=test.zip");
		
		String path=getServletContext().getRealPath("/uploads/files")+"/";
		String[] filenames=request.getParameterValues("filename");
		ZipOutputStream zos=new ZipOutputStream(response.getOutputStream());
		String str="";
		String rt="\r\n";
		for (String filename : filenames) {
			str+=filename+rt;
			File file=new File(path+filename);
			zos.putNextEntry(new ZipEntry(filename));
			FileInputStream fis=new FileInputStream(file);
			byte[] b=new byte[1024];
			int n=0;
			while((n=fis.read(b))!=-1){
				zos.write(b, 0, n);
			}
			zos.flush();
			fis.close();
		}
		zos.setComment("Download Success:"+rt+str);
		zos.flush();
		zos.close();
	}

}
