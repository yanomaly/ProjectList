package com.example.projectlist.services;

import com.example.projectlist.auxiliary.DemoProject;
import com.example.projectlist.entites.Project;
import com.example.projectlist.repositories.ProblemsRepository;
import com.example.projectlist.repositories.ProjectsRepository;
import com.example.projectlist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class ProjectService {

    @Autowired
    ProjectsRepository projectsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProblemsRepository problemsRepository;

    private Map<Long, Long> user_project = new HashMap<>();  //user_id + current project_id

    public String validation(DemoProject project){
        String decision = "";
        if(!Pattern.compile("([A-Z][a-z]+)|([А-Я][а-я]+)").matcher(project.getHead()).find())
            decision += "Wrong surname!\n\n";
        if(!Pattern.compile("([A-Z0-9][a-z0-9]+)|([А-Я0-9][а-я0-9]+)").matcher(project.getName()).find())
            decision += "Wrong project name!\n\n";
        if(!Pattern.compile("[0-9]+").matcher(project.getBudget()).find())
            decision += "Wrong budget!\n\n";
        if(!Pattern.compile("((0[1-9])|(1[0-9])|(2[0-9])|(3[0-1])|([0-9]))-((0[1-9])|(1[0-2])|([0-9]))-([2-9][0-9]{3})").matcher(project.getDateFinish()).find())
            decision += "Wrong date!\n\n";
        return  decision;
    }
    public Project createProject(DemoProject demoProject){
        Project project = new Project();
        if(demoProject.getName() != null && !demoProject.getName().equals(""))
        project.setName(demoProject.getName());
        if(demoProject.getHead() != null && !demoProject.getHead().equals(""))
        project.setHead(demoProject.getHead());
        if(demoProject.getBudget() != null && !demoProject.getBudget().equals(""))
        project.setBudget(Integer.parseInt(demoProject.getBudget()));
        if(demoProject.getDateFinish() != null && !demoProject.getDateFinish().equals("")) {
            String[] dateTemp = demoProject.getDateFinish().split("-");
            project.setDateFinish(new GregorianCalendar(Integer.parseInt(dateTemp[2]), Integer.parseInt(dateTemp[1]) - 1, Integer.parseInt(dateTemp[0])));
        }
        return project;
    }
    public List<Project> getData(long user_id){
        List<Project> data = new LinkedList<>();
        Project sample = userService.getRequest(user_id);
        String sortBy = null;
        switch (userService.getOrder(user_id) / 10){
            case (1):
                sortBy = "name";
                break;
            case (2):
                sortBy = "head";
                break;
            case(3):
                sortBy = "budget";
                break;
            case(4):
                sortBy = "dateFinish";
                break;
        }
        if(isEmpty(sample)) {
            if (userService.getOrder(user_id) % 10 == 1) {
                Pageable page = PageRequest.of(userService.getPage(user_id), 2, Sort.by(sortBy).descending());
                data = projectsRepository.findAllByUserIDAndIsDelete(user_id, false, page).getContent();
                userService.setMaxPage(user_id, page.getPageSize());
            }
            if (userService.getOrder(user_id) % 10 == 2) {
                Pageable page = PageRequest.of(userService.getPage(user_id), 2, Sort.by(sortBy).ascending());
                data = projectsRepository.findAllByUserIDAndIsDelete(user_id, false, page).getContent();
                userService.setMaxPage(user_id, page.getPageSize());
            }
            if (userService.getOrder(user_id) == 0) {
                Pageable page = PageRequest.of(userService.getPage(user_id), 2, Sort.by("projectID").descending());
                data = projectsRepository.findAllByUserIDAndIsDelete(user_id, false, page).getContent();
                userService.setMaxPage(user_id, page.getPageSize());
            }
        }
        else{
            String name, head;
            Integer budget1, budget2;
            Calendar dateFinish1, dateFinish2;
            if(sample.getName() == null)
                name = "%";
            else
                name = sample.getName();
            if(sample.getHead() == null)
                head = "%";
            else
                head = sample.getHead();
            if(sample.getBudget() == null){
                budget1 = 0;
                budget2 = Integer.MAX_VALUE;
            }
            else{
                budget1 = sample.getBudget();
                budget2 = budget1;
            }
            if(sample.getDateFinish() == null){
                dateFinish1 = new GregorianCalendar(0, 0, 0);
                dateFinish2 = new GregorianCalendar(10000, 0, 0);
            }
            else{
                dateFinish1 = sample.getDateFinish();
                dateFinish2 = dateFinish1;
            }
            Pageable page = PageRequest.of(userService.getPage(user_id), 2);
            data = projectsRepository.filter(name, head, budget1, budget2, dateFinish1, dateFinish2, page).getContent();
            userService.setMaxPage(user_id, page.getPageSize());
        }
        return data;
    }
    public void saveProject(Project project){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        long project_id = projectsRepository.save(project).getProjectID();
        long user_id = userRepository.findByUsername(loggedInUser.getName()).getUserID();
        user_project.put(user_id, project_id);
    }
    public void setActiveProject(long user_id, long project_id){
        user_project.put(user_id, project_id);
    }
    public void updateProject(Project project){
        projectsRepository.editProject(project.getProjectID(), project.getName(), project.getHead(), project.getDateFinish(), project.getBudget());
    }
    public long getProject_id(long user_id){
        if(user_project.containsKey(user_id))
            return user_project.get(user_id);
        else
            return -1;
    }
    public boolean isEmpty(Project project){
        if(project == null ||
                (project.getName() == null || project.getName().equals(""))
                        && (project.getHead() == null || project.getHead().equals(""))
                        && (project.getDateFinish() == null || project.getDateFinish().equals(""))
                        && (project.getBudget() == null || project.getBudget().equals("")))
            return true;
        else return false;
    }
}
