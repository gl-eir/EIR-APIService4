package com.ceir.CeirCode.repoService;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceir.CeirCode.model.app.Currency;
import com.ceir.CeirCode.model.app.PortAddress;
import com.ceir.CeirCode.repo.app.CurrencyRepo;

@Service
public class CurrencyRepoService {

	@Autowired
	CurrencyRepo currencyRepo;
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	public Currency getById(long id){
		try {
			return currencyRepo.findById(id);
		}
		catch(Exception e) {
			log.info("error occurs when fetching data by id");
			log.info(e.toString());
			return null;
		}
	}
	
	public Currency  save(Currency currency) {
		
		try {
			log.info("going to save  currency data");
			return currencyRepo.save(currency);
			
		}
		catch(Exception e) {
			log.info("error occurs while adding  currency data");
			log.info(e.toString());
			return  null;
		}
	}
	
public Currency  topDataByDate(Date date) {
		
		try {
			log.info("going to fetch data by top date");
			return currencyRepo.findTopByMonthDateOrderByIdDesc(date);
			
		}
		catch(Exception e) {
			log.info("error occurs while adding  currency data");
			log.info(e.toString());
			return  null;
		}
	}
	
public boolean existsByMonthDateAndCurrency(String month,Integer currency)
{

	try {
		log.info("going to check data by year month and currency");
		return currencyRepo.existsByMonthDateAndCurrency(month, currency);
		
	}
	catch(Exception e) {
		log.info("error occurs when going to check data by year month and currency");
		log.info(e.toString());
		return  false;
	}
}
}
