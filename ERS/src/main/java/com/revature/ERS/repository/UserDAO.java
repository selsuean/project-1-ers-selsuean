package com.revature.ERS.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.ERS.model.Reimbursement;
import com.revature.ERS.model.UserRole;
import com.revature.ERS.model.Users;
import com.revature.ERS.util.HibernateUtil;

public class UserDAO {

	public List<Users> allUsers() {

		/**
		 * Session interface has several methods that can be used in saving data to the
		 * db: persist, save, update, merge, saveorupdate Three main states in Session
		 * persistence context: - transient: instance is not and never was attached to a
		 * Session; has no corresponding row in database, usually a new object that you
		 * have created to save to the db - persistent: instance is associated with a
		 * unique Session object, when Session is flushed, this entity is guaranteed to
		 * have a correponsding consistent record in db - detached: instance was once
		 * attached to a Session but now is not
		 * 
		 * 
		 * What's inside createQuery is the Hibernate object to be mapped; should be
		 * closer to the class name (instead of the table name)
		 */
		Session session = HibernateUtil.getSession();
		return session.createQuery("from Users").list();
	}

	public Users thisUser(String username) {
		Users exist = null;

		List<Users> users = new ArrayList<>();
		Session session = HibernateUtil.getSession();
		users = session.createQuery("from Users where username = :usernameVar").setString("usernameVar", username)
				.list();
		if (!users.isEmpty()) {
			exist = users.get(0);
		}
		return exist;
	}

	public int saveUser(Users u) {

		/**
		 * Transaction is a REST interface in Hibernate that manages ACID compliant
		 * transactions atomicity, consistency, isolation, durability
		 * 
		 * session.save(obj) will save the object into the db (move it to a persistent
		 * state)
		 */
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		int result = (int) session.save(u);
		tx.commit();
		return result;
	}

	public String thisUserRole(String username) {
		List<String> userRole = new ArrayList<>();
		UserRole ur = null;
		String role = null;

		Session session = HibernateUtil.getSession();
		userRole = session.createQuery("select u.userRole.roles from Users u where u.username = :usernameVar")
				.setString("usernameVar", username).list();
		// select u.userRole.roles from Users u where u.username = :usernameVar
		// select ur.roles from UserRole ur join ur.Users u where u.username =
		// :usernameVar
		// select ur.roles from UserRole ur join ur.urID u where u.username =
		// :usernameVar

		role = userRole.get(0);
		if (role.isEmpty()) {
			return role;
		}

		return role;
	}

	public List<Reimbursement> thisUserReimbursement(String username) {
		List<Reimbursement> rList = new ArrayList<>();
		Reimbursement r = null;

		Session session = HibernateUtil.getSession();
		rList = session.createQuery("from Reimbursement where author.username = :usernameVar")
				.setString("usernameVar", username).list();
		
		return rList;
	}
	
//	public void actionUser(String username, String field, String entry) {
//		Session session = HibernateUtil.getSession();
//		Transaction tx = session.beginTransaction();
//		Query query = session.createQuery("update Users set :fieldVar = :entryVar where username = :usernameVar")
//						.setString("fieldVar", field).setString("entryVar", entry).setString("usernameVar", username);
//		query.executeUpdate();
//		tx.commit();
//		//update ReimbursementStatus set rStatus = :actionVar where rID = :idVar		
//	}
	
	public void actionUsername(String username, String entry) {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("update Users set username = :entryVar where username = :usernameVar")
						.setString("entryVar", entry).setString("usernameVar", username);
		query.executeUpdate();
		tx.commit();
		//update ReimbursementStatus set rStatus = :actionVar where rID = :idVar		
	}
	
	public void actionPassword(String username, String entry) {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("update Users set password = :entryVar where username = :usernameVar")
						.setString("entryVar", entry).setString("usernameVar", username);
		query.executeUpdate();
		tx.commit();
		//update ReimbursementStatus set rStatus = :actionVar where rID = :idVar		
	}
	
	public void actionEmail(String username, String entry) {
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("update Users set email = :entryVar where username = :usernameVar")
						.setString("entryVar", entry).setString("usernameVar", username);
		query.executeUpdate();
		tx.commit();
		//update ReimbursementStatus set rStatus = :actionVar where rID = :idVar		
	}
}