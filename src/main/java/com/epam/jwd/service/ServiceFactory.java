package com.epam.jwd.service;

import com.epam.jwd.model.Account;
import com.epam.jwd.model.DBEntity;
import com.epam.jwd.service.impl.ServiceFactoryImpl;

public interface ServiceFactory {

    <T extends DBEntity> EntityService<T> serviceFor(Class<T> modelClass);

    default AccountService userService() {
        return (AccountService ) serviceFor(Account.class);
    }

    static ServiceFactoryImpl getInstance() {
        return ServiceFactoryImpl.INSTANCE;
    }
}
