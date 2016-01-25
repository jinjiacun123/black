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
 * �������˷���
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
				//����΢�ź��ѵ�����
				WeiXinShareContent weixinContent = new WeiXinShareContent();
		        weixinContent.setShareContent(content);
		        weixinContent.setTitle(title);
		        weixinContent.setTargetUrl(url);
		        weixinContent.setShareMedia(umImage);
		        mController.setShareMedia(weixinContent);
		        
		        // ��������Ȧ���������
		        CircleShareContent circleMedia = new CircleShareContent();
		        circleMedia.setShareContent(content);
		        circleMedia.setTitle(title);
		        circleMedia.setTargetUrl(url);
		        circleMedia.setShareMedia(umImage);
		        mController.setShareMedia(circleMedia);
		        
		        //����qq���ѵ�����
		        QZoneShareContent qzone = new QZoneShareContent();
		        qzone.setShareContent(content);
		        qzone.setTitle(title);
		        qzone.setTargetUrl(url);
		        qzone.setShareMedia(umImage);
		        mController.setShareMedia(qzone);
		        
		        //�������˵�����
		       // SinaShareContent sinaContent = new SinaShareContent();
		        //sinaContent.setShareContent(content);
		       // sinaContent.setTargetUrl(url);
		        //sinaContent.setTitle(title);
		        //UMImage sinaimage = new UMImage(context, bitmap);
		        //sinaContent.setShareMedia(sinaimage);
		        //mController.setShareMedia(sinaContent);
		}
		 /**
	     * @�������� : ���QQƽ̨֧�� QQ��������ݣ� �����������ͣ� �����������֡�ͼƬ�����֡���Ƶ. ����˵�� : title, summary,
	     *       image url�б�����������һ��, targetUrl��������,��ҳ��ַ������"http://"��ͷ . title :
	     *       Ҫ������� summary : Ҫ��������ָ��� image url : ͼƬ��ַ [������������������дһ��] targetUrl
	     *       : �û�����÷���ʱ��ת����Ŀ���ַ [����] ( ������д��Ĭ������Ϊ������ҳ )
	     * @return
	     */
	    public static void addQQQZonePlatform(Context context) {
	        // ���QQ֧��, ��������QQ�������ݵ�target url
	        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler((Activity) context,
	                UMStaticConstant.QQAPPID, UMStaticConstant.QQAPPKEY);
//	        qqSsoHandler.setTargetUrl("www.baidu.com");
	        qqSsoHandler.addToSocialSDK();

	        // ���QZoneƽ̨
	        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler((Activity) context, UMStaticConstant.QQAPPID, UMStaticConstant.QQAPPKEY);
	        qZoneSsoHandler.addToSocialSDK();
	    }

		 /**
	     * @�������� : ���΢��ƽ̨����
	     * @return
	     */
	    public static void addWXPlatform(Context context) {
	        // ע�⣺��΢����Ȩ��ʱ�򣬱��봫��appSecret
	        // wx967daebe835fbeac������΢�ſ���ƽ̨ע��Ӧ�õ�AppID, ������Ҫ�滻����ע���AppID
	        // ���΢��ƽ̨
	        UMWXHandler wxHandler = new UMWXHandler(context, UMStaticConstant.WXAPPID, UMStaticConstant.WXAPPKEY);
	        wxHandler.addToSocialSDK();

	        // ֧��΢������Ȧ
	        UMWXHandler wxCircleHandler = new UMWXHandler(context,  UMStaticConstant.WXAPPID, UMStaticConstant.WXAPPKEY);
	        wxCircleHandler.setToCircle(true);
	        wxCircleHandler.addToSocialSDK();
	        
	    }
}
