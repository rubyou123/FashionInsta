package DATA;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.*;

import Parsing.ParsingCategoryOfNaverShoping;

public class ExternalDB {
	
	public void InsertFashionPost(HashMap<String, Double> fashionPostList){
		Connection con = null;
		java.sql.Statement stmt = null;
		ResultSet rs = null;
		
		System.out.println("Please, External(insert) DB Connection!");
		try {
			ExternalDBConn connection = new ExternalDBConn();
			con = connection.getConnection();

			if (con != null) {
				System.out.println("External DB(insert), Success Conn!");
			} else {
				System.out.println("External DB(insert), fail Conn!");
			}
	
			stmt = con.createStatement();
			
			Set<Entry<String, Double>> set = fashionPostList.entrySet();
			Iterator<Entry<String,Double>> iter = set.iterator();
			
			while(iter.hasNext()){
				Map.Entry<String, Double> e = (Map.Entry<String, Double>)iter.next();
				System.out.println(e.getKey() + "  :  " + e.getValue());
				String sql = "insert into datatoputapp VALUES ('" + e.getKey() +"' ,'" + e.getValue() +"')" ; 
				System.out.println(sql);
				stmt.executeUpdate(sql);
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("External DB(insert) Conn, Exit");
		ExternalDBConn.close(rs, stmt, con);
	}

	public HashMap<String,Integer> getWordList() {
		
		HashMap<String,Integer> wordList = new HashMap<String,Integer>();
		
		Connection con = null;
		java.sql.Statement stmt = null;
		ResultSet rs = null;
		
		System.out.println("Please, External DB Connection!");
		try {
			ExternalDBConn connection = new ExternalDBConn();
			con = connection.getConnection();

			if (con != null) {
				System.out.println("External DB, Success Conn!");
			} else {
				System.out.println("External DB, fail Conn!");
			}
			
			stmt = con.createStatement();
			String sql = "select wordName,count from word2";
			rs = stmt.executeQuery(sql);			

			while (rs.next()) {
			
				wordList.put(rs.getString("wordName"), rs.getInt("count"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		System.out.println("External DB(word2test) Conn, Exit");
		ExternalDBConn.close(rs, stmt, con);
		
		return wordList;
	}
	
	public HashMap<String,String> getPostIdList(){
		
		HashMap<String,String> postList = new HashMap<String,String>();
		
		Connection con = null;
		java.sql.Statement stmt = null;
		ResultSet rs = null;
		
		System.out.println("Please, External DB Connection!");
		try {
			ExternalDBConn connection = new ExternalDBConn();
			con = connection.getConnection();

			if (con != null) {
				System.out.println("External DB, Success Conn!");
			} else {
				System.out.println("External DB, fail Conn!");
			}
			
			stmt = con.createStatement();
			String sql = "select postID,wordName from word";
			rs = stmt.executeQuery(sql);			
			
			while (rs.next()) {
			
				if(postList.containsKey(rs.getString("postID"))){
					String pID = rs.getString("postID");
					postList.put(pID,  postList.get(pID)+ "#"+rs.getString("wordName"));
				}
				else{
					postList.put(rs.getString("postID"),rs.getString("wordName"));
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		System.out.println("External DB(wordtest) Conn, Exit");
		ExternalDBConn.close(rs, stmt, con);
		
		return postList;
	}
}
