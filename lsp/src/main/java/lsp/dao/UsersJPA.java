/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lsp.dao;

import javax.persistence.EntityManager;
import lsp.entity.Users;

/**
 *
 * @author Alexandr
 */
public class UsersJPA extends JPA {
    
     public String getLogin(String login, String password) {
        EntityManager manager = getManager();
        try {
            Users usr = (Users) manager.createQuery("SELECT u FROM Users u WHERE u.login = :login AND u.password = :password")
                    .setParameter("login", login).setParameter("password", password).getSingleResult();
            return usr.getSalt();
        } catch (Exception ex) {
            return null;
        } finally {
            manager.close();
        }
    }
}
