package DATA;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InstaInfoDB {

	Connection con = null;
	String dbURL;
	Statement st = null;
	ResultSet rs = null;

	public InstaInfoDB(String dbName) {
		try {
			String driverName = "com.mysql.jdbc.Driver";
			Class.forName(driverName);
			String url = "jdbc:mysql://localhost:3306/" + dbName;
			String id = "root";
			String password = "apmsetup";
			con = DriverManager.getConnection(url, id, password);
			st = con.createStatement();
			
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createBoardTable(String tableName) {
		try {
			String sql = "create table " + tableName + "Board ( " + "stringID varchar(255) not null, "
					+ "numberID varchar(255) not null, " + "postID varchar(255) not null, "
					+ "imagesURL varchar(255) not null, " + "createdTime int(11) not null, "
					+ "content varchar(255) not null, " + "likeCount int(11) not null, "
					+ "replyCount int(11) not null, " + "primary key(postID)" + ")";
			st.executeUpdate(sql);
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}
	}

	public void createBoardTagTable(String tableName) {
		try {
			String sql = "create table " + tableName + "BoardTag ( " + "postID varchar(255) not null, "
					+ "tag varchar(255) not null, " + "count int(11) not null " + ")";
			st.executeUpdate(sql);
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}
	}

	public void createTagTable(String tableName) {
		try {
			String sql = "create table " + tableName + "Tag ( " + "tag varchar(255) not null, "
					+ "count int(11) not null " + ")";
			st.executeUpdate(sql);
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}
	}

	// Board 테이블에 게시글 하나 삽입
	public void insertBoard(String tableName, InstaInfo info) {
		try {
			String sql = "insert into " + tableName + "Board values('" + info.getStringID() + "','" + info.getNumberID()
					+ "','" + info.getPostID() + "','" + info.getImagesURL() + "','" + info.getCreateTime() + "','"
					+ info.getContent() + "','" + info.getLikeCount() + "','" + info.getReplyCount() + "')";
			st.executeUpdate(sql);
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}
	}

	// BoardTag table의 (postID, 태그, 한게시글당 댓글 빈도수) 삽입
	public void insertBoardTag(String tableName, String postID, String tag, int count) {
		try {
			String sql = "insert into " + tableName + "BoardTag values('" + postID + "','" + tag + "','" + count + "')";
			st.executeUpdate(sql);
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}
	}

	// Tag table의 (태그, 총 게시글의 태그 빈도수)
	public void insertTag(String tableName, String tag, int count) {
		try {
			String sql = "insert into " + tableName + "Tag values('" + tag + "','" + count + "')";
			st.executeUpdate(sql);
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}
	}

	// BoardTag table의 count Update
	public void updateBoardTag(String tableName, String postID, String tag, int count) {
		try {
			String sql = "update " + tableName + "BoardTag set count = '" + count + "' where postID = '" + postID
					+ "' and tag = '" + tag + "'";
			st.executeUpdate(sql);
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}
	}

	// Tag table의 count Update
	public void updateTag(String tableName, String postID, String tag, int count) {
		try {
			String sql = "update " + tableName + "Tag set count = '" + count + "' where tag = '" + tag + "'";
			st.executeUpdate(sql);
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}
	}

	// Tag 테이블의 tag를 삭제
	public void deleteTag(String tableName, String tag) {
		try {
			String sql = "delete from " + tableName + "Tag where tag like '%" + tag + "%' ";
			st.executeUpdate(sql);
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}
	}

	// BoardTag 테이블의 tag를 삭제
	public void deleteBoardTag(String tableName, String tag) {
		try {
			String sql = "delete from " + tableName + "BoardTag where tag like '%" + tag + "% '";
			st.executeUpdate(sql);
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}
	}
	public void closeDB()
	{
	
		try {
			rs.close();
			st.close();
			con.close();
		} catch (SQLException sqex) {
			// TODO Auto-generated catch block
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}
	}
public boolean checkKeyWord(int year, String season, String keyWord)
	{
		boolean check = true;
		try {
			String sql = "select * from keyword where year = '" + year + "' and season = '" 
		+ season + "' and keyword = '" + keyWord +"'";
			rs = st.executeQuery(sql);
			if(rs.next())
			{
				check = true;
			}
			else
			{
				check = false;
			}
				
		} catch (SQLException sqex) {
			// TODO Auto-generated catch block
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}
		return check;
	}
	
	public void insertKeyWord(int year, String season, String keyWord)
	{
		try {
			String sql = "insert into keyword values('" + year + "','" + season + "','" + keyWord + "')";
			st.executeUpdate(sql);
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}
	}
	public int searchEarlyCreatedTime(String tableName)
	{
		int createdTime = -1;
		try {
			String sql = "select max(createdTime) from "+ tableName +"board";
			rs = st.executeQuery(sql);
			
			while(rs.next())
			{
				createdTime = rs.getInt(1);
			}
		} catch (Exception e) {
	//		System.out.println("SQLException: " + sqex.getMessage());
	//		System.out.println("SQLState: " + sqex.getSQLState());
			e.getStackTrace();
		}
		return createdTime;
	}
	
}
