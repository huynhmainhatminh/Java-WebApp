package com.test5.repositories;

import com.test5.dao.UsersDAO;

public class LoginRepositories implements ILoginRepositories{

    private final UsersDAO usersDAO = new UsersDAO();

    @Override
    public boolean checkLogin(String username, String password) {
        return usersDAO.checkLogin(username, password);
    }

}
