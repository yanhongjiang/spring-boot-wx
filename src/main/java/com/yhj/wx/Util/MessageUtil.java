package com.yhj.wx.Util;

import com.thoughtworks.xstream.XStream;
import com.yhj.wx.po.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by Administrator on 2017/10/18.
 */
public class MessageUtil {
    /**
     * 返回消息类型：文本
     */
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";

    /**
     * 返回消息类型：音乐
     */
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

    /**
     * 返回消息类型：图文
     */
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";

    /**
     * 请求消息类型：文本
     */
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";

    /**
     * 请求消息类型：图片
     */
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

    /**
     * 请求消息类型：链接
     */
    public static final String REQ_MESSAGE_TYPE_LINK = "link";

    /**
     * 请求消息类型：地理位置
     */
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

    /**
     * 请求消息类型：音频
     */
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

    /**
     * 请求消息类型：推送
     */
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";

    /**
     * 事件类型：subscribe(订阅)
     */
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

    /**
     * 事件类型：unsubscribe(取消订阅)
     */
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

    /**
     * 事件类型：CLICK(自定义菜单点击事件)
     */
    public static final String EVENT_TYPE_CLICK = "CLICK";

    public static final String MESSAGE_TEXT = "text";
    public static final String MESSAGE_NEWS = "news";
    public static final String MESSAGE_IMAGE = "image";
    public static final String MESSAGE_VOICE = "voice";
    public static final String MESSAGE_MUSIC = "music";
    public static final String MESSAGE_VIDEO = "video";
    public static final String MESSAGE_LINK = "link";
    public static final String MESSAGE_LOCATION = "location";
    public static final String MESSAGE_EVNET = "event";
    public static final String MESSAGE_SUBSCRIBE = "subscribe";
    public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
    public static final String MESSAGE_CLICK = "CLICK";
    public static final String MESSAGE_VIEW = "VIEW";
    public static final String MESSAGE_SCANCODE= "scancode_push";

    /**
     * 文本消息对象转换成xml
     *
     * @param textMessage 文本消息对象
     * @return xml
     */
    public static String textMessageToXml(TextMessage textMessage){
        XStream xstream = new XStream();
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }

    public static Map<String, String> xmlToMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader();

