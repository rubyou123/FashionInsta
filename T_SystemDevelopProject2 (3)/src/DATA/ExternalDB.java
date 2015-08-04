package DATA;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.*;

import Parsing.ParsingCategoryOfNaverShoping;

public class ExternalDB {
	
	public HashMap<String, Integer> getKeyWord()
	{
		HashMap<String, Integer> keywordList = new HashMap<String, Integer>();
		
		Connection con = null;
		java.sql.Statement stmt = null;
		ResultSet rs = null;
		try {
			ExternalDBConn connection = new ExternalDBConn();
			con = connection.getConnection();

			if (con != null) {
				System.out.println("External DB(insert), Success Conn!");
			} else {
				System.out.println("External DB(insert), fail Conn!");
			}
	
			stmt = con.createStatement();
			String sql = "select idkeyword,word from keyword" ; 
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				keywordList.put(rs.getString("word"), rs.getInt("idkeyword"));
				
				System.out.println(rs.getString("word") + "  :  " + rs.getInt("idkeyword"));
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ExternalDBConn.close(rs, stmt, con);
		return keywordList;
	}
	
	public void InsertFashionPost(HashMap<String, Double> fashionPostList, int id){
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

				String sql = "update board set weight = '" + e.getValue() + "' where postID = '" + e.getKey() +"' and keyword_idkeyword  = '" + id +"'" ; 
				System.out.println(sql);
				stmt.executeUpdate(sql);
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("External DB(insert) Conn, Exit");
		ExternalDBConn.close(rs, stmt, con);
	}

	public HashMap<String,Integer> getWordList(int idKeyword) {
		
		HashMap<String,Integer> wordList = new HashMap<String,Integer>();
		
		Connection con = null;
		java.sql.Statement stmt = null;
		ResultSet rs = null;
		int tagcount = 0;
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
			String sql = "select * from tag where keyword_idkeyword = '" + idKeyword + "'";
			rs = stmt.executeQuery(sql);			

			while (rs.next()) {
			
				wordList.put(rs.getString("tag"), rs.getInt("count"));
				System.out.println("tag : " + rs.getString("tag") + "  count : " + rs.getInt("count"));
				tagcount++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		System.out.println("총 tag 수 : " + tagcount);
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
			String sql = "select board_postID,tag from boardtag";
			rs = stmt.executeQuery(sql);			
			
			while (rs.next()) {
			
				if(postList.containsKey(rs.getString("board_postID"))){
					String pID = rs.getString("board_postID");
					postList.put(pID,  postList.get(pID)+ "#"+rs.getString("tag"));
				}
				else{
					postList.put(rs.getString("board_postID"),rs.getString("tag"));
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
