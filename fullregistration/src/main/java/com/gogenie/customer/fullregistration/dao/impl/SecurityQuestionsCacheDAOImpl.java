package com.gogenie.customer.fullregistration.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.gogenie.customer.fullregistration.dao.SecurityQuestionsCacheDAO;
import com.gogenie.customer.fullregistration.exception.CustomerRegistrationException;
import com.gogenie.customer.fullregistration.model.Questions;
import com.gogenie.customer.fullregistration.model.SecurityQuestionCache;
import com.gogenie.customer.fullregistration.model.SecurityQuestions;

@Named("questionsCache")
public class SecurityQuestionsCacheDAOImpl implements SecurityQuestionsCacheDAO {

	@Resource
	private DataSource gogenieDataSource;
	
	@Inject
	private SecurityQuestionCache securityQuestionCache;
	
	private SimpleJdbcCall retrieveQuestionsProc;
	private Logger logger = LoggerFactory.getLogger(SecurityQuestionsCacheDAOImpl.class);
	
	@Override
	@PostConstruct
	public void populateSecurityQuestionCache() throws CustomerRegistrationException {
		logger.debug("Entering into  populateSecurityQuestionCache");
		List<Questions> questionsList = null;
		try {
			retrieveQuestionsProc  = new SimpleJdbcCall(gogenieDataSource);
			retrieveQuestionsProc.withProcedureName("get_securityquestion").withoutProcedureColumnMetaDataAccess();
			Map<String, Object> result = retrieveQuestionsProc.execute();
			logger.debug("Security questions result set {} " , result.toString());
			List<Map<String, Object>> resultList = (List<Map<String, Object>>)result.get("#result-set-1");
			Map<String, Object> questionsMap = resultList.get(0);
			if (questionsMap.get("estatus") == null) {
				questionsList = new ArrayList<>();
				for (Map<String, Object> question : resultList) {
					Questions questionObj = new Questions();
					questionObj.setQuestionId((Integer)question.get("QUESTION_ID"));
					questionObj.setQuestion((String)question.get("SECURITY_QUESTION"));
					questionsList.add(questionObj);
				}
			}
			securityQuestionCache.setSecurityQuestionsList(questionsList);
		} catch (Exception e) {
			logger.error("Exception while building Security Questions Cache {} " , e.getMessage());
		}
		logger.debug("Exiting from populateSecurityQuestionCache");
	}

}
