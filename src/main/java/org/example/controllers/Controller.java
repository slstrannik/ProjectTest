package org.example.controllers;

import org.example.hibernate.dbservices.TaskService;
import org.example.hibernate.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class Controller {

    @Autowired
    TaskService service;

    @PostMapping(value = "task")
    public ResponseEntity<List<Task>> getTasks(@RequestBody Map<String, String> args) {
        List<Task> tasks;
        try {
            Date date1 = Date.valueOf(args.get("date1"));
            Date date2 = Date.valueOf(args.get("date2"));
            int ordernumber = -1;
            if (args.get("order_number") != null) ordernumber = Integer.parseInt(args.get("ordernumber"));
            tasks = service.getTasksbyRangeDate(date1, date2, ordernumber);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return tasks.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @RequestMapping(value = "belate")
    public ResponseEntity beLate(@RequestBody Map<String, String> args) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        try {
            int order = Integer.parseInt(args.get("ordernumber"));
            if (service.addTask(order)) status = HttpStatus.CREATED;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(status);
    }
}
