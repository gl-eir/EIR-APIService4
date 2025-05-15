package com.ceir.CeirCode.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceir.CeirCode.exceptions.ResourceServicesException;
import com.ceir.CeirCode.model.app.StateMgmtDb;
import com.ceir.CeirCode.model.app.StatesInterpretationDb;
import com.ceir.CeirCode.repo.app.StateMgmtRepository;
import com.ceir.CeirCode.repo.app.StatesInterpretaionRepository;

@Service
public class StateMgmtServiceImpl {

	private static final Logger logger = LogManager.getLogger(StateMgmtServiceImpl.class);

	@Autowired
	private StateMgmtRepository stateMgmtRepository;

	@Autowired
	private StatesInterpretaionRepository statesInterpretaionRepository; 


	
	/*
	 * 
	 * @future_scope : must use a join between state_mgmt_db and states_interpretation_db
	 */
	public List<StateMgmtDb> getByFeatureIdAndUserTypeId(Integer featureId, Integer userTypeId) {
		try {
			List<StateMgmtDb> stateMgmtDbsResult = new ArrayList<StateMgmtDb>();

			logger.info("Going to get states by featureId and usertypeId ");

			List<StateMgmtDb> stateMgmtDbs = stateMgmtRepository.getByFeatureIdAndUserTypeId(featureId, userTypeId);

			List<StatesInterpretationDb> statesInterpretationDbs = statesInterpretaionRepository.findByFeatureId(featureId);
			logger.debug(statesInterpretationDbs);

			for(StatesInterpretationDb statesInterpretationDb : statesInterpretationDbs) {

				for(StateMgmtDb stateMgmtDb : stateMgmtDbs) {
					if(stateMgmtDb.getState().equals(statesInterpretationDb.getState())) {
						stateMgmtDb.setInterp(statesInterpretationDb.getInterp());
						stateMgmtDbsResult.add(stateMgmtDb);
					}
				}
			}

			return stateMgmtDbsResult;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	/*
	 * 
	 */
	
}
