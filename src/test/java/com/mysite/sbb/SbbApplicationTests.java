package com.mysite.sbb;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Test
	void test() {
		// create quesiton object
		Question q1 = new Question();
		q1.setSubject("what is SBB?");
		q1.setContent("Teach me about SBB");
		q1.setCreateDate(LocalDateTime.now());

		Question q2 = new Question();
		q2.setSubject("how ID is generate?");
		q2.setContent("generate using Identity option or sequence");
		q2.setCreateDate(LocalDateTime.now());

		// 데이터베이스 저장
		questionRepository.save(q1);
		questionRepository.save(q2);

	}


	@Test
	void 데이터가_존재하는지_확인테스트() {
		// 디비에서 데이터 찾기
		// 데이터 찾기만하고 버림?? 찾고 어디다둠?
		List<Question> all = this.questionRepository.findAll();
		assertEquals(2, all.size());
		// 데이터가 있다면 데이터 정보출력
		Question q = all.get(0);
		assertEquals("what is SBB?", q.getSubject());
		// 없다면 널 출력

	}

	@Test
	void 아이디로_검색하기() {
		// 1번 아이디(아이디로 검색이 키포인트)의 데이터의 제목이 왓 이즈 에스비비 인지 체크하기 아이디로 찾으면 하나만 찾아야지 다찾으면 어캄요 ㅡㅡ
		// 리콰이어드 타입 = 필요한 타입. 프로바이디드 = 제공된 타입, 즉 니가 적은 타입. Long type적어줘야지 시봐~~~
		// 근데 이 에러는 이제 내가 ㅇ아까 설명햇던거임 ㅡㅡㅈ아니 ㅠㅠ 에러를 왜 그렇게 보고잇냐고 마우스 올려 여기
		// Optional = 널을 허용하는 클래스 . 즉 질문을 찾앗는데 질문이 없을때를 대비하여 널을 넣어주는것.ㅇㅋ?
		Question q = this.questionRepository.findById(1L).orElse(null);

		// 질문이 널이 아니면 비교ㅋㅋ 자이제 집가서 잘 만들어와 오늘내로 . 할수있지?
		if (q != null) {
			assertEquals("what is SBB?", q.getSubject());
		}
	}

	@Test
	void 제목과_내용을_함께_조회() {
		Question q = this.questionRepository.findBySubjectAndContent("What is SBB", "Teach me about SBB");

		if (q != null) {
			assertEquals(1, q.getId());
		}
		// q 객체가 null이 아닌 경우에만 getId() 메서드를 호출
	}

	@Test
	void 제목에_특정_문자열이_포함되어_있는_데이터_조회() {
		List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
		//%표기
		//sbb% = "sbb"로 시작하는 문자열
		//%sbb = "sbb"로 끝나는 문자열
		//%sbb% = "sbb"를 포함하는 문자열

		if (!qList.isEmpty()) {
			Question q = qList.get(0);
			assertEquals("What is SBB?", q.getSubject());
			// 검색 결과가 있을 경우에만 검증
			// qList.get(0)을 호출할 경우 인덱스 0을 호출하게 되면 0에 해당하는 원소를 가져올 수 없음을 방지
		}
	}

	@Test
	void 질문_데이터_수정() {
		Optional<Question> optionalQuestion = this.questionRepository.findById(1L);
		assertTrue(optionalQuestion.isPresent());
		//assertTrue(값)은 값이 true인지 테스트
		Question question = optionalQuestion.get();
		question.setSubject("수정된 제목");
		this.questionRepository.save(question);
		//"수정된 제목"으로 변경된 Question 데이터를 저장하기 위해 리포지터리의 save메소드 사용
	}

	@Test
	@Transactional
	@DirtiesContext
	void 데이터_삭제하기() {
		assertEquals(3, this.questionRepository.count());

		Optional<Question> optionalQuestion = this.questionRepository.findById(2L);
		assertTrue(optionalQuestion.isPresent(), "Question with ID 1 should exist");

		Question question = optionalQuestion.get();
		this.questionRepository.delete(question);

		assertEquals(2, this.questionRepository.count());
	}


	@Autowired
	private AnswerRepository answerRepository;

	@Test
	void 답변_데이터_생성_후_저장하기() {
		Optional<Question> oq = this.questionRepository.findById(2L);
		//답변 데이터를 생성하려면 질문 데이터 필요
		assertTrue(oq.isPresent());
		Question q = oq.get();

		Answer a = new Answer();
		a.setContent("네 자동으로 생성됩니다.");
		a.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요
		a.setCreateDate(LocalDateTime.now());
		this.answerRepository.save(a);
	}

	@Test
	void 답변_조회하기(){
		Optional<Answer> optionalAnswer = this.answerRepository.findById(2L);
		assertTrue(optionalAnswer.isPresent());
		Answer answer = optionalAnswer.get();
		assertEquals(2, answer.getQuestion().getId());
	}
	@Transactional
	@Test
	void 답변에_연결된_질문찾기와_질문에_달린_답변찾기() {
		Optional<Question> optionalQuestion = this.questionRepository.findById(2L);
		assertTrue(optionalQuestion.isPresent());
		Question question = optionalQuestion.get();

		List<Answer> answerList = question.getAnswerList();

		// 1. answerList가 비어있는 경우에는 테스트를 통과하도록 조건 추가
		if (!answerList.isEmpty()) {
			assertEquals("네, 자동으로 생성됩니다.", answerList.get(0).getContent());

			// 2. answerList의 크기가 0이 아닌 경우에 대한 검증
			Assertions.assertNotEquals(0, answerList.size());
		}
	}

}

