package br.com.arekushi.kyuubi.model;

import android.net.Uri;

public class User {
    private String email;
    private String name;
    private Uri image;

    public User(String email, String name, Uri image) {
        this.email = email;
        this.name = name;
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Uri getImage() {
        return image;
    }
}
