package com.example.projectlist.services;

import com.example.projectlist.entites.Problem;
import com.example.projectlist.repositories.ProblemsRepository;
import com.example.projectlist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class ProblemService {

    @Autowired
    ProblemsRepository problemsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectService projectService;

    private Map<Long, Long> project_problem = new HashMap<>(); //id_project + count of problems in it

    public String validation(Problem problem){
        String decision = "";
        if(!Pattern.compile("([A-Z0-9][a-z0-9]+)|([А-Я0-9][а-я0-9]+)").matcher(problem.getName()).find())
            decision += "Wrong problem name!\n\n";
        return decision;
    }
    public void saveProblem(Problem problem){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        long project_id = projectService.getProject_id(userRepository.findByUsername(loggedInUser.getName()).getUserID()); //get user current project
        problem.setProjectID(project_id);
        problemsRepository.save(problem);
        if(project_problem.containsKey(project_id))
            project_problem.put(project_id, project_problem.get(project_id) + 1L); //increase count
        else
            project_problem.put(project_id, 1L);
    }
    public long getCount(long project_id){
        if(project_problem.containsKey(project_id))
           return project_problem.get(project_id);
        else
            return 0l;
    }
}
