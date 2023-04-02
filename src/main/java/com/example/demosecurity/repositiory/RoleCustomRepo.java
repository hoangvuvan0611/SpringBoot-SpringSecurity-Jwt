package com.example.demosecurity.repositiory;

import com.example.demosecurity.entity.Role;
import com.example.demosecurity.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleCustomRepo {       //Hàm trả về các giá trị Role của từng user
    @PersistenceContext
    private EntityManager entityManager;

    public List<Role> getRole(User user){
        StringBuilder sql  = new StringBuilder()
                //truy vấn tới những role_name của user được truyền vào
                .append("select r.name as name from users u join\n" +
                        "             users_role ur on u.id = ur.user_id join\n" +
                        "                         role r on r.id = ur.role_id");
        sql.append(" where 1 = 1");
        if(user.getEmail() !=null){
            sql.append(" and email = :email");
        }

        NativeQuery<Role> query = ((Session) entityManager.getDelegate()).createNativeQuery(sql.toString());
        if(user.getName() != null){
            query.setParameter("email", user.getEmail());
        }
        query.addScalar("name", StandardBasicTypes.STRING);
        query.setResultTransformer(Transformers.aliasToBean(Role.class));
        System.out.println(query.list());
        return query.list();
    }
}
