package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Image;
import com.example.demo.entities.User;

@Repository
public class DatabaseStorage{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int saveUser(User user) {

        return jdbcTemplate.update(
                "insert into USERS (ID, UserName, Email) values(?,?,?)",
                user.getID(), user.getName(), user.getEmail());

    }

    public int saveImage(Image image) {

        return jdbcTemplate.update(
                "insert into IMAGES (ID, Image) values(?,?)",
                image.getID(), image.getImage());

    }

    public Image getImage(String userId) {
        String sql = "SELECT * FROM IMAGES WHERE ID = ?";
        Image target = jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new Image(
                        rs.getString("ID"),
                        rs.getBytes("Image")
                ),new Object[]{userId});
        return target;

    }

}
