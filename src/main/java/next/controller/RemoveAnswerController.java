package next.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import core.utils.ServletRequestUtils;

public class RemoveAnswerController extends AbstractController {

	@Override
	public ModelAndView execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		QuestionDao questionDao = new QuestionDao();
		AnswerDao answerDao = new AnswerDao();
		Question question;
		List<Answer> answers;
		Answer answer;
		
		long answerId = ServletRequestUtils.getRequiredLongParameter(request, "answerId");
		answer = answerDao.findByAnswerId(answerId);
		long questionId = answer.getQuestionId();
		answerDao.remove(answerId);
		question = questionDao.findById(questionId);
		question.setCountOfComment(question.getCountOfComment() - 1);
		questionDao.updateCommentCount(question);
		answers = answerDao.findAllByQuestionId(questionId);
		ModelAndView mav = jsonView();
		
		System.out.println("answerId : " + answerId);
		System.out.println("questionId : " + questionId);
		mav.addObject("question", question);
		mav.addObject("answers", answers);
		return mav;
		
		
	}

}
