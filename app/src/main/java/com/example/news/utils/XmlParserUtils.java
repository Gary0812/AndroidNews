package com.example.news.utils;

import android.util.Xml;

import com.example.news.model.NewsVo;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class XmlParserUtils {

    public static List<NewsVo> parserXml(InputStream in) {
        List<NewsVo> list = null;
        NewsVo nv = null;
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(in, "UTF-8");
            int type = parser.getEventType();

            while (type != XmlPullParser.END_DOCUMENT) {
                switch (type) {
                    case XmlPullParser.START_TAG:
                        if ("channel".equals(parser.getName())) {
                            list = new ArrayList<NewsVo>();
                        } else if ("item".equals(parser.getName())) {
                            nv = new NewsVo();
                        } else if ("title".equals(parser.getName())) {

                            nv.setTitle(parser.nextText());

                        } else if ("link".equals(parser.getName())) {
                            nv.setLink(parser.nextText());
                        } else if ("pubDate".equals(parser.getName())) {
                            nv.setPubDate(parser.nextText());
                        } else if ("author".equals(parser.getName())) {
                            nv.setAuthor(parser.nextText());
                        } else if ("description".equals(parser.getName())) {
                            nv.setDescription(parser.nextText());
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if ("item".equals(parser.getName())) {
                            list.add(nv);
                        }

                        break;
                    default:
                        break;

                }
                type = parser.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public static String getMacFromHardware() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";


    }
}