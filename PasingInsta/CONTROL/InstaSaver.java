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
	public void saveInsta(ArrayList<InstaInfo> instaList, String tableName, int idkeyword)
	{
		InstaInfo insta;
		int count = -1;
		String postID = "";
		int tagCount = -1;
		for(int i=0; i<instaList.size() ;i++)
		{
			insta = instaList.get(i);
			postID = insta.getPostID();
			
			if(instaDB.checkBoard(idkeyword, postID) == -1)
				instaDB.insertBoard(insta, idkeyword);
			else
				continue;
			
			insertTagList(insta);
			
			for(String tag : BoardTagList.keySet())
			{
				count = BoardTagList.get(tag);
				instaDB.insertBoardTag(postID, tag, count);
			}
			BoardTagList.clear();	
		}
		
		for(String tag : TagList.keySet())
		{
			count = TagList.get(tag);
			tagCount = instaDB.checkTagCount(idkeyword, tag);
			
	//		System.out.println("[saveInsta] tag : " + tag +" count : " + count + " tagCount : " + tagCount);
			
			if(tagCount == -1)
			{
				instaDB.insertTag(tag, count, idkeyword);
			}
			else
			{
				count += tagCount;
				instaDB.updateTag(tag, count, idkeyword);
			}
		}
	//	writeTags(tableName);
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
