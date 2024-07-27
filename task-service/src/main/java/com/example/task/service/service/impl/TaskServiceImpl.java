package com.example.task.service.service.impl;

import com.example.task.service.model.Task;
import com.example.task.service.model.TaskStatus;
import com.example.task.service.model.UserDto;
import com.example.task.service.repo.TaskRepository;
import com.example.task.service.service.EmailService;
import com.example.task.service.service.interfac.TaskService;
import com.example.task.service.service.interfac.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {


    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;

    @Override
    public Task createTask(Task task, String requestRole) throws Exception {
        if(!requestRole.equals("ROLE_ADMIN")){
            throw new Exception("ONLY admin can create task");
        }
        task.setStatus(TaskStatus.PENDING);
        task.setCreatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long id) throws Exception {
        return taskRepository.findById(id).orElseThrow(()->new Exception("task not found"));
    }

    @Override
    public List<Task> getAllTasks(TaskStatus status) {
        List<Task> tasks=taskRepository.findAll();
        List<Task> filterTasks=tasks.stream().filter(
                task->status==null||task.getStatus().name().equalsIgnoreCase(status.toString())
        ).collect(Collectors.toList());
        return filterTasks;
    }

    @Override
    public Task updateTask(Long id, Task updatedTask, Long userId) throws Exception {
        Task existingTask=getTaskById(id);
        if(updatedTask.getTitle()!=null){
            existingTask.setTitle(updatedTask.getTitle());
        }

        if(updatedTask.getDescription()!=null){
            existingTask.setDescription(updatedTask.getDescription());
        }
        if(updatedTask.getStatus()!=null){
            existingTask.setStatus(updatedTask.getStatus());
        }
        if(updatedTask.getDeadline()!=null){
            existingTask.setDeadline(updatedTask.getDeadline());
        }

        return taskRepository.save(existingTask);
    }

    @Override
    public void deleteTask(Long id) throws Exception {
        Task task=getTaskById(id);
        taskRepository.deleteById(id);

    }

    @Override
    public Task assignedToUser(Long userId, Long taskId,String jwt) throws Exception {
        Task task=getTaskById(taskId);
        task.setAssignedUserId(userId);
        task.setStatus(TaskStatus.ASSIGNED);
        UserDto user=userService.getUserProfileById(String.valueOf(userId),jwt);
        emailService.sendMail(user.getEmail());

        return taskRepository.save(task);
    }

    @Override
    public List<Task> assignedUsersTask(Long userId, TaskStatus status) {
        List<Task> tasks=taskRepository.findByAssignedUserId(userId);
        List<Task> filterTasks=tasks.stream().filter(
                task->status==null||task.getStatus().name().equalsIgnoreCase(status.toString())
        ).collect(Collectors.toList());
        return filterTasks;
    }

    @Override
    public Task completeTask(Long taskId) throws Exception {
        Task task=getTaskById(taskId);
        task.setStatus(TaskStatus.DONE);
        return taskRepository.save(task);
    }
}
