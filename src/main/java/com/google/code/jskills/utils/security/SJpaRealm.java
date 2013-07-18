package com.google.code.jskills.utils.security;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.jskills.business.services.SecurityService;
import com.google.code.jskills.business.services.impl.SecurityServiceImpl;
import com.google.code.jskills.dal.entities.GroupRoleEntity;
import com.google.code.jskills.dal.entities.RoleEntity;
import com.google.code.jskills.dal.entities.RolePermissionEntity;
import com.google.code.jskills.dal.entities.UserEntity;
import com.google.code.jskills.dal.entities.UserGroupEntity;
import com.google.code.jskills.dal.entities.UserRoleEntity;


public class SJpaRealm extends AuthorizingRealm implements Serializable{
	
	private static EntityManager EM;
	
	static{
		EM = getEntityManager();
	}
	
	private static final long serialVersionUID = 101L;
	
	private static final Logger LOG = LoggerFactory
			.getLogger(SJpaRealm.class);

	private final String getUserByEmail = 	"SELECT u FROM " +  UserEntity.class.getName()
											+" u WHERE u.email = :email";	
	private String getUserUR;
	
	private String getUserUGR;
	
	private String getRolesPByRoles;													

	/**
	 * Queries initialization 
	 */
	private void initQueries(){
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT u FROM ");
		sb.append(UserEntity.class.getName());
		sb.append(" u JOIN FETCH u.userRoleEntities ur");
		sb.append(" JOIN FETCH ur.roleEntity ");
		sb.append(" WHERE u.email = :email");		
		getUserUR = sb.toString();
		
		sb = new StringBuilder();
		sb.append("SELECT u FROM ");
		sb.append(UserEntity.class.getName());
		sb.append(" u JOIN FETCH u.userGroupEntities ug");
		sb.append(" JOIN FETCH ug.groupEntity g");
		sb.append(" JOIN FETCH g.groupRoleEntities gr");
		sb.append(" JOIN FETCH gr.roleEntity");
		sb.append(" WHERE u.email = :email");
		getUserUGR = sb.toString();
		
		sb = new StringBuilder();
		sb.append("SELECT r FROM ");
		sb.append( RoleEntity.class.getName());
		sb.append(" r JOIN FETCH r.rolePermissionEntities rp");
		sb.append(" JOIN FETCH rp.permissionEntity");
		sb.append(" where r.name in :roleNames");
		getRolesPByRoles = sb.toString();
	}
	
	/**
	 * Getting Roles and Permissions for
	 * @param PrincipalCollection (user's email in our case)
	 * @return SimpleAuthorizationInfo with roles and permissions
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals){
		LOG.info("doGetAuthorizationInfo from SJpaRealm started");
		
		if(principals == null){
			LOG.error("PrincipalCollection is null");
			throw new AuthorizationException("PrincipalCollection can not be null");
		}
		
		if(principals.isEmpty()){
			LOG.error("PrincipalCollection is empty");
			throw new AuthorizationException("PrincipalCollection can not be empty");
		}
		
		/**
		 * In our one Realm Application principals is a one element list with user's login in it.
		 * For multiple Realms applications consider working with principals as List 
		 */
		LOG.info("principals size = " + principals.asList().size());
		
