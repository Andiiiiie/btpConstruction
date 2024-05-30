package com.example.btpconstruction.service.dataBase;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@Getter
@Setter
public class adminBaseService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public adminBaseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void executeScript() {
        String sql = "DO $$\n" +
                "DECLARE\n" +
                "   table_name text;\n" +
                "BEGIN\n" +
                "   FOR table_name IN (SELECT tablename FROM pg_tables WHERE schemaname = 'public')\n" +
                "   LOOP\n" +
                "      EXECUTE 'ALTER TABLE ' || table_name || ' DISABLE TRIGGER ALL;';\n" +
                "      IF table_name = 'user_table' THEN\n" +
                "         EXECUTE 'DELETE FROM ' || table_name || ' WHERE roles=''CLIENT''';\n" +
                "      ELSE\n" +
                "         EXECUTE 'DELETE FROM ' || table_name;\n" +
                "      END IF;\n" +
                "      EXECUTE 'ALTER TABLE ' || table_name || ' ENABLE TRIGGER ALL;';\n" +
                "   END LOOP;\n" +
                "END $$;";

        jdbcTemplate.execute(sql);
    }
}
