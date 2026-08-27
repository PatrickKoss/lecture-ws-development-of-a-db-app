package course.library.api;

import course.library.service.BookService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {
  private final BookService service;

  public BookController(BookService service) {
    this.service = service;
  }

  @GetMapping
  public List<BookResponse> findAll() {
    return service.findAll().stream().map(BookResponse::from).toList();
  }

  @GetMapping("/{id}")
  public BookResponse findById(@PathVariable long id) {
    return BookResponse.from(service.findById(id));
  }
}
