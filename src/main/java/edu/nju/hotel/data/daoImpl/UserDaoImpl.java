package edu.nju.hotel.data.daoImpl;

import edu.nju.hotel.data.dao.BaseDao;
import edu.nju.hotel.data.dao.UserDao;
import edu.nju.hotel.data.model.User;
import edu.nju.hotel.data.repository.UserRepository;
import edu.nju.hotel.data.util.VerifyResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by zhouxiaofan on 2017/1/22.
 */
@Repository
@Transactional
public class UserDaoImpl implements UserDao {
    @Autowired
    BaseDao baseDao;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserRepository up;

    @Override
    public void add(User user) {
        baseDao.insert(user);
    }

    @Override
    public List<User> findAll() {
        Query q=em.createQuery("select u from User u");
        List<User> list=q.getResultList();
        return list;
    }

    @Override
    public User getUserByName(String username) {
        Query q=em.createQuery("select u from User u where u.name =?1");
        q.setParameter(1,username);
        return (User) q.getSingleResult();
    }

    @Override
    public VerifyResult verifyUser(int id, String password) {
        Query query = em.createQuery( "select psw from User where id = ?1" );
        query.setParameter(1,id);
        String psw = (String) query.getSingleResult();
        if( psw==null ){
            return VerifyResult.NOTEXIST;
        }
        else if(password.equals(psw)){
            return VerifyResult.SUCCESS;
        }
        return VerifyResult.INCORRECT;
    }



    @Override
    public User getUserById(int id) {
        Query q=em.createQuery("select u from User u where u.id =?1");
        q.setParameter(1,id);
        User user= (User) q.getSingleResult();
        return user;
    }

    @Override
    public void deleteInBatch() {

    }

    @Override
    public void changeState(int id) {

    }

    @Override
    public void update(User userEntity) {
        em.merge(userEntity);
    }
}
