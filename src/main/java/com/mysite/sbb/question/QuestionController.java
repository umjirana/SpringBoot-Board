package com.mysite.sbb.question;

import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import com.mysite.sbb.answer.AnswerForm;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Question> questionList = this.questionService.getList();
        model.addAttribute("questionList", questionList);
        return "question_list";
    }
    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id, AnswerForm answerForm){


        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);


        return "question_detail";
    }

    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {

        return "question_form";
    }
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
        //questionCreate의 메서드의 매개변수를 QuestionForm 객체로 변경
        //스프링 프레임워크의 자동 바인딩 기능
        //@Valid를 적용하면 QuestionForm의 @NotEmpty,@Size등의 검증기능 작용
        //BindingResult 매개변수는 @Valid애너테이션으로 인해 검증이 수행된 결과를 의미하는 객체
        //BindingResult 매개변수는 항상 @Valid 매개변수 바로 뒤에 위치해야 함
        //-> 위치가 정확하지 않다면 @Valid만 적용되어 입력값 검증 실패 시 400 오류 발생
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        this.questionService.create(questionForm.getSubject(), questionForm.getContent());

        return "redirect:/question/list"; // 질문 저장 후 질문목록으로 이동
    }
}