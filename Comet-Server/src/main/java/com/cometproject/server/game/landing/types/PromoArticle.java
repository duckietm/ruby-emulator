package com.cometproject.server.game.landing.types;

import java.sql.ResultSet;
import java.sql.SQLException;


public class PromoArticle {
    private final int id;
    private final String title;
    private final String message;
    private final String buttonText;
    private final String buttonLink;
    private final String imagePath;

    public PromoArticle(ResultSet data) throws SQLException {
        this.id = data.getInt("id");
        this.title = data.getString("title");
        this.message = data.getString("message");
        this.buttonText = data.getString("button_text");
        this.buttonLink = data.getString("button_link");
        this.imagePath = data.getString("image_path");
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getButtonText() {
        return buttonText;
    }

    public String getButtonLink() {
        return buttonLink;
    }

    public String getImagePath() {
        return imagePath;
    }
}
