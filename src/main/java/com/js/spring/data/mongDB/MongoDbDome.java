package com.js.spring.data.mongDB;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @author 刘锦涛
 * @title: MongDBDome
 * @projectName java_dome_work_space
 * @date 2021/3/12
 * @dateTime 15:31
 * @description: TODO
 */

public class MongoDbDome {


    static {

    }

    public static void main(String[] args) throws Exception {

        MongoClient mongoClient = getMongoClient();


        MongoTemplate mongoOps = new MongoTemplate(mongoClient, "admin");
        mongoOps.insert(new Person("Joe", 24));

        System.out.println(mongoOps.findOne(new Query(Criteria.where("name").is("Joe")), Person.class));

        mongoOps.dropCollection("person");

    }

    private static MongoClient getMongoClient() {

        return MongoClients.create("mongodb://192.168.79.128:27017");
    }


}

@Data
class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.age = age;
        this.name = name;
    }
}
