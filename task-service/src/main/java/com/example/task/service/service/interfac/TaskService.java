package com.example.task.service.service.interfac;

import com.example.task.service.model.Task;
import com.example.task.service.model.TaskStatus;

import java.util.List;

public interface TaskService {
    Task createTask(Task task, String requestRole)throws Exception;

    Task getTaskById(Long id)throws Exception;

    List<Task> getAllTasks(TaskStatus status);

    Task updateTask(Long id,Task updatedTask,Long userId)throws Exception;

    void deleteTask(Long id) throws Exception;

    Task assignedToUser(Long userId,Long taskId,String jwt)throws Exception;

    List<Task> assignedUsersTask(Long userId,TaskStatus status);

    Task completeTask(Long taskId)throws Exception;
}
