package com.ceir.CeirCode.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceir.CeirCode.Constants.TokenType;
import com.ceir.CeirCode.model.app.Token;
import com.ceir.CeirCode.model.app.User;
import com.ceir.CeirCode.repo.app.TokenRepository;

@Service
public class TokenService {
	
	@Autowired
	TokenRepository tokenRepository;
	
	public void saveUserToken(User user, String jwtToken) {
		Token token = new Token();
		token.setUserId(user.getId());
		token.setToken(jwtToken);
		token.setTokenType(TokenType.BEARER);
		token.setExpired(false);
		token.setRevoked(false);
		tokenRepository.save(token);
	}

	public void revokeAllUserTokens(User user) {
		List<Token> validUserTokens = tokenRepository.findAllValidTokenByUserId(user.getId());
	    if (validUserTokens.isEmpty())
	    	return;
	    validUserTokens.forEach(token -> {
	    	token.setExpired(true);
	    	token.setRevoked(true);
	    });
	    tokenRepository.saveAll(validUserTokens);
    }
	
}
