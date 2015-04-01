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

public class AddAnswerController extends AbstractController {

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		QuestionDao questionDao = new QuestionDao();
		AnswerDao answerDao = new AnswerDao();
		Question question;
		List<Answer> answers;
		
		String writer = ServletRequestUtils.getStringParameter(request, "writer");
		String contents = ServletRequestUtils.getStringParameter(request, "contents");
		long questionId = ServletRequestUtils.getRequiredLongParameter(request, "questionId");
		
		answerDao.insert(new Answer(writer, contents, questionId));
		question = questionDao.findById(questionId);
		question.setCountOfComment(question.getCountOfComment() + 1);
		questionDao.updateCommentCount(question);
		answers = answerDao.findAllByQuestionId(questionId);
		ModelAndView mav = jsonView();
		mav.addObject("question", question);
		mav.addObject("answers", answers);
		return mav;
	}

}
