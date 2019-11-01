package com.example.news;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.InputStream;
import java.util.ArrayList;
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


}
