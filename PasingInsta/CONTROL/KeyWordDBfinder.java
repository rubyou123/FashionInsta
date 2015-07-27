package CONTROL;

import DATA.InstaInfoDB;

public class KeyWordDBfinder {
	
	InstaInfoDB instaDB;
	
	public KeyWordDBfinder(String dbName)
	{
		instaDB = new InstaInfoDB(dbName);
	}
	
	public int getCreatedTime(int year, String season, String keyWord)
	{
		boolean check = true;
	    check = instaDB.checkKeyWord(year, season, keyWord);
		if(check == false)
		{
			instaDB.insertKeyWord(year, season, keyWord);
			System.out.println("======== 최근 마지막 게시글 : 없음 ");
			return -1;
		}
		else
		{
			int createdTime =  instaDB.searchEarlyCreatedTime(keyWord);
			System.out.println("======== 최근 마지막 게시글 : " + createdTime);
			return createdTime;
		}
	}
	
	public void closeDB()
	{
		instaDB.closeDB();
	}

}
