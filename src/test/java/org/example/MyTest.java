package org.example;

import org.example.annotation.AutoWired;
import org.example.controller.UserController;
import org.example.service.UserService;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.stream.Stream;

public class MyTest {

    @Test
  public void test01() throws Exception {
        UserController userController = new UserController();
        Class<? extends UserController> clazz = userController.getClass();
        //创建对象
        UserService userService = new UserService();
        //获取所有属性
        Field serviceField = clazz.getDeclaredField("userService");
        serviceField.setAccessible(true);
        //只有通过方法才能够设置具体的属性值
        String name = serviceField.getName();
        //拼接方法的名称
        name = name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
        String setMethodName = "set" + name;
        //通过方法属性注入属性的对象
        Method method = clazz.getMethod(setMethodName, UserService.class);
        method.invoke(userController, userService);
        System.out.println(userController.getUserService());
    }

    @Test
    public void test02() {
        UserController userController = new UserController();
        Class<? extends UserController> clazz = userController.getClass();
        //获取所有属性值
        Stream.of(clazz.getDeclaredFields()).forEach(field -> {
            AutoWired annotation = field.getAnnotation(AutoWired.class);
            if (annotation != null) {
                field.setAccessible(true);
                //获取属性类型
                Class<?> type = field.getType();
                try {
                    Object o = type.newInstance();
                    field.set(userController, o);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println(userController.getUserService());
    }
}
