/**
 * 
 */
package org.bjit.serviceadapter.agent;

import java.util.EventObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author administrator
 * 
 */
public class DispatchEvent extends EventObject {
	private String initialUrl = "";
	private String servletPath = "";
	private String dispatchedUrl = "";
	private int result = 0;
	private long startTick;
	private long endTick;
	private String message = "";
	private HttpServletRequest request;

	public DispatchEvent(Object source, String initialUrl, String servletPath,
			String dispatchedUrl, int result, long startTick, long endTick,
			String message, HttpServletRequest request) {
		super(source);
		this.initialUrl = initialUrl;
		this.servletPath = servletPath;
		this.dispatchedUrl = dispatchedUrl;
		this.result = result;
		this.startTick = startTick;
		this.endTick = endTick;
		this.message = message;
		this.request = request;
	}

	public String getInitialUrl() {
		return initialUrl;
	}

	public String getDispatchedUrl() {
		return dispatchedUrl;
	}

	public int getResult() {
		return result;
	}

	public long getStartTick() {
		return startTick;
	}

	public long getEndTick() {
		return endTick;
	}

	public String getMessage() {
		return message;
	}

	public String getServletPath() {
		return servletPath;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

}
