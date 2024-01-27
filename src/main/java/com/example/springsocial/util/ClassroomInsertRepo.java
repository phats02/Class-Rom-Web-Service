//package com.example.springsocial.util;
//
//import com.example.springsocial.model.Classroom;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.transaction.Transactional;
//
//public class ClassroomInsertRepo {
//    @PersistenceContext
//    private EntityManager entityManager;
//    @Transactional
//    public void insertWithQuery(Classroom classroom) {
//        entityManager.createNativeQuery("insert into courses (_id,name, description,join_id,created_at, update_at,teachers ,students , owner ) " +
//                        " VALUES (?,?,?,?,?,?,?)")
//                .setParameter(1, classroom.getId())
//                .setParameter(2, classroom.getFirstName())
//                .setParameter(3, classroom.getLastName())
//                .executeUpdate();
//    }
//}
