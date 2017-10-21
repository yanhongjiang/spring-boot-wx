package com.yhj.wx.service;

import com.yhj.wx.Util.MessageUtil;
import com.yhj.wx.Util.WeixinUtil;
import com.yhj.wx.po.TextMessage;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/18.
 */
@Service
public class WxServiceImpl implements WxService {
    @Override
    public String weixinPost(HttpServletRequest request) {
        String respMessage = null;
        try {

            // xml请求解析
            Map<String, String> map = MessageUtil.xmlToMap(request);

            // 发送方帐号（open_id）
            String fromUserName = map.get("FromUserName");
            // 公众帐号
            String toUserName = map.get("ToUserName");
            // 消息类型
            String msgType = map.get("MsgType");
            // 消息内容
            String content = map.get("Content");

            // 文本消息
          /*  if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                //这里根据关键字执行相应的逻辑，只有你想不到的，没有做不到的
                if (content.equals("xxx")) {

                }

                //自动回复
                TextMessage text = new TextMessage();
                text.setContent("the text is" + content);
                text.setToUserName(fromUserName);
                text.setFromUserName(toUserName);
                text.setCreateTime(new Date().getTime());
                text.setMsgType(msgType);

                respMessage = MessageUtil.textMessageToXml(text);

            }*/

            String message = null;
            if (MessageUtil.REQ_MESSAGE_TYPE_TEXT.equals(msgType)) {
                if ("1".equals(content)) {
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
                } else if ("2".equals(content)) {
                    message = MessageUtil.initNewsMessage(toUserName, fromUserName);
                } else if ("3".equals(content)) {
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.threeMenu());
                } else if ("?".equals(content) || "？".equals(content)) {
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
                } else if (content.startsWith("翻译")) {
                    String word = content.replaceAll("^翻译", "").trim();
                    if ("".equals(word)) {
                        message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.threeMenu());
                    } else {
                        message = MessageUtil.initText(toUserName, fromUserName, WeixinUtil.translate(word));
                    }
                }
            } else if (MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgType)) {
                String eventType = map.get("Event");
                if (MessageUtil.EVENT_TYPE_SUBSCRIBE.equals(eventType)) {
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
                } else if (MessageUtil.EVENT_TYPE_CLICK.equals(eventType)) {
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
                } else if (MessageUtil.MESSAGE_VIEW.equals(eventType)) {
                    String url = map.get("EventKey");
                    message = MessageUtil.initText(toUserName, fromUserName, url);
                } else if (MessageUtil.MESSAGE_SCANCODE.equals(eventType)) {
                    String key = map.get("EventKey");
                    message = MessageUtil.initText(toUserName, fromUserName, key);
                }
            } else if (MessageUtil.MESSAGE_LOCATION.equals(msgType)) {
                String label = map.get("Label");
                message = MessageUtil.initText(toUserName, fromUserName, label);
            }

            System.out.println(message);
            return message;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
