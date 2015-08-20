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
	
	public int checkTagCount(int idkeyword, String tag)
	{
		int check = -1;
		try {
			String sql = "select count from tag where keyword_idkeyword = '" + idkeyword +"' and tag = '" + tag  + "'";
			rs = st.executeQuery(sql);
			
			if(rs.next())
				check = rs.getInt("count");
			
		} catch (Exception sqex) {
			
			sqex.getStackTrace();

		}
	//	System.out.println("[checkTagCount] idkeyword : " + idkeyword + " count : " + check +" tag : " +tag);
		return check;
	}

	public int checkBoard(int idkeyword, String postID)
	{
		int check = -1;
		try {
			String sql = "select * from board where keyword_idkeyword = '" + idkeyword +"' and postID = '" + postID  +"'";
			rs = st.executeQuery(sql);
			
			if(rs.next())
				check = 1;
			
		} catch (Exception sqex) {
			
			sqex.getStackTrace();
		//	System.out.println("SQLException: " + sqex.getMessage());
			//System.out.println("SQLState: " + sqex.getSQLState());
			
		}
		System.out.println("checkBoard postID : " + postID + " , " + check );
		return check;
	}
	

	// Board 테이블에 게시글 하나 삽입
	public void insertBoard(InstaInfo info, int idkeyword) {
		try {
			String sql = "insert into board values('" + info.getPostID() + "','" +  info.getStringID() + "','" + info.getNumberID()
					+ "','" + info.getImagesURL() + "','" + info.getCreateTime() + "','"
					+ info.getContent() + "','" + info.getLikeCount() + "','" + info.getReplyCount() + "','" + idkeyword + "','" + 0 + "')";
			
			st.executeUpdate(sql);
		} catch (Exception sqex) {
			
			sqex.getStackTrace();
		//	System.out.println("SQLException: " + sqex.getMessage());
			//System.out.println("SQLState: " + sqex.getSQLState());
			
		}
	}

	// BoardTag table의 (postID, 태그, 한게시글당 댓글 빈도수) 삽입
	public void insertBoardTag(String postID, String tag, int count) {
		try {
			String sql = "insert into boardtag values('" +  tag + "','" + count +  "','" + postID + "')";
			st.executeUpdate(sql);
		} catch (Exception sqex) {
		//	System.out.println("SQLException: " + sqex.getMessage());
			//System.out.println("SQLState: " + sqex.getSQLState());
			sqex.getStackTrace();
		}
	}

	// Tag table의 (태그, 총 게시글의 태그 빈도수)
	public void insertTag(String tag, int count, int idkeyword) {
		try {
			String sql = "insert into tag values('" + tag + "','" + count + "','" + idkeyword  + "')";
			
			st.executeUpdate(sql);
		} catch (Exception e) {
		//	System.out.println("SQLException: " + sqex.getMessage());
		//	System.out.println("SQLState: " + sqex.getSQLState());
			e.getStackTrace();
			
		}
	}

	// BoardTag table의 count Update
	public void updateBoardTag(String postID, String tag, int count) {
		try {
			String sql = "update boardtag set count = '" + count + "' where board_postID = '" + postID
					+ "' and tag = '" + tag + "'";
			st.executeUpdate(sql);
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}
	}

	// Tag table의 count Update
	public void updateTag(String tag, int count, int idKeyword) {
		try {
			String sql = "update Tag set count = '" + count + "' where tag = '" + tag + "' and keyword_idkeyword = '" + idKeyword +"'";
			st.executeUpdate(sql);
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}
	}

	// Tag 테이블의 tag를 삭제
	public void deleteTag(String tableName, String tag) {
		try {
			String sql = "delete from Tag where tag like '%" + tag + "%' ";
			st.executeUpdate(sql);
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}
	}

	// BoardTag 테이블의 tag를 삭제
	public void deleteBoardTag(String tableName, String tag) {
		try {
			String sql = "delete from BoardTag where tag like '%" + tag + "% '";
			st.executeUpdate(sql);
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}
	}
	//close DB
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
	
	public int checkKeyWord(int year, String season, String keyWord)
	{
		int id = -1;
		try {
			String sql = "select idkeyword from keyword where year = '" + year + "' and season = '" 
		+ season + "' and word = '" + keyWord +"'";
			rs = st.executeQuery(sql);
			if(rs.next())
			{
				id = rs.getInt("idkeyword");
			}
				
		} catch (SQLException sqex) {
			// TODO Auto-generated catch block
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}
		System.out.println("[checkKeyWord] keyWord : " + keyWord + " idkeyWord : " + id);
		return id;
	}
	
	public void insertKeyWord(int year, String season, String keyWord)
	{
		try {
			String sql = "insert into keyword(year, season, word) values('" + year + "','" + season + "','" + keyWord + "')";
			st.executeUpdate(sql);
		} catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}
	}
	public int searchEarlyCreatedTime(int idkeyWord)
	{
		int createdTime = -1;
		
		try {
			
			String sql = "select max(createdTime) from board where keyword_idkeyword = '" + idkeyWord +"'";
			rs = st.executeQuery(sql);
			
			while(rs.next())
			{
				createdTime = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.getStackTrace();
		}
		System.out.println("[searchEarlyCreatedTime] createdTime : " + createdTime + " idkeyWord : " + idkeyWord);
		return createdTime;
		
	}
}
