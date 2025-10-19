package com.test5.repositories;

import com.test5.dao.UsersDAO;
import com.test5.pojo.User;

public class UsersRepositories implements IUsersRepositories{

    private UsersDAO usersDAO = null;

    public UsersRepositories(){
        this.usersDAO = new UsersDAO();
    }

    @Override
    public User findUserById(int id){
        return usersDAO.findUserById(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return usersDAO.findUserByUsername(username);
    }

    @Override
    public boolean checkLogin(String username, String password){
        return usersDAO.checkLogin(username,password);
    }

}
