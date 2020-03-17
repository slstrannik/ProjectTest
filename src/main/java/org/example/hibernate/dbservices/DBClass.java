package org.example.hibernate.dbservices;

import org.example.hibernate.entity.Task;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class DBClass {
    private static SessionFactory sessionFactory = null;

    private DBClass() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL94Dialect");
                configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
                configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/dbtest");
                configuration.setProperty("hibernate.connection.username", "postgres");
                configuration.setProperty("hibernate.connection.password", "admin");
                configuration.setProperty("hibernate.show_sql", "true");
                configuration.setProperty("hibernate.hbm2ddl.auto", "update");
                configuration.setProperty("spring.jpa.properties.hibernate.globally_quoted_identifiers", "true");
                configuration.addAnnotatedClass(Task.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
