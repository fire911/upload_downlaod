package com.goldcipher.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 文件上传
 * @author lene
 *
 */
@WebServlet("/uploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("请求servlet");
		InputStream in = request.getInputStream();
		//临时文件保存路径
		String path = getServletContext().getRealPath("/uploads/tempFile");
		File tempFile = new File(path);
		// 创建临时文件
		if (!tempFile.exists()) {
			tempFile.createNewFile();
		}
		byte[] bus = new byte[1024];
		int n = 0;
		FileOutputStream out = new FileOutputStream(tempFile);
		while ((n = in.read(bus, 0, bus.length)) != -1) {
			out.write(bus, 0, n);
		}
		out.close();
		in.close();

		// 获取文件名
		RandomAccessFile raf = new RandomAccessFile(tempFile, "r");
		raf.readLine();
		String str = raf.readLine();
		int beginIndex = str.lastIndexOf("\\") + 1;
		if (beginIndex == 0) {
			beginIndex = str.lastIndexOf("=\"") + 2;
		}
		int endIndex = str.lastIndexOf("\"");
		String fileName = str.substring(beginIndex, endIndex);
		System.out.println(fileName);
		raf.seek(0);
		// 获取文件内容起始位置
		long startPosition = 0;
		int i = 1;
		while ((n = raf.readByte()) != -1 && i <= 4) {
			startPosition++;
			if (n == '\n') {
				i++;
			}
		}
		//chrome不需要-1
		//startPosition = startPosition - 1;
		// 定位指针到文件结尾
		raf.seek(raf.length());
		// 获取文件内容截止位置
		long endPosition = raf.getFilePointer();
		int j = 1;
		while (endPosition >= 0 && j <= 2) {
			endPosition--;
			raf.seek(endPosition);
			if (raf.readByte() == '\n') {
				j++;
			}
		}
		endPosition = endPosition - 1;

		// 设置保存文件的路径
		String realPath = getServletContext().getRealPath("/uploads/files");
		File fileUpload = new File(realPath);
		if (!fileUpload.exists()) {
			fileUpload.mkdir();
		}
		File saveFile = new File(realPath, fileName);
		RandomAccessFile accessFile = new RandomAccessFile(saveFile, "rw");
		raf.seek(startPosition);
		while (startPosition < endPosition) {
			accessFile.write(raf.readByte());
			startPosition = raf.getFilePointer();
		}
		// 关闭资源
		accessFile.close();
		raf.close();
		// 删除临时文件
		tempFile.delete();
		
		request.setAttribute("result", "上传成功");
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
}