        InputStream ins = null;
        try {
            ins = request.getInputStream();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Document doc = null;
        try {
            doc = reader.read(ins);
            Element root = doc.getRootElement();
            List<Element> list = root.elements();

            for (Element e : list) {
                map.put(e.getName(), e.getText());
            }

            return map;
        } catch (DocumentException e1) {
            e1.printStackTrace();
        } finally {
            if (ins!=null){
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 组装文本消息
     * @param toUserName
     * @param fromUserName
     * @param content
     * @return
     */
    public static String initText(String toUserName,String fromUserName,String content){
        TextMessage text = new TextMessage();
        text.setFromUserName(toUserName);
        text.setToUserName(fromUserName);
        text.setMsgType(MessageUtil.MESSAGE_TEXT);
        text.setCreateTime(new Date().getTime());
        text.setContent(content);
        return textMessageToXml(text);
    }

    /**
     * 主菜单
     * @return
     */
    public static String menuText(){
        StringBuffer sb = new StringBuffer();
        sb.append("欢迎您的关注，请按照菜单提示进行操作：\n\n");
        sb.append("1、公众号介绍\n");
        sb.append("2、介绍\n");
        sb.append("3、词组翻译\n\n");
        sb.append("回复？调出此菜单。");
        return sb.toString();
    }

    public static String firstMenu(){
        StringBuffer sb = new StringBuffer();
        sb.append("本套课程介绍微信公众号开发，主要涉及公众号介绍、编辑模式介绍、开发模式介绍等");
        return sb.toString();
    }

    public static String secondMenu(){
        StringBuffer sb = new StringBuffer();
        sb.append("慕课网是垂直的互联网IT技能免费学习网站。以独家视频教程、在线编程工具、学习计划、问答社区为核心特色。在这里，你可以找到最好的互联网技术牛人，也可以通过免费的在线公开视频课程学习国内领先的互联网IT技术。");
        sb.append("慕课网课程涵盖前端开发、PHP、Html5、Android、iOS、Swift等IT前沿技术语言，包括基础课程、实用案例、高级分享三大类型，适合不同阶段的学习人群。以纯干货、短视频的形式为平台特点，为在校学生、职场白领提供了一个迅速提升技能、共同分享进步的学习平台。");
        return sb.toString();
    }

    public static String threeMenu(){
        StringBuffer sb = new StringBuffer();
        sb.append("词组翻译使用指南\n\n");
        sb.append("使用示例：\n");
        sb.append("翻译足球\n");
        sb.append("翻译中国足球\n");
        sb.append("翻译football\n\n");
        sb.append("回复？显示主菜单。");
        return sb.toString();
    }
    /**
     * 图文消息转为xml
     * @param newsMessage
     * @return
     */
    public static String newsMessageToXml(NewsMessage newsMessage){
        XStream xstream = new XStream();
        xstream.alias("xml", newsMessage.getClass());
        xstream.alias("item", new News().getClass());
        return xstream.toXML(newsMessage);
    }

    /**
     * 图片消息转为xml
     * @param imageMessage
     * @return
     */
    public static String imageMessageToXml(ImageMessage imageMessage){
        XStream xstream = new XStream();
        xstream.alias("xml", imageMessage.getClass());
        return xstream.toXML(imageMessage);
    }

    /**
     * 音乐消息转为xml
     * @param musicMessage
     * @return
     */
    public static String musicMessageToXml(MusicMessage musicMessage){
        XStream xstream = new XStream();
        xstream.alias("xml", musicMessage.getClass());
        return xstream.toXML(musicMessage);
    }
    /**
     * 图文消息的组装
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public static String initNewsMessage(String toUserName,String fromUserName){
        String message = null;
        List<News> newsList = new ArrayList<News>();
        NewsMessage newsMessage = new NewsMessage();

        News news = new News();
        news.setTitle("个人公众号开发");
        news.setDescription("本公众号正处于开发阶段，现在已完成的功能有，文本回复，图文回复，图片回退，音乐回复，还有在线翻译功能。回复功能暂时都是回复同一条数据，后期增加动态返回数据。现在相对比较完整的是翻译功能，你可以进行英文翻译成中文，中文翻译成英文，也支持日语的翻译。由于系统默认中文翻译为英文如果需要将中文翻译成日文的话，可以在中文后面添加'JP'字符串，这样后台将会帮你把中文翻译成日文并返回给你。");
        news.setPicUrl(ImageUtil.PROJECT_ROOT_IMAGE_URL+"/2.jpg");
       // news.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc5d25b23f0cf5b9e&redirect_uri=http%3a%2f%2fyhj.tunnel.qydev.com%2fadvise%2ftoIndexAdvise&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
        //news.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc5d25b23f0cf5b9e&redirect_uri=http://yanhj23.imwork.net:30684/advise/toIndexAdvise&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
        news.setUrl(AuthorizeUtil.getSnsapiBaseURL("/advise/toIndexAdvise"));
        newsList.add(news);

        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MESSAGE_NEWS);
        newsMessage.setArticles(newsList);
        newsMessage.setArticleCount(newsList.size());

        message = newsMessageToXml(newsMessage);
        return message;
    }

    /**
     * 组装图片消息
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public static String initImageMessage(String toUserName,String fromUserName){
        String message = null;
        Image image = new Image();
        image.setMediaId("h3qvx2UpXdHplzPwxgQV_r6566YuiJjBsfW5qoDbC69_S7RdnMydhizT4F1UQk35");
        ImageMessage imageMessage = new ImageMessage();
        imageMessage.setFromUserName(toUserName);
        imageMessage.setToUserName(fromUserName);
        imageMessage.setMsgType(MESSAGE_IMAGE);
        imageMessage.setCreateTime(new Date().getTime());
        imageMessage.setImage(image);
        message = imageMessageToXml(imageMessage);
        return message;
    }

    /**
     * 组装音乐消息
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public static String initMusicMessage(String toUserName,String fromUserName){
        String message = null;
        Music music = new Music();
        music.setThumbMediaId("WsHCQr1ftJQwmGUGhCP8gZ13a77XVg5Ah_uHPHVEAQuRE5FEjn-DsZJzFZqZFeFk");
        music.setTitle("爱的故事上集");
        music.setDescription("");
        music.setMusicUrl("http://yhj.tunnel.qydev.com/resource/love.mp3");
        music.setHQMusicUrl("http://yhj.tunnel.qydev.com/resource/love.mp3");

        MusicMessage musicMessage = new MusicMessage();
        musicMessage.setFromUserName(toUserName);
        musicMessage.setToUserName(fromUserName);
        musicMessage.setMsgType(MESSAGE_MUSIC);
        musicMessage.setCreateTime(new Date().getTime());
        musicMessage.setMusic(music);
        message = musicMessageToXml(musicMessage);
        return message;
    }
}
