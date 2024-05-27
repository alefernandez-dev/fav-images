package de.alejandro.favimages.image;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

class ImageRowMapper implements RowMapper<Image> {
    @Override
    public Image mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Image(
                rs.getObject("image_id", UUID.class),
                rs.getString("image_name"),
                rs.getString("image_detail"),
                rs.getString("image_file_name"),
                new ImageCategory(
                        rs.getString("category_name"),
                        rs.getLong("category_id")
                )
        );
    }
}
