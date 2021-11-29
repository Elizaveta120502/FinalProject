package com.epam.jwd.service.impl;


import com.epam.jwd.dao.impl.DAOFactory;
import com.epam.jwd.model.DBEntity;
import com.epam.jwd.service.EntityService;
import com.epam.jwd.service.ServiceFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public enum ServiceFactoryImpl implements ServiceFactory {

    INSTANCE;
    private static final String SERVICE_NOT_FOUND = "Could not create service for %s class";
    private final Map<Class<?>, EntityService<?>> serviceByEntity = new ConcurrentHashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public <T extends DBEntity> EntityService<T> serviceFor(Class<T> modelClass) {
        return (EntityService<T>) serviceByEntity
                .computeIfAbsent(modelClass, createServiceFromClass());
    }

    private Function<Class<?>, EntityService<?>> createServiceFromClass() {
        return clazz -> {
            final String className = clazz.getSimpleName();
            switch (className) {
                case "Account":
                    return new AccountServiceImpl(DAOFactory.getInstance().getAccountDAO());
                default:
                    throw new IllegalArgumentException(String.format(SERVICE_NOT_FOUND, className));
            }
        };
    }
}

