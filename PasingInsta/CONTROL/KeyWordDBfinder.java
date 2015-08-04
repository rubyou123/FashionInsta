package CONTROL;

import DATA.InstaInfoDB;

public class KeyWordDBfinder {
	
	InstaInfoDB instaDB;
	
	public KeyWordDBfinder(String dbName)
	{
		instaDB = new InstaInfoDB(dbName);
	}
	
	public int getCreatedTime(int year, String season, String keyWord, int idKeyword)
	{
		int createdTime = -1;
		createdTime =  instaDB.searchEarlyCreatedTime(idKeyword);
		if(createdTime == -1)
		{
		//	instaDB.insertKeyWord(year, season, keyWord);
			System.out.println("======== 최근 마지막 게시글 : 없음 ");
		}
		else
		{
		//	createdTime =  instaDB.searchEarlyCreatedTime(keyWord, check);
			System.out.println("======== 최근 마지막 게시글 : " + createdTime);

		}
		return createdTime;
	}
	
	public int getIDKeyword(int year, String season, String keyword)
	{
		int check = instaDB.checkKeyWord(year, season, keyword);
		
		if(check == -1)
		{
			instaDB.insertKeyWord(year, season, keyword);
			check = instaDB.checkKeyWord(year, season, keyword);
		}
		
		return check;
	}
	
	
	public void closeDB()
	{
		instaDB.closeDB();
	}

}
