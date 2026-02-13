package kz.aitu.endterm;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/professors")
@CrossOrigin(origins = "*")
public class ProfessorController {

    private final ProfessorRepository repository;

    public ProfessorController(ProfessorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Professor> getAll(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String search) {
        return repository.findAll(sortBy, department, search);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Professor professor) {
        if (professor.getName() == null || professor.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        repository.save(professor);
        return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\": \"Professor created\"}");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Professor professor) {
        professor.setId(id);
        int rows = repository.update(professor);
        if (rows == 0) throw new RuntimeException("Professor not found with id: " + id);
        return ResponseEntity.ok("{\"message\": \"Professor updated\"}");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        int rows = repository.deleteById(id);
        if (rows == 0) throw new RuntimeException("Professor not found with id: " + id);
        return ResponseEntity.ok("{\"message\": \"Professor deleted\"}");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", e.getMessage()));
    }
}