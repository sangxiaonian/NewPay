package haipo.com.newpay.utils;

import android.text.TextUtils;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

public class XmlUtils {

	private static XmlUtils xmlUtils = null;
	private String reason="启用";

	private XmlUtils() {
	};

	public static XmlUtils getInstance() {
		if (xmlUtils == null) {
			xmlUtils = new XmlUtils();
		}
//
		return xmlUtils;
	}



	public static String getSpecialValue(String xmlString,String name){
		String value = null;
		
		if (TextUtils.isEmpty(xmlString)) {
			return null;
		}
		
		XmlPullParser pullParser = Xml.newPullParser();
	
		try {
			pullParser.setInput(new ByteArrayInputStream(xmlString.getBytes("UTF-8")), "UTF-8");
			int event = pullParser.getEventType();// 触发第一个事件
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
					case XmlPullParser.START_TAG:
						if (name.equals(pullParser.getName())) {
							value = pullParser.nextText();
						}
						break;
				}
				event = pullParser.next();

			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

	public static String mapToXml(Map<String,String> map){


		Iterator iterator = map.keySet().iterator();
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		while (iterator.hasNext()){
			String key = (String) iterator.next();
			String value  = map.get(key);
			sb.append("<").append(key).append(">");
			sb.append(value);
			sb.append("</").append(key).append(">");
		}
		sb.append("</xml>");

		return sb.toString();

	}



}
