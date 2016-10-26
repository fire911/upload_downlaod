package com.goldcipher.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet("/downloadServlet")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DownloadServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String filename = request.getParameter("filename");
		// 获取文件下载路径
		String path = getServletContext().getRealPath("/uploads/files");
		File file = new File(path, filename);
		if (file.exists()) {
			// 设置相应类型 application/octet-stream
			response.setContentType("appliaction/x-msdownload");
			response.setHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");
			InputStream in = new FileInputStream(file);
			ServletOutputStream out = response.getOutputStream();
			byte b[] = new byte[1024];
			int i;
			while ((i = in.read(b)) != -1) {
				out.write(b, 0, i);
			}
			out.close();
			in.close();
		} else {
			request.setAttribute("errorResult", "文件" + filename + "不存在下载失败！");
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
