package com.ceir.CeirCode.repo.app;


import com.ceir.CeirCode.model.app.LinkDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

@Transactional(rollbackOn = {SQLException.class})
@Repository
public interface LinkRepository extends JpaRepository<LinkDetails, String>, JpaSpecificationExecutor<LinkDetails> {
	List<LinkDetails> findByStatus(String status);
	@Modifying
	@Query("UPDATE LinkDetails SET linkState =:#{#a.linkState},status =:#{#a.status} WHERE linkId =:#{#a.linkId}")
	int update(@Param("a") LinkDetails linkDetails);

	@Modifying
	@Query("UPDATE LinkDetails SET status =:#{#b.status} WHERE linkId =:#{#b.linkId}")
	int updateStatus(@Param("b") LinkDetails linkDetails);


}
