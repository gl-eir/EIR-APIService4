package com.ceir.CeirCode.SpecificationBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.domain.Specification;

import com.ceir.CeirCode.Constants.Datatype;
import com.ceir.CeirCode.Constants.SearchOperation;
import com.ceir.CeirCode.model.app.SearchCriteria;
import com.ceir.CeirCode.model.app.User;
import com.ceir.CeirCode.model.app.UserProfile;
import com.ceir.CeirCode.model.app.Userrole;
import com.ceir.CeirCode.model.app.Usertype;
import com.ceir.CeirCode.util.DbFunctions;


public class SpecificationBuilder<T> {

	private static final Logger logger = LogManager.getLogger(SpecificationBuilder.class);

	private final List<SearchCriteria> params;
	private final List<SearchCriteria> searchParams;
	private final String dialect;
	private List<Specification<T>> specifications;

	public SpecificationBuilder(String dialect) {
		params = new ArrayList<>();
		searchParams = new ArrayList<>();
		specifications = new LinkedList<>();
		this.dialect = dialect;
	}

	public final SpecificationBuilder<T> with(SearchCriteria criteria) { 
		params.add(criteria);
		return this;
	}

	public final SpecificationBuilder<T> orSearch(SearchCriteria criteria) { 
		searchParams.add(criteria);
		return this;
	}

	public Specification<T> build() { 
		// convert each of SearchCriteria params to Specification and construct combined specification based on custom rules.

		
		Specification<T> finalSpecification = null;

		Specification<T> searchSpecification  = null;
		List<Specification<T>> specifications = createSpecifications( params );

		if(!specifications.isEmpty()) {
			finalSpecification = Specification.where(specifications.get(0));

			for(int i = 1; i<specifications.size(); i++) {
				finalSpecification = finalSpecification.and(specifications.get(i));
			}
		}

		if( !searchParams.isEmpty() ) {
			specifications = createSpecifications( searchParams );
			if( !specifications.isEmpty()) {
				
				searchSpecification = specifications.get(0);
				for(int i = 1; i<specifications.size() ;i++) {
					searchSpecification = searchSpecification.or(specifications.get(i));
				}
				
				finalSpecification = finalSpecification.and( searchSpecification );
			}
		}

		return finalSpecification;
	}

	public void addSpecification(Specification<T> specification) { 
		specifications.add(specification);
	}

