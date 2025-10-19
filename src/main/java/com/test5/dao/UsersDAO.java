package com.test5.dao;

import com.test5.pojo.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class UsersDAO {
    private Configuration configuration = null;
    private SessionFactory sessionFactory = null;

    public UsersDAO() {
        configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
    }


    // hàm kiểm tra username có tồn tại không
    public boolean usernameExists(String username) {
        Session session = null;
        User user = null;
        try {
            session = sessionFactory.openSession();
            Query<User> query = session.createQuery("FROM User WHERE username = :username", User.class);
            query.setParameter("username", username);
            boolean exists = !query.getResultList().isEmpty();
            return exists;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


    // hàm kiểm tra username và password có tồn tại trong database
    public boolean checkLogin(String username, String password) {
        Session session = null;
        boolean exists = false;

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            String hql = "SELECT COUNT(u) FROM User u WHERE u.username = :username AND u.password = :password";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("username", username);
            query.setParameter("password", password);

            Long count = query.uniqueResult();

            if (count != null && count > 0) {
                exists = true;
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return exists;
    }


    // hàm thêm user mới
    public void addUser(User user) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            session.persist(user);
            session.getTransaction().commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    // tìm kiếm người dùng bằng id
    public User findUserById(int id) {
        Session session = null;
        User user = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            // Hibernate sẽ tìm theo khóa chính (id)
            user = session.get(User.class, id);

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }


    // tìm kiếm người dùng bằng username
    public User findUserByUsername(String username) {
        Session session = null;
        User user = null;

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();

            String hql = "FROM User u WHERE u.username = :username";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("username", username);

            user = query.uniqueResult(); // Lấy 1 user hoặc null nếu không có

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }


//    public User findUserById(int id) {
//        Session session = null;
//        User user = null;
//        try {
//            session = sessionFactory.openSession();
//            session.beginTransaction();
//
//            String hql = "FROM User u WHERE u.id = :id";
//            Query<User> query = session.createQuery(hql, User.class);
//            query.setParameter("id", id);
//            user = query.uniqueResult();
//
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            if (session != null && session.getTransaction().isActive()) {
//                session.getTransaction().rollback();
//            }
//            e.printStackTrace();
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//        return user;
//    }


}
