package com.test5.repositories;

import com.test5.dao.UsersDAO;
import com.test5.pojo.User;

public class RegisterRepositories implements IRegisterRepositories {

    private UsersDAO usersDAO = null;

    public RegisterRepositories(){
        this.usersDAO = new UsersDAO();
    }


    @Override
    public void addUser(User user){
        usersDAO.addUser(user);
    }

    @Override
    public boolean usernameExists(String username) {
        return usersDAO.usernameExists(username);
    }


}