	private List<Specification<T>> createSpecifications(List<SearchCriteria> criterias){

		try {
			for(SearchCriteria searchCriteria : params) {
				specifications.add((root, query, cb)-> {
					// Path<Tuple> tuple = root.<Tuple>get(searchCriteria);
					if(SearchOperation.GREATER_THAN.equals(searchCriteria.getSearchOperation())
							&& Datatype.STRING.equals(searchCriteria.getDatatype())) {
						return cb.greaterThan(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
					}
					else if(SearchOperation.LESS_THAN.equals(searchCriteria.getSearchOperation())
							&& Datatype.STRING.equals(searchCriteria.getDatatype())) {
						return cb.lessThan(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
					}
					else if(SearchOperation.LIKE.equals(searchCriteria.getSearchOperation())
							&& Datatype.STRING.equals(searchCriteria.getDatatype())) {
						return cb.like(root.get(searchCriteria.getKey()), "%" +searchCriteria.getValue().toString()+ "%");
					}
					else if(SearchOperation.EQUALITY.equals(searchCriteria.getSearchOperation())
							&& Datatype.STRING.equals(searchCriteria.getDatatype())) {
						return cb.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
					}
					else if(SearchOperation.GREATER_THAN.equals(searchCriteria.getSearchOperation())
							&& Datatype.DATE.equals(searchCriteria.getDatatype())){
						Expression<String> dateStringExpr = cb.function(DbFunctions.getDate(dialect), String.class, root.get(searchCriteria.getKey()), cb.literal(DbFunctions.getDateFormat(dialect)));
						return cb.greaterThanOrEqualTo(cb.lower(dateStringExpr), searchCriteria.getValue().toString());
					}
					else if(SearchOperation.LESS_THAN.equals(searchCriteria.getSearchOperation())
							&& Datatype.DATE.equals(searchCriteria.getDatatype())){
						Expression<String> dateStringExpr = cb.function(DbFunctions.getDate(dialect), String.class, root.get(searchCriteria.getKey()), cb.literal(DbFunctions.getDateFormat(dialect)));
						return cb.lessThanOrEqualTo(cb.lower(dateStringExpr), searchCriteria.getValue().toString());
					}
					
					else if(SearchOperation.EQUALITY.equals(searchCriteria.getSearchOperation())
							&& Datatype.DATE.equals(searchCriteria.getDatatype())){
						Expression<String> dateStringExpr = cb.function(DbFunctions.getDate(dialect), String.class, root.get(searchCriteria.getKey()), cb.literal(DbFunctions.getDateFormat(dialect)));
						return cb.equal(cb.lower(dateStringExpr), searchCriteria.getValue().toString());
					}
					else if(SearchOperation.EQUALITY.equals(searchCriteria.getSearchOperation())
							&& Datatype.INTEGER.equals(searchCriteria.getDatatype())) {
						return cb.equal(root.get(searchCriteria.getKey()),searchCriteria.getValue());
					} 
					
					else if(SearchOperation.EQUALITY.equals(searchCriteria.getSearchOperation())
							&& Datatype.ARRAYLIST.equals(searchCriteria.getDatatype())) {
						return cb.in(root.get(searchCriteria.getKey()));
					} 
					else {
						return null;
					}
				});
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return specifications;
	}

	public Specification<T> in(SearchCriteria searchCriteria, ArrayList<Long> status){
		return (root, query, cb) -> {
			logger.info("In query save ");
			logger.info("key= "+searchCriteria.getKey());
				//return cb.in(root.get(searchCriteria.getKey())).value(status);
				return root.get(searchCriteria.getKey()).in(status);
		};
	}
	
	public Specification<UserProfile> joinWithUser(SearchCriteria searchCriteria){
		return (root, query, cb) -> { 
			Join<UserProfile, User> user = root.join("user".intern());
		//	Join<User, Userrole> user = users.join("userRole".intern());
			if(SearchOperation.GREATER_THAN.equals(searchCriteria.getSearchOperation())
					&& Datatype.STRING.equals(searchCriteria.getDatatype())) {
				return cb.greaterThan(user.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
			}
			else if(SearchOperation.LESS_THAN.equals(searchCriteria.getSearchOperation())
					&& Datatype.STRING.equals(searchCriteria.getDatatype())) {
				return cb.lessThan(user.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
			}
			else if(SearchOperation.EQUALITY.equals(searchCriteria.getSearchOperation())
					&& Datatype.STRING.equals(searchCriteria.getDatatype())) {
				return cb.equal(user.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
			}
			else if(SearchOperation.EQUALITY.equals(searchCriteria.getSearchOperation())
					&& Datatype.INT.equals(searchCriteria.getDatatype())) {
				return cb.equal(user.get(searchCriteria.getKey()), (int)searchCriteria.getValue());
			}
			else if(SearchOperation.EQUALITY.equals(searchCriteria.getSearchOperation())
					&& Datatype.LONG.equals(searchCriteria.getDatatype())) {
				return cb.equal(user.get(searchCriteria.getKey()), (long)searchCriteria.getValue());
			}
			else if(SearchOperation.EQUALITY.equals(searchCriteria.getSearchOperation())
					&& Datatype.INTEGER.equals(searchCriteria.getDatatype())) {
				return cb.equal(user.get(searchCriteria.getKey()),searchCriteria.getValue());
			} 
			else if(SearchOperation.EQUALITY.equals(searchCriteria.getSearchOperation())
					&& Datatype.ARRAYLIST.equals(searchCriteria.getDatatype())) {
				return cb.in(user.get(searchCriteria.getKey())).value(searchCriteria.getValue());
			}
			else if(SearchOperation.EQUALITY.equals(searchCriteria.getSearchOperation())
					&& Datatype.LONG.equals(searchCriteria.getDatatype())) {
				return cb.equal(user.get(searchCriteria.getKey()), (Long)searchCriteria.getValue());
			}
			else if(SearchOperation.GREATER_THAN.equals(searchCriteria.getSearchOperation())
					&& Datatype.DATE.equals(searchCriteria.getDatatype())){
				Expression<String> dateStringExpr = cb.function(DbFunctions.getDate(dialect), String.class, user.get(searchCriteria.getKey()), cb.literal(DbFunctions.getDateFormat(dialect)));
				return cb.greaterThanOrEqualTo(cb.lower(dateStringExpr), searchCriteria.getValue().toString());
			}
			else if(SearchOperation.LESS_THAN.equals(searchCriteria.getSearchOperation())
					&& Datatype.DATE.equals(searchCriteria.getDatatype())){
				Expression<String> dateStringExpr = cb.function(DbFunctions.getDate(dialect), String.class, user.get(searchCriteria.getKey()), cb.literal(DbFunctions.getDateFormat(dialect)));
				return cb.lessThanOrEqualTo(cb.lower(dateStringExpr), searchCriteria.getValue().toString());
			}
			else if(SearchOperation.NEGATION.equals(searchCriteria.getSearchOperation())
					&& Datatype.STRING.equals(searchCriteria.getDatatype())) {
				return cb.notEqual(user.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
			}
			else if(SearchOperation.NEGATION.equals(searchCriteria.getSearchOperation())
					&& Datatype.INT.equals(searchCriteria.getDatatype())) {
				return cb.notEqual(user.get(searchCriteria.getKey()), (Integer)searchCriteria.getValue());
			}else if(SearchOperation.NEGATION.equals(searchCriteria.getSearchOperation())
					&& Datatype.LONG.equals(searchCriteria.getDatatype())) {
				return cb.notEqual(user.get(searchCriteria.getKey()), (Long)searchCriteria.getValue());
			}else {
				return null;
			}
			
		};
	}
	

	public Specification<UserProfile> joinWithMultiple(SearchCriteria searchCriteria){
		return (root, query, cb) -> {
			Join<UserProfile, User> addresses = root.join("user".intern());
			//Join<User, Usertype> userdetails = addresses.join("usertype".intern());	
			Join<User, Userrole> userRoles = addresses.join("userrole".intern());	
			return cb.equal(userRoles.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
		}; 
	}
	
	public Specification<T>  inQuery(String key,List<Integer> status){
		return (root, query, cb) -> {
			logger.info("In query save ");
			Join<UserProfile, User> user = root.join("user".intern());
		//	Join<User, Userrole> user = users.join("userRole".intern());
			//return cb.in(user.get(key)).value(status);
			return user.get(key).in(status);
		};
	}

	public Specification<T>  inQueryGroupBy(String key,List<Integer> status){
		return (root, query, cb) -> {
			logger.info("In query save ");
			//query.groupBy(root.get("firstName"));
			Join<UserProfile, User> user = root.join("user".intern());
		Join<User, Userrole> userRoles = user.join("userRole".intern());
		//query.groupBy(root.get("id"));
		//	Join<User, Userrole> user = users.join("userRole".intern());
			//return cb.in(user.get(key)).value(status);
			return userRoles.get(key).in(status);
		};
	}
	
//	  static Specification<User> hasRoles(List<String> roles) { 
//	return (root,query, cb) -> { query.distinct(true); 
//		  Join<User, Account> joinUserAccount =
//	  root.join(User_.account); Join<Account, AccountRole> acctRolesJoin =
//	  joinUserAccount.join(Account_.accountRoles); Join<AccountRole, Role>
//	  rolesJoin = acctRolesJoin.join(AccountRole_.role);
//	  
//	  return rolesJoin.get(Role_.name).in(roles); }; }
	 
    
    
}
