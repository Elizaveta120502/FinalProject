package com.epam.jwd.service.impl;

import com.epam.jwd.dao.AccountDAO;
import com.epam.jwd.dao.PictureDAO;
import com.epam.jwd.service.PictureService;

public class PictureServiceImpl implements PictureService {

    private final PictureDAO pictureDAO;

    public PictureServiceImpl(PictureDAO pictureDAO) {
        this.pictureDAO = pictureDAO;
    }
}
