package com.example.projectlist.service;

import com.example.projectlist.entites.Problem;
import com.example.projectlist.services.ProblemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class ProblemServiceTests {

    @Autowired
    ProblemService problemService;

    @Test
    public void validation() {
        String test1 = problemService.validation(new Problem(""));
        String test2 = problemService.validation(new Problem("Wron#g na4me"));
        String test3 = problemService.validation(new Problem("hmm"));
        String test4 = problemService.validation(new Problem("N1ce name"));

        Assert.isTrue(!test1.equals(""));
        Assert.isTrue(!test2.equals(""));
        Assert.isTrue(!test3.equals(""));
        Assert.isTrue(test4.equals(""));
    }

}
