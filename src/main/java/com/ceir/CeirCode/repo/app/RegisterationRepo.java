package com.ceir.CeirCode.repo.app;
//package com.ceir.CeirCode.repo;
//
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.PreparedStatementCreator;
//import org.springframework.stereotype.Repository;
//import com.ceir.CeirCode.model.userProfile;
//@Repository
//public class RegisterationRepo {
//	private final Logger log = LoggerFactory.getLogger(getClass());	
//	
//	@Autowired
//	@Qualifier("postgresJdbcTemplate")	
//	protected JdbcTemplate postgresJdbcTemplate;
//	
//   public int saveUserProfile(userProfile userData) {
//		
//		  String
//		  query="insert into user_details(firstName,middleName,lastName,companyName,type,vatStatus,vatNo,"+
//		  "propertyLocation,street,locality,province,country,passportNo,phoneNo,userid,email)"
//		  + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//		 
//		/*
//		 * String
//		 * query="insert into user_details(firstName,middleName,lastName,companyName,type,vatStatus,vatNo,"
//		 * +
//		 * "propertyLocation,street,locality,province,country,passportNo,phoneNo,userid,email)"
//		 * + " values('"+userData.getFirstname()+"','"+userData.getMiddleName()+"','"+
//		 * userData.getLastName()+"'," +
//		 * "'"+userData.getCompanyName()+"','"+userData.getType()+"',"+userData.
//		 * getVatStatus()+",'"+userData.getVatNo()+"'," +
//		 * "'"+userData.getPropertyLocation()+"','"+userData.getStreet()+"','"+userData.
//		 * getLocality()+"'," +
//		 * "'"+userData.getProvince()+"','"+userData.getCountry()+"','"+userData.
//		 * getPassportNo()+"'," +
//		 * "'"+userData.getPhoneNo()+"',"+userData.getUserid()+",'"+userData.getEmail()+
//		 * "'," + ")";
//		 */	log.info("query to save user profile details:  "+query);
//	int i=0; 
//	try{
//		log.info("inside try");
//			
//			  final PreparedStatementCreator psc = new PreparedStatementCreator() { public
//			  PreparedStatement createPreparedStatement(final Connection connection) throws
//			  SQLException { final PreparedStatement pr_stmt =
//			  connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
//			  log.info("inside prepare statement");
//			  pr_stmt.setString(1,userData.getFirstname());
//			  pr_stmt.setString(2,userData.getMiddleName());
//			  pr_stmt.setString(3,userData.getLastName());
//			  pr_stmt.setString(4,userData.getCompanyName());
//			  pr_stmt.setString(5,userData.getType());
//			  pr_stmt.setInt(6,userData.getVatStatus());
//			  pr_stmt.setString(7,userData.getVatNo());
//			  pr_stmt.setString(8,userData.getPropertyLocation());
//			  pr_stmt.setString(9,userData.getStreet());
//			  pr_stmt.setString(10,userData.getLocality());
//			  pr_stmt.setString(11,userData.getProvince());
//			  pr_stmt.setString(12,userData.getCountry());
//			  pr_stmt.setString(13,userData.getPassportNo());
//			  pr_stmt.setString(14,userData.getPhoneNo());
//			 // pr_stmt.setInt(15,userData.getUserid());
//			  pr_stmt.setString(16,userData.getEmail());
//			  log.info("complted prepare statement"); return pr_stmt; }
//			  
//			  };
//			 
//		i=	postgresJdbcTemplate.update(psc);
//		log.info("try complete");
//
//		return i;
//	}
//	catch (Exception e) {
//		e.printStackTrace();
//		log.info("exception occurs in user addition  details: "+e.getMessage());
//		return 0;
//	}
//   }
//   public int saveUser(userProfile userRegister) {
//	   String query="insert into user(username,password,usertype) values(?,?,?)";
//	   log.info("query to save user data:  "+query);
//	   int i;   
//		try{   
//			final PreparedStatementCreator psc = new PreparedStatementCreator() {
//				public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
//					final PreparedStatement pr_stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
//					pr_stmt.setString(1,userRegister.getUsername());
//					pr_stmt.setString(2,userRegister.getPassword());
//					pr_stmt.setString(3, userRegister.getUsertype());
//					return pr_stmt;  
//				}
//			};
//			i=	postgresJdbcTemplate.update(psc);
//			return i;
//		}
//		catch (Exception e) {
//			log.info("if exception occur when get user data: "+e.getMessage()); 
//			e.printStackTrace();
//			return 0;
//		}
//   }
//		
//   public String getUserType(Integer usertypeId){
//	   String  query="select usertype from usertype where id=?";
//		  log.info("query to check user type id:  "+query); try{ return
//		  postgresJdbcTemplate.queryForObject(query,new Object[]{usertypeId}, String.class); 
//		  } catch(Exception e) {
//		  e.printStackTrace();
//		  log.info("if exception occur when get user data: "+e.getMessage()); return
//		  null; 
//		  } 
//		  }
//
//
//
//   public Integer getUserId(String username){
//	   String  query="select id from user where username=?";
//		  log.info("query to get user id:  "+query); try{ return
//		  postgresJdbcTemplate.queryForObject(query,new String[]{username}, Integer.class); 
//		  }  
//		  catch(Exception e) {
//		  e.printStackTrace();
//		  return 0; 
//		  } 
//		  }
//   
//public List<String> getRoles(Integer[] usertypeId){     
//	String  query="select usertype from usertype where id in ("+usertypeId+") ";
//	  log.info("query to check user type id:  "+query); try{ return
//	  postgresJdbcTemplate.queryForList(query, String.class); 
//	  } catch(Exception e) {
//	  e.printStackTrace();  
//	  log.info("if exception occur when get user data: "+e.getMessage()); return
//	  null; 
//	  }        
//}
//} 