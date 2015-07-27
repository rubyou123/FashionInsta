package CONTROL;


import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.json.simple.*;

import DATA.*;

public class InstaPaser {

	ArrayList<InstaInfo> instaList = new ArrayList<InstaInfo>();
	ArrayList<String> searchWordList = new ArrayList<String>();
	InstaSaver insaver;
	
	public void MainPasing(String dbName)
	{
		searchWordList.add("놈코어룩");
//		searchWordList.add("시스루");
		insaver = new InstaSaver(dbName);
		StopWordRemover stwdremv = new StopWordRemover(dbName);
		try
		{
			for(String key : searchWordList)
			{
				getInstaInformation(key);
				insaver.saveInsta(instaList, key);
				stwdremv.removeStopWord(key);
				
				instaList.clear();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void getInstaInformation(String searchWord) throws IOException 
	{
		URL requestURL; // access_token을 가진 처음 요청 URL
		String nextURL = ""; // api 검색후 api에서 제공하는 다음 URL
		HttpURLConnection httpCon; 
		String startURL = null;
		System.out.println(searchWord + " 수집중...");
		int boardCount = 0;
		
		//갯수 정하는 부분
			for (int i = 0; i<20000; i++) {
				
				//너무 빠르면 안되니 0.5초의 sleep
				try {
					Thread.sleep(400);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				//end of sleep
		
				
				Date date = new Date(); // 가져온 날짜 확인하기 위해서
			    System.out.println("----- "+searchWord  + i +" -----" + date.toString());
			    
			    
				if (i == 0) { // 첫번째면 URL 생성
					startURL = "https://api.instagram.com/v1/tags/"+ URLEncoder.encode(searchWord, "UTF-8")
							    + "/media/recent?access_token=1583007482.1fb234f.395c91113a6d43be81db7a7d25a5b02e";
					requestURL = new URL(startURL);
					System.out.println(startURL);
					
				} 
				else // 첫번째가 아니라면 requsetURL을 api에서 나오는 NextURL로 변경 
				{ 
					System.out.println(nextURL);
					requestURL = new URL(nextURL);
				}
				
				httpCon = (HttpURLConnection) requestURL.openConnection(); // URL 연결
				Object instaObject = JSONValue.parse(new InputStreamReader(httpCon.getInputStream()));
				JSONObject jInstaObject = (JSONObject) instaObject;
				
				// json으로 데이터를 받아옴
				JSONArray datas = (JSONArray) jInstaObject.get("data"); // data로 묶여진(게시글) 것들을 가지고 옴 (20개의 게시글 list)
				Iterator<JSONObject> datas_iterator = datas.iterator();

				// next_url이 있는 부분 pagination
				JSONObject next_url_str = (JSONObject) jInstaObject.get("pagination");
				nextURL = (String) next_url_str.get("next_url"); // 다음 url을 추출
			
					
				// 하나의 게시글 (최대 20개)
				while (datas_iterator.hasNext()) {
					InstaInfo instainfo = new InstaInfo();
					List<String> tempTags = new ArrayList<String>();
					boardCount++;
					JSONObject index = datas_iterator.next();

					// 태그 가져오기
					// 태그의 갯수만큼 for문 동작
					JSONArray tags = (JSONArray) index.get("tags"); // 한개의 글 안에 속한 태그들을 다 들고옴
					for (int j = 0; j < tags.size(); j++) {
						tempTags.add((String) tags.get(j));
					}
					// 다가져온후 InstaInfo에 저장
					instainfo.setTags(tempTags);

					// 게시글 고유번호 가져오기
					String postID = (String) index.get("id");
					instainfo.setPostID(postID);
					
					// 작성날짜가져오기
					// String으로 가져와서 Int로 저장u
					String stringTime = (String) index.get("created_time");
					instainfo.setCreateTime(Integer.parseInt(stringTime));
					
					//null 확인해주기 
					// 본문 내용 가져오기
					JSONObject caption = (JSONObject) index.get("caption");
					try{
						instainfo.setContent((String) caption.get("text"));
					}catch(NullPointerException e)
					{
						System.out.println("Text Null");
						instainfo.setContent("");
					}
					
					
					// image추출 0 - 150x150(thumbnail) / 1 -
					// 306x306(low_resolution) / 2 -
					// 640x640(standard_resolution)
					
					JSONObject images = (JSONObject) index.get("images");
					JSONObject standard_resolution = (JSONObject) images.get("standard_resolution");

					instainfo.setImagesURL((String) standard_resolution.get("url"));

					// 댓글 갯수 가져오기
					JSONObject reply = (JSONObject) index.get("comments");
					instainfo.setReplyCount(Integer.parseInt(reply.get("count").toString()));

					// 좋아요 갯수 가져오기
					JSONObject like = (JSONObject) index.get("likes");
					instainfo.setLikeCount(Integer.parseInt(like.get("count").toString()));

					// 사용자 정보가져오기
					JSONObject user = (JSONObject) index.get("user");
					instainfo.setStringID((String) user.get("username"));
					instainfo.setNumberID((String) user.get("id"));

					//print
					instaList.add(instainfo);

				} // end of while(게시글 목록)

				if (datas.size() < 20 || nextURL == null) { // data 크기가 20개가 안되면 -> 마지막 검색 결과
					System.out.println("종료");
					break;
				}
				
		   } // end of for(인스타 몇번 반복 할지)
			// return instaInfoList;

			System.out.println("총 게시글 수 : " + boardCount);
	}
	
	// Map 정렬
}
