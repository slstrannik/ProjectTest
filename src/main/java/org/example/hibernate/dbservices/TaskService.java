package org.example.hibernate.dbservices;

import org.example.hibernate.entity.Task;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    public TaskService() {
    }

    @Transactional
    public List<Task> getTasksbyRangeDate(Date date1, Date date2, Integer ordernumber) {
        EntityManager manager = DBClass.getSessionFactory().createEntityManager();
        manager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Task> criteriaQuery = criteriaBuilder.createQuery(Task.class);
        Root<Task> root = criteriaQuery.from(Task.class);
        criteriaQuery.select(root);
        List<Predicate> predicates = new ArrayList<>();
        Predicate predicate = criteriaBuilder.between(root.get("deliverydate"), date1, date2);
        predicates.add(predicate);
        if (ordernumber > -1) {
            predicate = criteriaBuilder.equal(root.get("ordernumber"), ordernumber);
            predicates.add(predicate);
        }
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        List<Task> result = manager.createQuery(criteriaQuery).getResultList();
        manager.close();
        return result;
    }

    @Transactional
    public boolean addTask(Integer orderNumber){
        EntityManager manager = DBClass.getSessionFactory().createEntityManager();
        int  result = 0;
        try {
            manager.getTransaction().begin();
            result = manager.createNativeQuery("INSERT INTO tasks " +
                    "(deliverydate, orderaddtime, ordernumber, status) " +
                    "VALUES (CURRENT_DATE, CURRENT_TIME, ?, false)")
                    .setParameter(1, orderNumber).executeUpdate();
            manager.getTransaction().commit();
        } finally {
            manager.close();
        }
        return result != 0;
    }
}
