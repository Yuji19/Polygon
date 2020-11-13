package com.yuji.polygon;

import com.yuji.polygon.entity.Menu;
import com.yuji.polygon.entity.Permission;
import com.yuji.polygon.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PolygonWebApplicationTests {

    @Autowired
    MenuService menuService;

    @Test
    void contextLoads() {
        List<Menu> menus = menuService.getAllMenuWithPermission();
        for (Menu menu : menus){
            System.out.println(menu);

        }
    }

    @Test
    void testMenu(){
        System.out.println(menuService.getAllMenu());
    }

}
