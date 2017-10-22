package com.yhj.wx.controller;

import com.yhj.wx.Util.UserInfoUtil;
import com.yhj.wx.http.HttpsUtil;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrator on 2017/10/21.
 */
@Controller
@RequestMapping(value = "/advise")
public class AdviseController {
        private Logger logger = LoggerFactory.getLogger(getClass());

        public static final String WX_APPID = "wxc5d25b23f0cf5b9e";
        public static final String WX_APPSECRET = "d4624c36b6795d1d99dcf0547af5443d";

        //跳转到建议页面
        @RequestMapping("/toIndexAdvise")
        public String toIndexAdvise(@RequestParam(name = "code", required = false) String code,
                                    @RequestParam(name = "state") String state) {
            JSONObject jsonObject = UserInfoUtil.getUserInfo(code,state);
            return "advise";
        }
}
