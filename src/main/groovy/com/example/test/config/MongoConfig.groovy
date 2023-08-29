package com.example.test.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories(basePackages = 'com.example.test.repository')
class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return 'mytestdb'    }

    @Override
    protected List<String> getMappingBasePackages() {
        return ['com.example.test', 'com.example.test.model']
    }
}
