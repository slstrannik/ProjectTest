package org.example.hibernate.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "tasks")
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false, unique=true)
    private Long id;
    @Column(unique=true)
    private Integer ordernumber;
    private Date deliverydate;
    private Time orderaddtime;
    private Boolean status;

    public Task() {
        
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", ordernumber=" + ordernumber +
                ", deliverydate=" + deliverydate +
                ", orderaddtime=" + orderaddtime +
                ", status=" + status +
                '}';
    }

    public Task(Long id, Integer ordernumber, Date deliverydate, Time orderaddtime, Boolean status) {
        this.id = id;
        this.ordernumber = ordernumber;
        this.deliverydate = deliverydate;
        this.orderaddtime = orderaddtime;
        this.status = status;
    }
}
