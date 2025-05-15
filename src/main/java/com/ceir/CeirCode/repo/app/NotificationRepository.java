package com.ceir.CeirCode.repo.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ceir.CeirCode.model.app.Notification;


public interface NotificationRepository extends JpaRepository<Notification, Long>, JpaSpecificationExecutor<Notification>{




}
