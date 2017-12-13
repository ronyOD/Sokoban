package leaderboard;

import java.util.List;
import java.util.Optional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import controller.MyController;
import model.data.hibernate.Records;

/**
* <h1>DAL</h1>
* DATA ACCESS LAYER - reads from database using ORM
* @author  Ron Yaish
* @version 1.0
* @since   13-07-2017
*/
public class DAL {

	/**
	* This method is used to insert records to db using Hibernate ORM
	* @param res
	*/
	@SuppressWarnings("deprecation")
	public void InsertRecord(Optional<String> res){
		Configuration con = new Configuration();
		con.configure("hibernate.cfg.xml");
		SessionFactory SF = con.buildSessionFactory();
		Session session = SF.openSession();
		
		model.data.hibernate.Records records = new model.data.hibernate.Records();
		records.setUserName(res.get());
		records.setLevelName(MyController.it().getModel().getLevel().getLevelName());
		records.setNumOfSteps(MyController.it().getModel().getLevel().getmovesCount());
		records.setTime(MyController.it().getModel().getLevel().gettotalTime());
		
		Transaction TR = session.beginTransaction();
		session.save(records);
		System.out.println("Object Saved");
		TR.commit();
		session.close();
		SF.close();
	}
	
	//get records by LevelName
	/**
	* This method is used to get record from db via Hibernate ORM.
	* @param LevelName
	* @return a list of records
	*/
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Records> GetData(String LevelName){
		
		Configuration con = new Configuration();
		con.configure("hibernate.cfg.xml");
		SessionFactory SF = con.buildSessionFactory();
		Session session = SF.openSession();
		
		String hql = "FROM Records WHERE LevelName = '" + LevelName + "' ORDER BY NumOfSteps";
		Query query = session.createQuery(hql);
		//query.setFirstResult(28);
		query.setMaxResults(10);
		List<Records> results = query.list();
		return results;
	}
	
	//get records by UserName
	/**
	* This method is used to get record from db via Hibernate ORM.
	* @param UserName
	* @return a list of records
	*/
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Records> GetDataByUserName(String UserName){
		
		Configuration con = new Configuration();
		con.configure("hibernate.cfg.xml");
		SessionFactory SF = con.buildSessionFactory();
		Session session = SF.openSession();
		
		String hql = "From Records WHERE UserName = '"+ UserName + "' ORDER BY NumOfSteps";
		Query query = session.createQuery(hql);
		query.setMaxResults(10);
		List<Records> results = query.list();
		return results;
	}
}
