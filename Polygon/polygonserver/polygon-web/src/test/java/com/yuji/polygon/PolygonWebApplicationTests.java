package com.yuji.polygon;

import com.yuji.polygon.entity.Permission;
import com.yuji.polygon.service.PermissionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.AntPathMatcher;

import java.util.List;

@SpringBootTest
class PolygonWebApplicationTests {

    @Autowired
    PermissionService permissionService;

    @Test
    void contextLoads() {
        List<Permission> permissions = permissionService.getAllPermissionByRole();
        for (Permission permission : permissions){
            System.out.println(permission);
        }
    }

    @Test
    void testPermission(){
        List<Permission> permissions = permissionService.getPermissionByRoleId(1);
        for (Permission permission : permissions){
            System.out.println(permission);
        }
    }

    @Test
    void testUrl(){
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        boolean result1 = antPathMatcher.match("/leave/**","/leave/query/page");
        if (result1){
            boolean result2 = "/leave/query/page".contains("query");
            System.out.println(result2);
        }


    }

}
