//package com.ceir.CeirCode.SpecificationBuilder;
//
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//
//import javax.persistence.Tuple;
//import javax.persistence.criteria.Expression;
//import javax.validation.Path;
//
//import org.springframework.data.jpa.domain.Specification;
//
//import com.ceir.CeirCode.model.SearchCriteria;
//import com.ceir.CeirCode.model.TypeApprovedDb;
//import com.ceir.CeirCode.Constants.Datatype;
//import com.ceir.CeirCode.Constants.SearchOperation;
//import com.ceir.CeirCode.util.DbFunctions;
//
//
//public class TypeApprovedSpecificationBuilder {
//	private final List<SearchCriteria> params;
//	private final String dialect;
//	private List<Specification<TypeApprovedDb>> specifications;
//
//
//	public TypeApprovedSpecificationBuilder(String dialect) {
//		params = new ArrayList<>();
//		specifications = new LinkedList<>();
//		this.dialect = dialect;
//	}
//
//	public final TypeApprovedSpecificationBuilder with(SearchCriteria criteria) { 
//		params.add(criteria);
//		return this;
//	}
//
//	public Specification<TypeApprovedDb> build() { 
//
//		Specification<TypeApprovedDb> finalSpecification = null;
//
//		createSpecifications();
//
//		if(!specifications.isEmpty()) {
//			finalSpecification = Specification.where(specifications.get(0));
//
//			for(int i = 1; i<specifications.size() ;i++) {
//				finalSpecification = finalSpecification.and(specifications.get(i));
//			}
//		}
//
//		return finalSpecification;
//	}
//
//	private List<Specification<TypeApprovedDb>> createSpecifications(){
//
//		try {
//			for(SearchCriteria searchCriteria : params) {
//				specifications.add((root, query, cb)-> {
//					 //Path<Tuple> tuple = root.<Tuple>get(searchCriteria);
//					if(SearchOperation.GREATER_THAN.equals(searchCriteria.getSearchOperation())
//							&& Datatype.STRING.equals(searchCriteria.getDatatype())) {
//						return cb.greaterThan(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
//					}
//					else if(SearchOperation.LESS_THAN.equals(searchCriteria.getSearchOperation())
//							&& Datatype.STRING.equals(searchCriteria.getDatatype())) {
//						return cb.lessThan(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
//					}
//					else if(SearchOperation.EQUALITY.equals(searchCriteria.getSearchOperation())
//							&& Datatype.STRING.equals(searchCriteria.getDatatype())) {
//						return cb.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
//					}
//					else if(SearchOperation.GREATER_THAN.equals(searchCriteria.getSearchOperation())
//							&& Datatype.DATE.equals(searchCriteria.getDatatype())){
//						Expression<String> dateStringExpr = cb.function(DbFunctions.getDate(dialect), String.class, root.get(searchCriteria.getKey()), cb.literal(DbFunctions.getDateFormat(dialect)));
//						return cb.greaterThan(cb.lower(dateStringExpr), searchCriteria.getValue().toString());
//					}
//					else if(SearchOperation.LESS_THAN.equals(searchCriteria.getSearchOperation())
//							&& Datatype.DATE.equals(searchCriteria.getDatatype())){
//						Expression<String> dateStringExpr = cb.function(DbFunctions.getDate(dialect), String.class, root.get(searchCriteria.getKey()), cb.literal(DbFunctions.getDateFormat(dialect)));
//						return cb.lessThan(cb.lower(dateStringExpr), searchCriteria.getValue().toString());
//					}
//					else {
//						return null;
//					}
//				});
//			}
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return specifications;
//	}
//}
