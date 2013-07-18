package com.google.code.jskills;

import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.shiro.annotation.AnnotationsShiroAuthorizationStrategy;
import org.wicketstuff.shiro.authz.ShiroUnauthorizedComponentListener;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;

import org.apache.wicket.Page;
import org.apache.wicket.cdi.CdiConfiguration;
import org.apache.wicket.cdi.ConversationPropagation;
import org.apache.wicket.protocol.http.WebApplication;

import com.google.code.jskills.pages.AdministrativeTools;
import com.google.code.jskills.pages.Dashboard;
import com.google.code.jskills.pages.Index;
import com.google.code.jskills.pages.Profiles;
import com.google.code.jskills.pages.Report;
//import com.google.code.jskills.pages.RolesList;
import com.google.code.jskills.pages.TestsExecution;
import com.google.code.jskills.pages.TestsManagement;
import com.google.code.jskills.pages.admin.AdminHome;
import com.google.code.jskills.pages.admin.PermissionsList;
import com.google.code.jskills.pages.admin.PickUserRoles;
import com.google.code.jskills.pages.admin.UsersList;
import com.google.code.jskills.pages.admin.EditUser;
import com.google.code.jskills.pages.admin.RolesList;
import com.google.code.jskills.pages.auth.LoginPage;
import com.google.code.jskills.pages.auth.RestrictedPage;
import com.google.code.jskills.pages.competence.AddCompetences;
import com.google.code.jskills.pages.competence.CompetencesPage;
import com.google.code.jskills.pages.forgotpassword.ForgotPassword;
import com.google.code.jskills.pages.forgotpassword.RestorePasswordPage;
import com.google.code.jskills.pages.profile.CreateProfile;
import com.google.code.jskills.pages.profile.ProfilesList;
import com.google.code.jskills.pages.registration.DoneRegistration;
import com.google.code.jskills.pages.registration.Registration;
import com.google.code.jskills.pages.registration.VerificationPage;
import com.google.code.jskills.pages.testmanagement.AnswerListPage;
import com.google.code.jskills.pages.testmanagement.CreateAnswer;
import com.google.code.jskills.pages.testmanagement.CreateQuestion;
import com.google.code.jskills.pages.testmanagement.CreateTest;
import com.google.code.jskills.pages.testmanagement.EditAnswer;
import com.google.code.jskills.pages.testmanagement.EditQuestion;
import com.google.code.jskills.pages.testmanagement.EditTest;
import com.google.code.jskills.pages.testmanagement.QuestionListPage;
import com.google.code.jskills.pages.testmanagement.TestListPage;
import com.google.code.jskills.utils.security.SJpaRealm;

public class MainApplication extends WebApplication {

	@Override
	public Class<? extends Page> getHomePage() {
		return Index.class;
	}

	private static final Logger LOG = LoggerFactory
			.getLogger(MainApplication.class);

	@Override
	protected void init() {
		super.init();
		// Enable CDI
		BeanManager bm;
		try {
			bm = (BeanManager) new InitialContext()
					.lookup("java:comp/BeanManager");
		} catch (NamingException e) {
			LOG.error("Error during context initialization", e);

			throw new IllegalStateException("Unable to obtain CDI BeanManager",
					e);
		}

		// Configure CDI, disabling Conversations as we aren't using them
		new CdiConfiguration(bm).setPropagation(ConversationPropagation.NONE)
				.configure(this);

		initSecurity();

		// Mount the InsertContact page at /insert
		mountPage("/auth", LoginPage.class);
		mountPage("/admin", AdminHome.class);
		mountPage("/adminTools", AdministrativeTools.class);
		mountPage("/dashboard", Dashboard.class);
		mountPage("/profiles", Profiles.class);
		mountPage("/testManagement", TestsManagement.class);
		mountPage("/testManagement/testList", TestListPage.class);
		mountPage("/testManagement/questionList", QuestionListPage.class);
		mountPage("/testManagement/answerList", AnswerListPage.class);
		mountPage("/testManagement/testList/edit", EditTest.class);
		mountPage("/testManagement/questionList/edit", EditQuestion.class);
		mountPage("/testManagement/answerList/edit", EditAnswer.class);
		
		mountPage("/testManagement/testList/create", CreateTest.class);
		mountPage("/testManagement/questionList/create", CreateQuestion.class);
		mountPage("/testManagement/answerList/create", CreateAnswer.class);
		
		mountPage("/testExecution", TestsExecution.class);
		mountPage("/reporting", Report.class);
		// mountPage("/rolesList", RolesList.class);
		mountPage("/index", Index.class);
		mountPage("/registration", Registration.class);
		mountPage("/doneregistration", DoneRegistration.class);
		mountPage("/verification", VerificationPage.class);
		mountPage("/restorepassword", RestorePasswordPage.class);
		mountPage("/forgotpassword", ForgotPassword.class);
		mountPage("/admin/users", UsersList.class);
		mountPage("/admin/users/edit", EditUser.class);
		mountPage("/admin/roles", RolesList.class);
		mountPage("/admin/picklist", PickUserRoles.class);
		mountPage("/admin/permissions", PermissionsList.class);
		mountPage("/competencespage", CompetencesPage.class);
		mountPage("/addcompetence", AddCompetences.class);
		mountPage("profiles/profilesList", ProfilesList.class);
		mountPage("profiles/profilesList/createProfile", CreateProfile.class);
		// mountPage("/insert", InsertContact.class);

	}

	private void initSecurity() {

		SJpaRealm realm = new SJpaRealm();

		DefaultSecurityManager securityManager = new DefaultSecurityManager(
				realm);
		SecurityUtils.setSecurityManager(securityManager);

		AnnotationsShiroAuthorizationStrategy strategy = new AnnotationsShiroAuthorizationStrategy();
		getSecuritySettings().setAuthorizationStrategy(strategy);

		getSecuritySettings().setUnauthorizedComponentInstantiationListener(
				new ShiroUnauthorizedComponentListener(LoginPage.class,
						RestrictedPage.class, strategy));
	}
}
