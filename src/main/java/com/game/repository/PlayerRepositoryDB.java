package com.game.repository;

import com.game.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Repository(value = "db")
public class PlayerRepositoryDB implements IPlayerRepository {

    private final SessionFactory sessionFactory;

    public PlayerRepositoryDB() {
        Properties properties = new Properties();

        properties.put(Environment.DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
        properties.put(Environment.URL, "jdbc:p6spy:mysql://localhost:3306/rpg");

        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "root");
        properties.put(Environment.SHOW_SQL, "true");
        properties.put(Environment.HBM2DDL_AUTO, "update");
        this.sessionFactory = new Configuration()
                .setProperties(properties)
                .addAnnotatedClass(Player.class)
                .buildSessionFactory();
    }

    @Override
    public List<Player> getAll(int pageNumber, int pageSize) {

        try (Session session = sessionFactory.openSession()) {
            NativeQuery<Player> query = session.createNativeQuery("select * from player", Player.class);
            List<Player> resultLIst = query.list();
            return resultLIst;
        }
    }

    @Override
    public int getAllCount() {

        try (Session session = sessionFactory.openSession()) {
            Query<Integer> query = session.createNamedQuery("Player_Count", Integer.class);
            int count = query.getSingleResult();
            return count;
        }
    }

    @Override
    public Player save(Player player) {
        return null;
    }

    @Override
    public Player update(Player player) {
        return null;
    }

    @Override
    public Optional<Player> findById(long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Player player) {

    }

    @PreDestroy
    public void beforeStop() {
        try (Session session = sessionFactory.openSession()) {
            session.close();
        }
    }
}