package com.epam.jwd.model;

public class Picture implements DBEntity {
    private Long id;
    private String pictureURL;
    private String pictureName;

    public Picture(Long id, String pictureURL, String pictureName) {
        this.id = id;
        this.pictureURL = pictureURL;
        this.pictureName = pictureName;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", pictureURL='" + pictureURL + '\'' +
                ", pictureName='" + pictureName + '\'' +
                '}';
    }
}
