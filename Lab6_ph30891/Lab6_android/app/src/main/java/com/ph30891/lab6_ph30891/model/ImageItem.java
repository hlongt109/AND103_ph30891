package com.ph30891.lab6_ph30891.model;

import java.io.File;

public class ImageItem {
    private File url;

    public ImageItem(File url) {
        this.url = url;
    }

    public ImageItem() {
    }

    public File getUrl() {
        return url;
    }

    public ImageItem setUrl(File url) {
        this.url = url;
        return this;
    }
}
