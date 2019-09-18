package com.zljx.service.impl;

import com.zljx.service.JDBCTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;

/**
 * @Author: sunzhihan
 * @Date：2019/07/01 10:19
 * @Version 1.0
 **/
@Service
public class JDBCTemplateImpl implements JDBCTemplate {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String QUERY_USER_SUBSCRIBE_SQL = "select {0} from {1}";


    @Override
    public List<Integer> selectSubscribeColumnIds() {
        return jdbcTemplate.query(MessageFormat.format(QUERY_USER_SUBSCRIBE_SQL, "id","cart"),
                new RowMapper<Integer>() {
                    @Override
                    public Integer mapRow(ResultSet rs, int i) throws SQLException {
                        return rs.getInt("id");
                    }
                });
    }
}
