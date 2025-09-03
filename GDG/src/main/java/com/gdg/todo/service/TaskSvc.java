package com.gdg.todo.service;
import com.gdg.todo.task.Task;
import com.gdg.todo.repository.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskSvc {
    @Autowired
    private TaskRepo repo;

    public Task addTodo(Task t) {
        t.setCreateD(LocalDateTime.now());
        t.setUpdateD(LocalDateTime.now());
        if(t.getStatus()==null) t.setStatus("PENDING");
        System.out.println("Adding task: " + t);
        return repo.save(t);
    }
    public List<Task> getTodos() { return repo.findAll(); }
    public Task update(String id, Task newWork){
        Optional<Task> options = repo.findById(id);
        if(options.isPresent()){
            Task work = options.get();
            work.setTitle(newWork.getTitle());
            work.setDesc(newWork.getDesc());
            work.setStatus(newWork.getStatus());
            work.setDueD(newWork.getDueD());
            work.setUpdateD(LocalDateTime.now()); // <-- fixed
            return repo.save(work);
        }
        return null;
    }

    public Task getById(String id) { return repo.findById(id).orElse(null); }
    public boolean delete(String id) {
        if(repo.existsById(id)) { repo.deleteById(id); return true; }
        return false;
    }
    public List<Task> getByStatus(String s) { return repo.findByStatusIgnoreCase(s); }
    public List<Task> getByDue(LocalDate due) { return repo.findByDueD(due); }
    public List<Task> getOverDue() { return repo.findByDueDBefore(LocalDate.now()); }
}
