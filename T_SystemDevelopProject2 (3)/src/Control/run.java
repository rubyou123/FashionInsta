package Control;

import java.sql.*;
import DATA.*;
import Parsing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Executors;

//java.net.sockettimeoutexception read timed out �빐寃고븷寃�
public class run {
	
	static HashMap<String,Integer> wordList;
	static HashMap<String,Double> keyWordList;
	static HashMap<String,Double> fashionPostList;
	static  HashMap<String,String> postIDList;
	static  ArrayList<Word> boardList;
	static  ParsingCategoryOfNaverShoping parsingCategory;
	static String url;
	static int totalFre;
	public static void main(String args[]) {
		//String arr[] = {"놈코어룩","시스루룩"};

	//	for(int i=0; i<3; i++)
	//	{
		
		wordList = new HashMap<String,Integer>(); //전체단어 
		 keyWordList = new HashMap<String,Double>(); // 패션 키워드에 대한 정보만 가져온다
		 postIDList = new HashMap<String,String>(); // 한 게시글에 대한 포스트와 해시테그 저장
		  fashionPostList = new HashMap<String,Double>(); // 한 게시글에 대한 포스트와 가중치 저장
		 boardList = new ArrayList<Word>();
		 parsingCategory = new ParsingCategoryOfNaverShoping();
		  url = "";
		  totalFre = 0;
		  int idkeyword = 5;
		  String keyword = "프린지룩";
		
		ExternalDB edb = new ExternalDB();
		wordList = edb.getWordList(idkeyword); // 전체 단어에 대한 빈도수를 가진 총단어  1. 놈코어 2. 시스루룩  3.어슬레져룩  9.올화이트룩
		
		setCategoryList(); //카테고리정보가져옴
		setKeywordList(keyword); //패션 키워드 정보
		
		postIDList = edb.getPostIdList();
		
		setFashionPost();
		
		edb.InsertFashionPost(fashionPostList, idkeyword);
	//	}
		
	}
	
	public static void setFashionPost(){

		Set<Entry<String, String>> set = postIDList.entrySet();
		Iterator<Entry<String,String>> iter = set.iterator();
	
		double frequencyNum = 0;
		int containNum = 0;

		while(iter.hasNext()){
			Map.Entry<String, String> e = (Map.Entry<String, String>)iter.next();
			
			String []hashTags = e.getValue().split("#");
	
			for(int i=0; i<hashTags.length; i++){
				if(keyWordList.containsKey(hashTags[i])){
					frequencyNum += keyWordList.get(hashTags[i]);
					containNum++;
					System.out.println("postID : " + e.getKey() + "  fashionKeyword : " + hashTags[i] );
				}
			}
			//가중치와 포스트아이디 넣는다
			//System.out.println(containNum + frequencyNum);
			double weighting = (hashTags.length/(double)containNum) * (frequencyNum * (totalFre /(double)frequencyNum*2));
			String str = String.format("%.4f", weighting);
			fashionPostList.put(e.getKey(), Double.valueOf(str));
			
			frequencyNum = 0;
			containNum = 0;
		}
		
	}
	
	public static void setKeywordList(String key){
		int size = 0;
		
		Set<Entry<String, Integer>> set = wordList.entrySet();
		Iterator<Entry<String,Integer>> iter = set.iterator();
		
		while(iter.hasNext()){
			Map.Entry<String, Integer> e = (Map.Entry<String, Integer>)iter.next();
			
			size += e.getValue();
		}
	
		totalFre = size;
		int index = 0;
		for(int i=0; i<boardList.size(); i++){
			if(boardList.get(i).getWord().equals(key)){
				index = i;
				break;
			}
		}
		//System.out.println("boardList에서 놈코어룩이 위치한 곳  : " + index);
		
		System.out.println(boardList.get(index).getCategoryList()[5]);
		
		for(int i=0; i < boardList.size(); i++){
			if(Double.valueOf(boardList.get(i).getCategoryList()[5]) >= Double.valueOf(boardList.get(index).getCategoryList()[5])){
				String keyword = boardList.get(i).getWord();
				System.out.println("핵심키워드 이상단어들 : " + keyword);
				if(size != 0){
					double value = wordList.get(keyword)/(double)size;
				
					String str = String.format("%.4f", value);
					keyWordList.put(keyword, Double.valueOf(str));
					
					System.out.println(keyword + "  : " + str );
				}
			}				
		}
	}
	
	public static boolean categoryListCheck(String categoryList[]){
		
		for(int i=0; i<11; i++){
			if(categoryList[i] != "0"){
				return true;
			}
		}	
		return false;
	}
	
	public static void setCategoryList(){
		System.out.println("setCategoryList() : init CategoryList...");
		
		//Set<String> key = wordList.keySet();
		Set<Entry<String, Integer>> set = wordList.entrySet();
		Iterator<Entry<String,Integer>> iter = set.iterator();
		int i=1;
		while(iter.hasNext()){
			Map.Entry<String, Integer> e = (Map.Entry<String, Integer>)iter.next();
			
			Word b = new Word();
			url = MakeNaverShoppingUrl.execute(e.getKey());
			b = parsingCategory.connection(url, e.getKey());
			if(i%20 == 0)
				System.out.println(i +"번재 진행 중");
			if(b != null)
				boardList.add(b);
			i++;
		}
		
		System.out.println("setCategoryList() : Complete!");
	}
	
}
