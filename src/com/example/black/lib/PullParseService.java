package com.example.black.lib;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import com.example.black.lib.model.Hr_Region;
import com.example.black.lib.model.VersionCode;

import android.util.Xml;


/**
 * pull����
 * 
 * @author Admin
 * 
 */
public class PullParseService {
	// �汾��
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

		// �����ļ�
		public static List<Hr_Region> getHr_Region(InputStream inputStream)
				throws Exception {
			List<Hr_Region> list = null;
			Hr_Region region = null;
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(inputStream, "UTF-8");
			int event = parser.getEventType();// ������һ���¼�
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:// �жϵ�ǰ�¼��Ƿ����ĵ���ʼ�¼� :
					list = new ArrayList<Hr_Region>();
					break;
				case XmlPullParser.START_TAG:// �жϵ�ǰ�¼��Ƿ��Ǳ�ǩԪ�ؿ�ʼ�¼�
					if ("hr_region".equals(parser.getName())) {// �жϿ�ʼ��ǩԪ���Ƿ���hr_region
						region = new Hr_Region();
					}
					if (region != null) {
						if ("region_id".equals(parser.getName())) {// �жϿ�ʼ��ǩԪ���Ƿ���region_id
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
				case XmlPullParser.END_TAG:// �жϵ�ǰ�¼��Ƿ��Ǳ�ǩԪ�ؽ����¼�
					if ("hr_region".equals(parser.getName())) {// �жϽ�����ǩԪ���Ƿ���hr_region
						list.add(region);
						region = null;
					}
					break;
				}
				event = parser.next();// ������һ��Ԫ�ز�������Ӧ�¼�
			}
			return list;
		}
}
