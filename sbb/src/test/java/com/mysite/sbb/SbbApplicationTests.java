package com.mysite.sbb;

import com.mysite.sbb.Entity.Question;
import com.mysite.sbb.Entity.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Test
	void test(){
		// create quesiton object
		Question q1 = new Question();
		q1.setSubject("what is SBB?");
		q1.setContent("Teach me about SBB");
		q1.setCreate_date(LocalDateTime.now());

		Question q2 = new Question();
		q2.setSubject("how ID is generate?");
		q2.setContent("generate using Identity option or sequence");
		q2.setCreate_date(LocalDateTime.now());

		// 데이터베이스 저장
		questionRepository.save(q1);
		questionRepository.save(q2);

		}


		@Test
		void 데이터가_존재하는지_확인테스트(){
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
		void 아이디로_검색하기(){
			// 1번 아이디(아이디로 검색이 키포인트)의 데이터의 제목이 왓 이즈 에스비비 인지 체크하기 아이디로 찾으면 하나만 찾아야지 다찾으면 어캄요 ㅡㅡ
			// 리콰이어드 타입 = 필요한 타입. 프로바이디드 = 제공된 타입, 즉 니가 적은 타입. Long type적어줘야지 시봐~~~
			// 근데 이 에러는 이제 내가 ㅇ아까 설명햇던거임 ㅡㅡㅈ아니 ㅠㅠ 에러를 왜 그렇게 보고잇냐고 마우스 올려 여기
			// Optional = 널을 허용하는 클래스 . 즉 질문을 찾앗는데 질문이 없을때를 대비하여 널을 넣어주는것.ㅇㅋ?
			Question q = this.questionRepository.findById(1L).orElse(null);

			// 질문이 널이 아니면 비교ㅋㅋ 자이제 집가서 잘 만들어와 오늘내로 . 할수있지?
			if (q != null){
				assertEquals("what is SBB?", q.getSubject());
			}
		}
	}


