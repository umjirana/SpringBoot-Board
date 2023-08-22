package com.mysite.sbb.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findBySubjectAndContent(String subject, String content);
    List<Question> findBySubjectLike(String subject);
}