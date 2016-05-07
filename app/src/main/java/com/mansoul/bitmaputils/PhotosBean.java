package com.mansoul.bitmaputils;

import java.util.List;

/**
 * Created by Mansoul on 16/5/6.
 */
public class PhotosBean {

    public NewsData data;

    public class NewsData {
        public List<PhotosData> news;
    }

    public class PhotosData {
        public int id;
        public String listimage;
        public String title;
    }

}
