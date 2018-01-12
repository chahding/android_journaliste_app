package com.reda.touk.myapp.helpers;


import com.reda.touk.myapp.models.Video;

import java.util.List;

public interface OnNewVideoListener {
    void callback(List<Video> videos);
}