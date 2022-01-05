package org.studyhub.yygh.hosp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author haoren
 * @create 2022-01-05 19:13
 */
@SpringBootTest
public class TestClass {
    @Test
    public void test(){
        List<Long> idList = new ArrayList<Long>();
        idList.add(1L);
        idList.add(2L);
        System.out.println(idList);
    }
}
