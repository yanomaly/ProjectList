package com.example.projectlist.service;

import com.example.projectlist.auxiliary.DemoProject;
import com.example.projectlist.services.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class ProjectServiceTest {

    @Autowired
    ProjectService projectService;

    @Test
    public void validation(){
        DemoProject demoProject1 = new DemoProject("wRong#Name", "999Head", "10-01-2003", "budget");
        DemoProject demoProject2 = new DemoProject("Name 9", "Wrong9 head", "2003-10-01", "128");
        DemoProject demoProject3 = new DemoProject("Right name", "Head", "20-02-2002", "191919");
        DemoProject demoProject4 = new DemoProject("", "", "", "");

        Assert.isTrue(!projectService.validation(demoProject1).equals(""));
        Assert.isTrue(!projectService.validation(demoProject2).equals(""));
        Assert.isTrue(projectService.validation(demoProject3).equals(""));
        Assert.isTrue(!projectService.validation(demoProject4).equals(""));
    }

}
