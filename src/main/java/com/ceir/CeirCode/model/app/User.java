package com.ceir.CeirCode.model.app;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
//@Audited
@Table(name = "User")
public class User extends AllRequest {//implements UserDetails{

    private static long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;

    @Column(nullable = false)
    @JsonIgnore
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime passwordDate;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedOn;
    @Transient
    private String currentStatusInterp;
    private Integer currentStatus;
    private Integer previousStatus;
    private String remark;

    private Integer parentId = 0;

    private String userLanguage;

    private String modifiedBy;

    private String referenceId;

    private String approvedBy;

    //@NotAudited
    //@JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    UserProfile userProfile;

	/*
	 * @NotAudited
	 * 
	 * @JsonIgnore
	 * 
	 * @Transient
	 * 
	 * @OneToOne(mappedBy = "userDetails", cascade = {CascadeType.PERSIST,
	 * CascadeType.REMOVE}, fetch = FetchType.EAGER) UserTemporarydetails
	 * userTemporarydetails;
	 */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_type_id", nullable = false)
    private Usertype usertype;

    @NotAudited
    @JsonIgnore
    @OneToMany(mappedBy = "userData", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Userrole> userRole;

   // @NotAudited
   // @JsonIgnore
  //  @OneToMany(mappedBy = "userPayment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   // private List<UserPayment> userPayments;

    @NotAudited
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserSecurityquestion> userSecurityquestion;

    @NotAudited
    @JsonIgnore
    @OneToMany(mappedBy = "userPassword", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserPasswordHistory> userPasswordHistory;

//    @JsonIgnore
//    @NotAudited
//    @OneToMany(mappedBy = "userSlaReport", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<SlaReport> SlaReport;

    @NotAudited
    @JsonIgnore
    @OneToMany(mappedBy = "currentUserLogin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<CurrentLogin> currentLogin;

    @Transient
    private String stateInterp;

    @Transient
    private Long contactNumber;

    @Transient
    private String email;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = modifiedOn;
    }

    public LocalDateTime getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public Usertype getUsertype() {
        return usertype;
    }

    public void
            setUsertype(Usertype usertype) {
        this.usertype = usertype;
    }

    public List<UserSecurityquestion> getUserSecurityquestion() {
        return userSecurityquestion;
    }

    public void setUserSecurityquestion(List<UserSecurityquestion> userSecurityquestion) {
        this.userSecurityquestion = userSecurityquestion;
    }

    public List<Userrole> getUserRole() {
        return userRole;
    }

    public void setUserRole(List<Userrole> userRole) {
        this.userRole = userRole;
    }



    public Integer getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Integer getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(Integer previousStatus) {
        this.previousStatus = previousStatus;
    }

	/*
	 * public UserTemporarydetails getUserTemporarydetails() { return
	 * userTemporarydetails; }
	 * 
	 * public void setUserTemporarydetails(UserTemporarydetails
	 * userTemporarydetails) { this.userTemporarydetails = userTemporarydetails; }
	 */

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    //	public List<TypeApprovedDb> getTypeApprovedDb() { return typeApprovedDb; }
    //	public void setTypeApprovedDb(List<TypeApprovedDb> typeApprovedDb) {
    //		this.typeApprovedDb = typeApprovedDb; }
    //
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getUserLanguage() {
        return userLanguage;
    }

    public void setUserLanguage(String userLanguage) {
        this.userLanguage = userLanguage;
    }

    public List<UserPasswordHistory> getUserPasswordHistory() {
        return userPasswordHistory;
    }

    public void setUserPasswordHistory(List<UserPasswordHistory> userPasswordHistory) {
        this.userPasswordHistory = userPasswordHistory;
    }

    public User() {
        super();
    }

    public User(String username, String password, Integer currentStatus, Integer previousStatus, Usertype usertype) {
        super();
        this.username = username;
        this.password = password;
        this.currentStatus = currentStatus;
        this.previousStatus = previousStatus;
        this.usertype = usertype;
    }

    public User(String username, String password, Integer currentStatus, String remark, UserProfile userProfile, Usertype usertype, List<Userrole> role) {
        super();
        this.username = username;
        this.password = password;
        this.currentStatus = currentStatus;
        this.remark = remark;
        this.userProfile = userProfile;
        this.usertype = usertype;
        this.userRole = role;
    }

    public User(long id, String username, String password, Integer currentStatus, String remark, UserProfile userProfile, Usertype usertype, List<Userrole> role) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.currentStatus = currentStatus;
        this.remark = remark;
        this.userProfile = userProfile;
        this.usertype = usertype;
        this.userRole = role;
    }

    public User(long id) {
        super();
        this.id = id;
    }

    public User(Integer currentStatus, Integer previousStatus) {
        super();
        this.currentStatus = currentStatus;
        this.previousStatus = previousStatus;
    }

    public LocalDateTime getPasswordDate() {
        return passwordDate;
    }

    public void setPasswordDate(LocalDateTime passwordDate) {
        this.passwordDate = passwordDate;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static void setSerialVersionUID(long serialVersionUID) {
        User.serialVersionUID = serialVersionUID;
    }

    public String getStateInterp() {
        return stateInterp;
    }

    public void setStateInterp(String stateInterp) {
        this.stateInterp = stateInterp;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

	/*
	 * public List<UserPayment> getUserPayments() { return userPayments; }
	 * 
	 * public void setUserPayments(List<UserPayment> userPayments) {
	 * this.userPayments = userPayments; }
	 */

//    @JsonIgnore
//    public List<SlaReport> getSlaReport() {
//        return SlaReport;
//    }
//
//    public void setSlaReport(List<SlaReport> slaReport) {
//        SlaReport = slaReport;
//    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public List<CurrentLogin> getCurrentLogin() {
        return currentLogin;
    }

    public void setCurrentLogin(List<CurrentLogin> currentLogin) {
        this.currentLogin = currentLogin;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("User [id=");
        builder.append(id);
        builder.append(", username=");
        builder.append(username);
        builder.append(", password=");
        builder.append(password);
        builder.append(", createdOn=");
        builder.append(createdOn);
        builder.append(", modifiedOn=");
        builder.append(modifiedOn);
        builder.append(", currentStatus=");
        builder.append(currentStatus);
        builder.append(", previousStatus=");
        builder.append(previousStatus);
        builder.append(", remark=");
        builder.append(remark);
        builder.append(", parentId=");
        builder.append(parentId);
        builder.append(", userLanguage=");
        builder.append(userLanguage);
        builder.append(", modifiedBy=");
        builder.append(modifiedBy);
        builder.append(", referenceId=");
        builder.append(referenceId);
        builder.append(", approvedBy=");
        builder.append(approvedBy);
        builder.append(", stateInterp=");
        builder.append(stateInterp);
        builder.append("]");
        return builder.toString();
    }
    
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
//	}
//	@Override
//	public boolean isAccountNonExpired() {
//		return true;
//	}
//	@Override
//	public boolean isAccountNonLocked() {
//		return true;
//	}
//	@Override
//	public boolean isCredentialsNonExpired() {
//		return true;
//	}
//	@Override
//	public boolean isEnabled() {
//		return true;
//	}

}
