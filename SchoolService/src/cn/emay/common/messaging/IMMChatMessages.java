package cn.emay.common.messaging;

import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.easemob.server.example.comm.Constants;
import com.easemob.server.example.comm.HTTPMethod;
import com.easemob.server.example.httpclient.utils.HTTPClientUtils;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class IMMChatMessages extends IMBase{
	private static final Logger LOGGER = Logger.getLogger(IMMChatMessages.class);
	private static final String APPKEY = Constants.APPKEY;
	  /**
		 * 获取聊天消息
		 * 
		 * @param queryStrNode
		 *
		 */
		public static ObjectNode getChatMessages(ObjectNode queryStrNode) {

			ObjectNode objectNode = factory.objectNode();

			// check appKey format
			if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
				LOGGER.error("Bad format of Appkey: " + APPKEY);

				objectNode.put("message", "Bad format of Appkey");

				return objectNode;
			}

			try {

				String rest = "";
				if (null != queryStrNode && queryStrNode.get("ql") != null && !StringUtils.isEmpty(queryStrNode.get("ql").asText())) {
					rest = "ql="+ java.net.URLEncoder.encode(queryStrNode.get("ql").asText(), "utf-8");
				}
				if (null != queryStrNode && queryStrNode.get("limit") != null && !StringUtils.isEmpty(queryStrNode.get("limit").asText())) {
					rest = rest + "&limit=" + queryStrNode.get("limit").asText();
				}
				if (null != queryStrNode && queryStrNode.get("cursor") != null && !StringUtils.isEmpty(queryStrNode.get("cursor").asText())) {
					rest = rest + "&cursor=" + queryStrNode.get("cursor").asText();
				}
			
				URL chatMessagesUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/chatmessages?" + rest);
				
				objectNode = HTTPClientUtils.sendHTTPRequest(chatMessagesUrl, credential, null, HTTPMethod.METHOD_GET);

			} catch (Exception e) {
				e.printStackTrace();
			}

			return objectNode;
		}
}
