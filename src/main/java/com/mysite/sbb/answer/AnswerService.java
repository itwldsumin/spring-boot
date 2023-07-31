package com.mysite.sbb.answer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import DataNotFoundException.DataNotFoundException;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class AnswerService {

	private final AnswerRepository answerRepositoty;

	public Answer create(Question question, String content, SiteUser author) {
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		answer.setQuestion(question);
		answer.setAuthor(author);
		this.answerRepositoty.save(answer);
		
		return answer;
	}
	
	public Answer getAnswer(Integer id) {
		Optional<Answer> answer = this.answerRepositoty.findById(id);
		if(answer.isPresent()) {
			return answer.get();
		} else {
			throw new DataNotFoundException("answer not found");
		}
	}

	public void modify(Answer answer, String content) {
		answer.setContent(content);
		answer.setModifyDate(LocalDateTime.now());
		this.answerRepositoty.save(answer);
	}
	
	public void delete(Answer answer) {
		this.answerRepositoty.delete(answer);
	}
	
	public void vote(Answer answer, SiteUser siteUser) {
		answer.getVoter().add(siteUser);
		this.answerRepositoty.save(answer);
	}
}
