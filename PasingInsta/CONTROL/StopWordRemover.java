package CONTROL;

import java.util.ArrayList;
import java.util.List;

import DATA.*;

public class StopWordRemover {

	InstaInfoDB instaDB;
	
	public StopWordRemover(String dbName)
	{
		instaDB = new InstaInfoDB(dbName);
	}
	
	public void removeStopWord(String tableName)
	{
		try
		{
			List<String> stopWordList = new ArrayList<String>();
			//Insta용어
			stopWordList.add("그램");
			stopWordList.add("인스타");
			stopWordList.add("insta");
			stopWordList.add("gram");
			stopWordList.add("맞팔");
			stopWordList.add("선팔");
			stopWordList.add("소통");
			stopWordList.add("대세");
			stopWordList.add("팔로");
			stopWordList.add("셀카");
			stopWordList.add("페친");
			stopWordList.add("인친");
			stopWordList.add("인증");
			stopWordList.add("셀기꾼");
			stopWordList.add("착샷");
			stopWordList.add("찍샷");
			stopWordList.add("이웃");
			stopWordList.add("태그");
			stopWordList.add("셀피");
			stopWordList.add("selie");
			stopWordList.add("좋아요");
			//SNS
			stopWordList.add("네이버");
			stopWordList.add("카스");
			stopWordList.add("블로그");
			stopWordList.add("sns");
			//비속어
			stopWordList.add("씨발");
			stopWordList.add("섹스");
			//요일
			stopWordList.add("월요일");
			stopWordList.add("화요일");
			stopWordList.add("수요일");
			stopWordList.add("목요일");
			stopWordList.add("금요일");
			stopWordList.add("토요일");
			stopWordList.add("일요일");
			stopWordList.add("휴일");
			//지역
			stopWordList.add("압구정");
			stopWordList.add("경성대");
			stopWordList.add("싱가폴");
			stopWordList.add("명동");
			stopWordList.add("서울");
			stopWordList.add("대학교");
			stopWordList.add("대구");
			stopWordList.add("동성로");
			stopWordList.add("미술관");
			stopWordList.add("부산");
			stopWordList.add("남포동");
			stopWordList.add("홍대");
			stopWordList.add("시내");
			stopWordList.add("부산대");
			stopWordList.add("울산");
			stopWordList.add("광주");
			stopWordList.add("포항");
			stopWordList.add("대전");
			stopWordList.add("창원");
			stopWordList.add("봉선동");
			stopWordList.add("가양동");
			stopWordList.add("상남동");
			//기타
			stopWordList.add("맨홀");
			stopWordList.add("ㅋ");
			stopWordList.add("네일");
			stopWordList.add("nail");
			
			for(String stopWord : stopWordList)
			{
				instaDB.deleteTag(tableName, stopWord);
				instaDB.deleteBoardTag(tableName, stopWord);
			}
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}
	
	public void closeInstaInfoDB()
	{
		instaDB.closeDB();
	}
}
