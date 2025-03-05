package loginPocketMaster.comment;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*")
@Tag(name = "Comment Controller", description = "Comment Controller")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestBody Map<String, String> request) {
        Long userId = Long.parseLong(request.get("userId"));
        String teamName = request.get("teamName");
        String content = request.get("content");

        return ResponseEntity.ok(commentService.addComment(userId, teamName, content));
    }



    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Comment>> getUserComments(@PathVariable Long userId) {
        return ResponseEntity.ok(commentService.getCommentsByUser(userId));
    }


    @GetMapping("/team/{teamName}")
    public ResponseEntity<List<Comment>> getTeamComments(@PathVariable String teamName) {
        return ResponseEntity.ok(commentService.getCommentsByTeam(teamName));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String newContent = request.get("content");
        Comment comment = commentService.updateComment(id, newContent);
        return ResponseEntity.ok(comment);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok("Comment deleted successfully");
    }
}

