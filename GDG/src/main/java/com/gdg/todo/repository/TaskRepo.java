package com.gdg.todo.repository;

import com.gdg.todo.task.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepo extends MongoRepository<Task, String> {

    List<Task> findByStatusIgnoreCase(String s);

    List<Task> findByDueD(LocalDate dueD);

    List<Task> findByDueDBefore(LocalDate dueD);

}
