package com.voracity.shushudemo.stream;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class StreamPracticeDataTest {


    @Test
    public void filterTest(){
        List<User> userList = StreamPracticeData.userList;
        List<User> collect = userList.stream().filter(user -> user.getAge() < 30 && user.getCity() == "北京").collect(Collectors.toList());
        collect.forEach(user -> System.out.println(user));
    }

    @Test
    public void mapTest(){
        List<User> userList = StreamPracticeData.userList;
//        userList.forEach(user -> System.out.println(user));
        System.out.println("---------------------------------");
        List<String> collect = userList.stream().map(new Function<User, String>() {
            @Override
            public String apply(User user) {
                return user.getName();
            }
        }).collect(Collectors.toList());
        collect.forEach(user -> System.out.println(user));
//        List<User> collect = (List<User>) collect1
//        collect.forEach(user -> System.out.println(user));
//        collect1.forEach(new BiConsumer<String, List<User>>() {
//            @Override
//            public void accept(String s, List<User> users) {
//                System.out.println("城市："+s+"--------------");
//                users.forEach(user -> System.out.println(user));
//            }
//        });
    }

    @Test
    public void groupingTest(){
        List<User> userList = StreamPracticeData.userList;
//        userList.forEach(user -> System.out.println(user));
        System.out.println("---------------------------------");
//        Map<String, List<User>> collect = userList.stream().collect(Collectors.groupingBy(User::getCity));
        Map<String, List<User>> collect = userList.stream().collect(Collectors.groupingBy(new Function<User, String>() {
            @Override
            public String apply(User user) {
                return user.getCity();
            }
        }));
        System.out.println(collect);

    }

}
