package com.epam.jwd.dao;

import com.epam.jwd.model.Picture;

import java.util.Optional;

public interface PictureDAO extends DBEntityDAO<Picture>{


    Optional<Picture> findPictureByName(String name);
}
