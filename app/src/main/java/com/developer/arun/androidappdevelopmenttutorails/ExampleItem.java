package com.developer.arun.androidappdevelopmenttutorails;

public class ExampleItem {
    private String video_id;
    private String video_title;
    private String url;

    public ExampleItem(String video_id, String video_title, String url) {
        this.video_id = video_id;
        this.video_title = video_title;
        this.url = url;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
