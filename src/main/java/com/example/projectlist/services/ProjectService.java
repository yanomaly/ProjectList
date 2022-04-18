package com.example.projectlist.services;

import com.example.projectlist.entites.Project;
import com.example.projectlist.repositories.ProblemsRepository;
import com.example.projectlist.repositories.ProjectsRepository;
import com.example.projectlist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class ProjectService {

    @Autowired
    ProjectsRepository projectsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProblemsRepository problemsRepository;

    private Map<Long, Long> user_project = new HashMap<>();  //user_id + current project_id

    public String validation(Project project){
        String decision = "";
        if(!Pattern.compile("([A-Z][a-z]+)|([А-Я][а-я]+)").matcher(project.getHead()).find())
            decision += "Wrong surname!\n\n";
        if(!Pattern.compile("([A-Z0-9][a-z0-9]+)|([А-Я0-9][а-я0-9]+)").matcher(project.getName()).find())
            decision += "Wrong project name!\n\n";
        if(!Pattern.compile("[0-9]+").matcher(project.getBudget()).find())
            decision += "Wrong budget!\n\n";
        if(!Pattern.compile("((0[1-9])|(1[0-9])|(2[0-9])|(3[0-1]))-((0[1-9])|(1[0-2]))-([2-9][0-9]{3})").matcher(project.getDateFinish()).find())
            decision += "Wrong date!\n\n";
        return  decision;
    }

    public void saveProject(Project project){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        long project_id = projectsRepository.save(project).getProjectID();
        long user_id = userRepository.findByUsername(loggedInUser.getName()).getUserID();
        user_project.put(user_id, project_id);
    }

    public void deleteFromUser_project(long user_id){
        user_project.remove(user_id);
    }

    public long getProject_id(long user_id){
        if(user_project.containsKey(user_id))
            return user_project.get(user_id);
        else
            return -1;
    }

    public void setActiveProject(long user_id, long project_id){
        user_project.put(user_id, project_id);
    }

    public List<Project> filter(Long user_id, Project project){
        List<Project> temp = projectsRepository.findAllByUserID(user_id);
        temp = temp.stream().filter(x -> x.getIsDelete() == false).toList();
        if(project.getName() != null && !project.getName().equals("") && temp != null)
            temp = temp.stream().filter(x -> x.getName().equals(project.getName())).toList();
        if(project.getHead() != null && !project.getHead().equals("") && temp != null)
            temp = temp.stream().filter(x -> x.getHead().equals(project.getHead())).toList();
        if(project.getBudget() != null && !project.getBudget().equals("") && temp != null)
            temp = temp.stream().filter(x -> x.getBudget().equals(project.getBudget())).toList();
        if(project.getDateFinish() != null && !project.getDateFinish().equals("") && temp != null)
            temp = temp.stream().filter(x -> x.getDateFinish().equals(project.getDateFinish())).toList();
        return temp;
    }
}