		String email = (String)principals.iterator().next();
		Set<String> resultRoles = new HashSet<String>();
		Set<Permission> resultPermissions	= new HashSet<Permission>();
		initQueries();
		try{
			/**
			 * Get roles throw user_role connection
			 */
			Query queryUR = EM.createQuery(getUserUR).setParameter("email", email);
			UserEntity user;
			try{
				user = (UserEntity) queryUR.getSingleResult();
				LOG.info(user.getFirstName()+user.getLastName());				
				if(!checkUser(user)) return null;				
				Set<UserRoleEntity> userRoles = (Set<UserRoleEntity>)user.getUserRoleEntities();
				for(UserRoleEntity ur: userRoles){
					resultRoles.add(ur.getRoleEntity().getName());
				}
			}catch(NoResultException e){
				LOG.error("No roles found for user");			
			}
			
			
			/**
			 * Get roles throw user_group -> group_role connection
			 */
			Query queryUGR = EM.createQuery(getUserUGR).setParameter("email", email);
			try{
				user = (UserEntity) queryUGR.getSingleResult();
				if(!checkUser(user)) return null;		
				LOG.info("doGetAuthorizationInfo for " + user.getEmail());
				Set<UserGroupEntity> userGroups = (Set<UserGroupEntity>)user.getUserGroupEntities();
				Set<GroupRoleEntity> userGroupRoles;
				
				for(UserGroupEntity ug: userGroups){
					 userGroupRoles =  ug.getGroupEntity().getGroupRoleEntities();
					 for(GroupRoleEntity gr: userGroupRoles){
						 resultRoles.add(gr.getRoleEntity().getName());
					 }
				}
			}catch(NoResultException nre){
				LOG.info("No groups found for user or group has no roles");
			}
			
			/**
			 * Get permissions for all roles from resultRoles
			 */		
			Query getRolesPQuery = EM.createQuery(getRolesPByRoles).setParameter("roleNames", resultRoles);
			try{
				List<RoleEntity> rolesP = (List<RoleEntity>)getRolesPQuery.getResultList();
				Set<RolePermissionEntity> rps;
				for (RoleEntity r: rolesP){
					rps = r.getRolePermissionEntities();
					for (RolePermissionEntity rp: rps){
						resultPermissions.add(new WildcardPermission(rp.getPermissionEntity().getName()));
					}
				}
			}catch(NoResultException nre){
				LOG.info("No permissions found for a role");
			}
			
			/**
			 * Main part: setting roles and permissions into AuthorizationInfo
			 */
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(resultRoles);
			info.setRoles(resultRoles); 
			info.setObjectPermissions(resultPermissions);
			LOG.info("doGetAuthorizationInfo succeed");
			return info;
			
		}catch(Exception e){
			LOG.error("doGetAuthorizationInfo failed");
			e.printStackTrace();
			throw new AuthorizationException("Unable to execute doGetAuthorizationInfo properly");	
		}	
	}

	/**
	 * Looks for user's email and password in DB
	 * @param AuthenticationToken with email and password entered by user in logging
	 * @return SimpleAccount with email and password
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo (AuthenticationToken token)
			throws AuthenticationException, InvalidParameterException {

		if (token == null){
			throw new InvalidParameterException("Null AuthenticationToken in realm");
		}
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		
		String email = upToken.getUsername();
		char[] password = upToken.getPassword();
		  
        if (email == null) {  
            throw new AccountException("Null emails are not allowed by this realm");  
        }
        
        if (password == null) {  
            throw new AccountException("Null passwords are not allowed by this realm");  
        }
        
        String pass = new String(password);
        
        SecurityService ss = new SecurityServiceImpl();
        
        String hashedPass = ss.computeHash(pass);
        
        LOG.info("Looking for user with email: " + email);
    
        //user from DB
        UserEntity user = null;
        try{
        	user = (UserEntity) EM.createQuery(getUserByEmail).setParameter("email", email).getSingleResult();
            LOG.info("Query from SJpaRealm succeed");
        }catch(Exception e){
        	LOG.error("Unable to execute query");
        	e.printStackTrace();
        	throw new AuthenticationException("No user with requested email found");
        }
        
        //User with such email has not been found
        if (user == null) {  
        	LOG.info("No user with requested email found");
            throw new UnknownAccountException("There is no user with such email: " + email);  
        }
        
        if (user.getIsDeleted()) {
       	LOG.info("User is deleted");
			throw new UnknownAccountException("There is no user with such email: " + email);
		}
        
        if (!user.getIsVerified()) {
       	LOG.info("User is not verified");
			throw new UnknownAccountException("There is no user with such email: " + email);
		}
        
        if (user.getIsBlocked()) {
       	LOG.info("User is blocked");
			throw new LockedAccountException("User with email: " + email + " is currently blocked!");
		}
        
        //Passwords do not match
        if ( !hashedPass.equals(user.getPassword()) ){
        	LOG.info("Passwords is not correct");
        	throw new AuthenticationException("Invalid password for user with email: " + email);
        }
        
        /**
         * Main part: setting email and password into SimpleAccount
         */
        SimpleAccount account = new SimpleAccount(email, password, getName());
        
//        char [] hashedPassowrd = hashedPass.toCharArray();
//        SimpleAccount account = new SimpleAccount(email, hashedPassowrd, getName());
		return account;
	}
	
	/**
	 * Get EntityManager from Persistence
	 * @return EntityManager
	 */
	protected static EntityManager getEntityManager(){
		
		EntityManagerFactory emf = null;
		EntityManager entityManager = null;
		
		try{
			emf = Persistence.createEntityManagerFactory("jskillsDB");
			entityManager = emf.createEntityManager();
		}catch(Exception e){
			LOG.error("Unable to obtain EntityManager in SJpaReam");
			e.printStackTrace();
			throw new AuthenticationException("Unable to obtain EntityManager in SJpaReam");
		}
		
		return entityManager;
	}

	protected boolean checkUser(UserEntity user){		
		if (user.getIsBlocked() || (user.getIsDeleted()) || (!user.getIsVerified())){
			return false;
		}		
		return true;
	}
}
