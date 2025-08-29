package com.example.todo;

import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "http://localhost:4200") // Angular dev server
public class TodoController {
  private final TodoRepository repo;

  public TodoController(TodoRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<Todo> all() {
    return repo.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
  }

  @PostMapping
  public ResponseEntity<Todo> create(@Valid @RequestBody Todo payload) {
    Todo t = new Todo();
    t.setTitle(payload.getTitle());
    t.setDone(payload.isDone());
    Todo saved = repo.save(t);
    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
  }

  @PutMapping("/{id}")
  public Todo update(@PathVariable Long id, @Valid @RequestBody Todo payload) {
    return repo.findById(id).map(t -> {
      t.setTitle(payload.getTitle());
      t.setDone(payload.isDone());
      return repo.save(t);
    }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @PatchMapping("/{id}/toggle")
  public Todo toggle(@PathVariable Long id) {
    return repo.findById(id).map(t -> {
      t.setDone(!t.isDone());
      return repo.save(t);
    }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    if (!repo.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    repo.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
