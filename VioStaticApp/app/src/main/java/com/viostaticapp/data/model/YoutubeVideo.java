package com.viostaticapp.data.model;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.firebase.Timestamp;

import org.apache.commons.text.StringEscapeUtils;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import lombok.Data;

@Data
public class YoutubeVideo implements Serializable {

    private String id;

    private String title;

    private String description;

    private String thumbnail;

    private String videoUrl;

    private Channel channel;

    private String publishedAt;

    public String getPublishedAt() {

        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date date = dateFormat.parse(publishedAt);
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = formatter.format(date);

            return dateStr;

        } catch (Exception e) {
            return publishedAt;
        }

    }

    public YoutubeVideo() {
    }

    public String getTitle() {

        return StringEscapeUtils.unescapeHtml4(title);
    }
}
