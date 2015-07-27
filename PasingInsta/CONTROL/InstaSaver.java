package CONTROL;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DATA.*;

public class InstaSaver {

	InstaInfoDB instaDB;
	Map<String, Integer> BoardTagList;
	Map<String, Integer> TagList;
	
	public InstaSaver(String dbName)
	{
		BoardTagList = new HashMap<String, Integer>();
		TagList = new HashMap<String, Integer>();
		instaDB = new InstaInfoDB(dbName);
	}
	
	//table을 만듬
	public void createTable(String tableName)
	{
		instaDB.createBoardTable(tableName);
		instaDB.createBoardTagTable(tableName);
		instaDB.createTagTable(tableName);
	}
	//tagList함수에 저장.
	public void insertTagList(InstaInfo insta)
	{
		List<String> tags = insta.getTags();
		int count = -1;
		
		for(String tag : tags)
		{
			if(BoardTagList.containsKey(tag))
			{
				count = BoardTagList.get(tag);
				count++;
				BoardTagList.replace(tag, count);
			}
			else
			{
				BoardTagList.put(tag, 1);
			}
			if(TagList.containsKey(tag))
			{
				count = TagList.get(tag);
				count++;
				TagList.replace(tag, count);
			}
			else
			{
				TagList.put(tag, 1);
			}
		}
	}

	//insta 정보를 저장
	public void saveInsta(ArrayList<InstaInfo> instaList, String tableName)
	{
		InstaInfo insta;
		int count = -1;
		String postID = "";
		
		for(int i=0; i<instaList.size() ;i++)
		{
			insta = instaList.get(i);
			postID = insta.getPostID();
			insertTagList(insta);
			
			instaDB.insertBoard(tableName, insta);
			
			for(String tag : BoardTagList.keySet())
			{
				count = BoardTagList.get(tag);
				instaDB.insertBoardTag(tableName, postID,tag, count);
			}
			BoardTagList.clear();	
		}
		for(String tag : TagList.keySet())
		{
			count = TagList.get(tag);
			instaDB.insertTag(tableName, tag, count);
		}
		writeTags(tableName);
		TagList.clear();
	}
	//tag를 .csv파일 저장.
	public void writeTags(String tableName)
	{
		String fileName = tableName + ".csv";

		try {
			BufferedWriter fw = new BufferedWriter(new FileWriter(fileName, true));
			int count = 0;
			String line = "";
			for(String key : TagList.keySet())
			{
				count = TagList.get(key);
				line = key + "," + count +"\n";
				fw.write(line);
			}
			
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void closeInstaInfoDB()
	{
		instaDB.closeDB();
	}
	
	
}
