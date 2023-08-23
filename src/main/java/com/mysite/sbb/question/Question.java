package com.mysite.sbb.question;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.mysite.sbb.answer.Answer;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT", length = 2048)
    private String content;

    private LocalDateTime create_date;

    public List<Answer> getAnswerList() {
        List<Answer> answerList = new ArrayList<>();  // 또는 다른 List 구현체를 사용할 수 있습니다.
        // 필요한 로직을 통해 answerList에 데이터를 추가하거나 초기화합니다.
        return answerList;
    }


}

