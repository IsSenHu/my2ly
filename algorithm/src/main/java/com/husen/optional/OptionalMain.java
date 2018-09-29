package com.husen.optional;

import com.husen.vo.Person;

import java.time.LocalDate;
import java.util.Optional;

public class OptionalMain {
    public static void main(String[] args) {
        Person person = new Person();
        person.setName("胡森");
        person.setAge(23);
        person.setBirthday(LocalDate.of(1995, 5, 28));
        System.out.println(person);
//        person = null;
        Optional<Person> optionalPerson = Optional.ofNullable(person);
        //存在即返回，否则使用默认值，可以用来替换判断为空的正则表达式
        System.out.println(optionalPerson.orElse(null));
        System.out.println(optionalPerson.orElse(new Person()));
        //存在即返回，否则使用函数
        System.out.println(optionalPerson.orElseGet(() -> new Person()));
        //存在对它做点什么
        optionalPerson.ifPresent(System.out::println);
        optionalPerson.map(Person::getBirthday).ifPresent(System.out::println);
    }
}
