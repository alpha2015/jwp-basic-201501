package next.controller;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.QuestionDao;
import next.model.Question;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;

public class ApiFindQuestionsController extends AbstractController{
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		QuestionDao questionDao = new QuestionDao();
		List<Question> questions;
		questions = questionDao.findAll();
		
		final GsonBuilder builder = new GsonBuilder();
	    builder.excludeFieldsWithoutExposeAnnotation();
	    final Gson gson = builder.create();
	    PrintWriter out = response.getWriter();
	    Iterator<Question> itr = questions.iterator();
	    
	    while(itr.hasNext()){
	    	Question each = itr.next();
	    	String jsonData = gson.toJson(each);
		    response.setContentType("application/json;charset=UTF-8");
		    out.println(jsonData);
	    }
	    ModelAndView mav = jstlView("/list.jsp");
	    return mav;
		
	}

}
