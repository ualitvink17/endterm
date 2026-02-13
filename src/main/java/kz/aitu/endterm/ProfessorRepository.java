package kz.aitu.endterm;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProfessorRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProfessorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Professor> rowMapper = (rs, rowNum) -> new Professor(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("department"),
            rs.getString("city"),
            rs.getInt("experience_years")
    );

    public List<Professor> findAll(String sortBy, String departmentFilter, String search) {
        StringBuilder sql = new StringBuilder("SELECT * FROM professors WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (search != null && !search.isEmpty()) {
            sql.append("AND LOWER(name) LIKE LOWER(?) ");
            params.add("%" + search + "%");
        }

        if (departmentFilter != null && !departmentFilter.isEmpty()) {
            sql.append("AND department = ? ");
            params.add(departmentFilter);
        }

        if (sortBy != null && !sortBy.isEmpty()) {
            String sortColumn = switch (sortBy) {
                case "name" -> "name";
                case "experience" -> "experience_years";
                case "city" -> "city";
                default -> "id";
            };
            sql.append("ORDER BY ").append(sortColumn);
        } else {
            sql.append("ORDER BY id");
        }

        return jdbcTemplate.query(sql.toString(), rowMapper, params.toArray());
    }

    public int save(Professor p) {
        String sql = "INSERT INTO professors (name, department, city, experience_years) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, p.getName(), p.getDepartment(), p.getCity(), p.getExperienceYears());
    }

    public int update(Professor p) {
        String sql = "UPDATE professors SET name=?, department=?, city=?, experience_years=? WHERE id=?";
        return jdbcTemplate.update(sql, p.getName(), p.getDepartment(), p.getCity(), p.getExperienceYears(), p.getId());
    }

    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM professors WHERE id=?", id);
    }

    public Professor findById(Long id) {
        String sql = "SELECT * FROM professors WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }
}