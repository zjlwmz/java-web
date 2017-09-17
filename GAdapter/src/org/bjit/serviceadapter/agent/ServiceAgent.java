package org.bjit.serviceadapter.agent;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 * 						    update  20090527  
 * 							添加处理中文编码（当需要处理编码时在web.xml中设置URIEncoding_GET，URIEncoding_POST值）
 * 							修改连接处理流，提高速度。
 * 							设置连接处理流的连接和获得数据流的时间，服务器有误时，这里可能出现长等待状态。
 * 							初始一些处理，不用每次请求，都查询一次.
 * 						update  郭敏 20091222
 * 							修改字符编码的问题，要求客户端请求过来的URL和数据必需经过UTF-8编码
 */
public class ServiceAgent extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7844911149252523280L;
	private static boolean debug = false;
	private static String key = "";
	
	public void init() throws ServletException {
		debug = Boolean.parseBoolean(getInitParameter("debug"));
		key = "user=" + getInitParameter("user") + "&password="+ getInitParameter("password");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String svrUrl = getRealUrl(request);
		dispatch(request, response, "GET", svrUrl, "");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String data = URLDecoder.decode(readXMLFromRequestBody(request),"UTF-8");
		String svrUrl = getRealUrl(request);
		if (debug)
			System.out.println(data);
		dispatch(request, response, "POST", svrUrl, data);
	}
	
	private void dispatch(HttpServletRequest request,
			HttpServletResponse response, String method, String urlStr,
			String postData) throws ServletException, IOException {
		BufferedInputStream pStream = null;
		ServletOutputStream out = null;
		HttpURLConnection conn = null;

		URL url = null;
		try {
			url = new URL(urlStr.replaceAll(" ", "%20").replaceAll("#", "%23"));
			out = response.getOutputStream();
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(3000);
			conn.setReadTimeout(60000);
			conn.setRequestMethod(method);
//			conn.setAllowUserInteraction(false);
			if ("post".equalsIgnoreCase(method)) {
				conn.setDoOutput(true);
				conn.setDoInput(true);
				conn.setUseCaches(true);
				conn.connect();
				OutputStream pout = null;
				try {
					pout = new DataOutputStream(conn.getOutputStream());
					pout.write(postData.getBytes("UTF-8"));
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					if (pout != null) {
						pout.flush();
						pout.close();
					}
				}
			}
			response.setContentType(conn.getContentType());
			byte[] btBuffer = new byte[2048];
			pStream = new BufferedInputStream(conn.getInputStream());
			int length = -1;
			while ((length = pStream.read(btBuffer)) != -1)
				out.write(btBuffer, 0, length);
		} catch (Exception ex) {
			try {
				response.sendError(conn.getResponseCode());
			} catch (Exception e) {
				response.sendError(500);
			}
			if (debug) {
				System.out.println("适配器转发的服务发生错误！");
				System.out.println(ex.getMessage());
			}
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
			if (pStream != null)
				pStream.close();
			if (conn != null)
				conn.disconnect();
		}
	}

	private String getRealUrl(HttpServletRequest request)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();
		String serviceAgent = servletPath.substring(1);
		String queryString = request.getQueryString();
		
		if (queryString == null || "".equals(queryString.trim())){
			queryString = key;
		}else{
			queryString = URLDecoder.decode(queryString,"UTF-8");
			queryString += "&"+key;
		}	

		String serviceUrl = getInitParameter(serviceAgent);
		if ("".equalsIgnoreCase(serviceUrl) || "null".equalsIgnoreCase(serviceUrl) || serviceUrl == null) {
			String uri = request.getRequestURI();
			String serviceAgentTmp = servletPath.substring(1) + "/*";
			String serviceEnd = uri.substring(uri.indexOf(servletPath) + 4);
			serviceUrl = getInitParameter(serviceAgentTmp);
			if (serviceUrl == null)
				System.out.println("初始化参数" + serviceAgent + "或"+ serviceAgentTmp + "没找到");
			else
				serviceUrl = serviceUrl.substring(0, serviceUrl.length() - 2)+ serviceEnd;
		}

		if (debug)
			System.out.println("redirect to : " + serviceUrl + "?" + queryString);
		return serviceUrl + "?" + queryString;
	}
	
	private String readXMLFromRequestBody(HttpServletRequest request) {
		StringBuffer strBuf = new StringBuffer();
		try {
			BufferedReader reader = request.getReader();
			String line = null;
			while ((line = reader.readLine()) != null)
				strBuf.append(line).append("\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strBuf.toString();
	}
}