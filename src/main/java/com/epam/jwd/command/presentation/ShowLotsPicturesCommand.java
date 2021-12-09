//package com.epam.jwd.command.presentation;
//
//import com.epam.jwd.command.Command;
//import com.epam.jwd.command.CommandRequest;
//import com.epam.jwd.command.CommandResponse;
//import com.epam.jwd.controller.RequestFactory;
//import com.epam.jwd.dao.impl.DAOFactory;
//import com.epam.jwd.model.Picture;
//import com.epam.jwd.service.EntityService;
//import com.epam.jwd.service.ServiceFactory;
//
//import java.util.List;
//
//public enum ShowLotsPicturesCommand implements Command {
//    INSTANCE(ServiceFactory.getInstance().serviceFor(Picture.class),
//            RequestFactory.getInstance());
//
//    private static List<Picture> PICTURES = null;
//
//    static {
//        try {
//            PICTURES = DAOFactory.getInstance().getLotDAO().readAll();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static final String PICTURES_ATTRIBUTE_NAME = "pictures";
//
//    private static final CommandResponse FORWARD_TO_LOTS_PAGE = new CommandResponse() {
//        @Override
//        public boolean isRedirect() {
//            return false;
//        }
//
//        @Override
//        public String getPath() {
//            return "/WEB-INF/jsp/pictures.jsp";
//        }
//    };
//
//    private final EntityService<Picture> service;
//    private final RequestFactory requestFactory;
//
//    ShowLotsPicturesCommand (EntityService<Picture> service, RequestFactory requestFactory) {
//        this.service = service;
//        this.requestFactory = requestFactory;
//    }
//
//    @Override
//    public CommandResponse execute(CommandRequest request) {
//
//        request.addAttributeToJsp(PICTURES_ATTRIBUTE_NAME,PICTURES);
//        return FORWARD_TO_LOTS_PAGE;
//    }
//}
