package com.gdg.todo.ctrl;

import com.gdg.todo.service.TaskSvc;
import com.gdg.todo.task.Task;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*") // <-- allows requests from any origin
@RestController
@RequestMapping("/tasks")
public class TaskCTRL {
    private TaskSvc svc;

    public TaskCTRL(TaskSvc svc) {
        this.svc = svc;
    }

    @PostMapping
    public Task addTodo(@RequestBody Task task){
        return svc.addTodo(task);
    }

    @GetMapping
    public List<Task> getTodos(){
        return svc.getTodos();
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable String id, @RequestBody Task task){
        return svc.update(id, task);
    }

    @GetMapping("/{id}")
    public Task getById(@PathVariable String id){
        return svc.getById(id);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable String id){
        return svc.delete(id);
    }

    @GetMapping("/status")
    public List<Task> getByStatus(@RequestParam String s){
        return svc.getByStatus(s);
    }

    @GetMapping("/duedate")
    public List<Task> getByDue(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate due){
        return svc.getByDue(due);
    }

    @GetMapping("/overDue")
    public List<Task> getOverDue(){
        return svc.getOverDue();
    }
}
