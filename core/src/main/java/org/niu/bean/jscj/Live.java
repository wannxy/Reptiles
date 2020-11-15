package org.niu.bean.jscj;

import java.util.Date;

public class Live {
    private int id;
    private String content;
    private String content_prefix;
    private String link;
    private String attribute;
    private int up_counts;
    private int down_counts;
    private Long date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent_prefix() {
        return content_prefix;
    }

    public void setContent_prefix(String content_prefix) {
        this.content_prefix = content_prefix;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public int getUp_counts() {
        return up_counts;
    }

    public void setUp_counts(int up_counts) {
        this.up_counts = up_counts;
    }

    public int getDown_counts() {
        return down_counts;
    }

    public void setDown_counts(int down_counts) {
        this.down_counts = down_counts;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Live{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", content_prefix='" + content_prefix + '\'' +
                ", link='" + link + '\'' +
                ", attribute='" + attribute + '\'' +
                ", up_counts=" + up_counts +
                ", down_counts=" + down_counts +
                ", datetime=" + date +
                '}';
    }
}
