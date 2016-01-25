package com.example.black.lib.umeng;

import android.app.Activity;
import android.content.Context;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

/**
 * 处理友盟分享
 * @author Admin
 *
 */
public class UMShareManager {
	public static void  UMSendShare(Context context,UMSocialService mController,
			  String title,String url,UMImage umImage,String content){
			  mController.getConfig().setSsoHandler(new SinaSsoHandler());
			  addQQQZonePlatform(context);
			  addWXPlatform(context);
			  setShareComtent(context, mController, title, url, umImage, content);
			  //mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.QZONE,SHARE_MEDIA.SINA);
			  mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.QZONE);
			  mController.openShare((Activity) context, false);
		}
		
		public static void setShareComtent(Context context,UMSocialService mController,
				String title,String url,UMImage umImage,String content){
				//设置微信好友的内容
				WeiXinShareContent weixinContent = new WeiXinShareContent();
		        weixinContent.setShareContent(content);
		        weixinContent.setTitle(title);
		        weixinContent.setTargetUrl(url);
		        weixinContent.setShareMedia(umImage);
		        mController.setShareMedia(weixinContent);
		        
		        // 设置朋友圈分享的内容
		        CircleShareContent circleMedia = new CircleShareContent();
		        circleMedia.setShareContent(content);
		        circleMedia.setTitle(title);
		        circleMedia.setTargetUrl(url);
		        circleMedia.setShareMedia(umImage);
		        mController.setShareMedia(circleMedia);
		        
		        //设置qq好友的内容
		        QZoneShareContent qzone = new QZoneShareContent();
		        qzone.setShareContent(content);
		        qzone.setTitle(title);
		        qzone.setTargetUrl(url);
		        qzone.setShareMedia(umImage);
		        mController.setShareMedia(qzone);
		        
		        //设置新浪的内容
		       // SinaShareContent sinaContent = new SinaShareContent();
		        //sinaContent.setShareContent(content);
		       // sinaContent.setTargetUrl(url);
		        //sinaContent.setTitle(title);
		        //UMImage sinaimage = new UMImage(context, bitmap);
		        //sinaContent.setShareMedia(sinaimage);
		        //mController.setShareMedia(sinaContent);
		}
		 /**
	     * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
	     *       image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 . title :
	     *       要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个] targetUrl
	     *       : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
	     * @return
	     */
	    public static void addQQQZonePlatform(Context context) {
	        // 添加QQ支持, 并且设置QQ分享内容的target url
	        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler((Activity) context,
	                UMStaticConstant.QQAPPID, UMStaticConstant.QQAPPKEY);
//	        qqSsoHandler.setTargetUrl("www.baidu.com");
	        qqSsoHandler.addToSocialSDK();

	        // 添加QZone平台
	        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler((Activity) context, UMStaticConstant.QQAPPID, UMStaticConstant.QQAPPKEY);
	        qZoneSsoHandler.addToSocialSDK();
	    }

		 /**
	     * @功能描述 : 添加微信平台分享
	     * @return
	     */
	    public static void addWXPlatform(Context context) {
	        // 注意：在微信授权的时候，必须传递appSecret
	        // wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
	        // 添加微信平台
	        UMWXHandler wxHandler = new UMWXHandler(context, UMStaticConstant.WXAPPID, UMStaticConstant.WXAPPKEY);
	        wxHandler.addToSocialSDK();

	        // 支持微信朋友圈
	        UMWXHandler wxCircleHandler = new UMWXHandler(context,  UMStaticConstant.WXAPPID, UMStaticConstant.WXAPPKEY);
	        wxCircleHandler.setToCircle(true);
	        wxCircleHandler.addToSocialSDK();
	        
	    }
}
