package com.example.black.lib;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import com.example.black.lib.model.Hr_Region;
import com.example.black.lib.model.VersionCode;

import android.util.Xml;


/**
 * pull解析
 * 
 * @author Admin
 * 
 */
public class PullParseService {
	// 版本号
		public static VersionCode getVersionCode(InputStream inputStream)
				throws Exception {
			VersionCode code = null;
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(inputStream, "UTF-8");
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					
					break;
				case XmlPullParser.START_TAG:
					if ("info".equals(parser.getName())) {
						
						code = new VersionCode();
					}
					if (code != null) {
						if ("versionCode".equals(parser.getName())) {
							
							code.setVersionCode(Integer.valueOf(parser.nextText()));
						} else if ("versionName".equals(parser.getName())) {
							
							code.setVersionName(parser.nextText());
						} else if ("label".equals(parser.getName())) {
							
							code.setLabel(parser.nextText());
						} else if ("path".equals(parser.getName())) {
							
							code.setPath(parser.nextText());
						}
					}
					break;
				case XmlPullParser.END_TAG:
					if ("info".equals(parser.getName())) {
						return code;
					}
					break;
				}
				event = parser.next();
			}
			return null;
		}

		// 城市文件
		public static List<Hr_Region> getHr_Region(InputStream inputStream)
				throws Exception {
			List<Hr_Region> list = null;
			Hr_Region region = null;
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(inputStream, "UTF-8");
			int event = parser.getEventType();// 产生第一个事件
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:// 判断当前事件是否是文档开始事件 :
					list = new ArrayList<Hr_Region>();
					break;
				case XmlPullParser.START_TAG:// 判断当前事件是否是标签元素开始事件
					if ("hr_region".equals(parser.getName())) {// 判断开始标签元素是否是hr_region
						region = new Hr_Region();
					}
					if (region != null) {
						if ("region_id".equals(parser.getName())) {// 判断开始标签元素是否是region_id
							region.setRegion_id(parser.nextText());
						} else if ("parent_id".equals(parser.getName())) {
							region.setParent_id(parser.nextText());
						} else if ("region_name".equals(parser.getName())) {
							region.setRegion_name(parser.nextText());
						} else if ("region_type".equals(parser.getName())) {
							region.setRegion_type(parser.nextText());
						} else if ("agency_id".equals(parser.getName())) {
							region.setAgency_id(parser.nextText());
						}
					}
					break;
				case XmlPullParser.END_TAG:// 判断当前事件是否是标签元素结束事件
					if ("hr_region".equals(parser.getName())) {// 判断结束标签元素是否是hr_region
						list.add(region);
						region = null;
					}
					break;
				}
				event = parser.next();// 进入下一个元素并触发相应事件
			}
			return list;
		}
}
