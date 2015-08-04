package DATA;

import java.util.ArrayList;

public class Word { 

	/*
	0 - 패션잡화
	1 - 스포츠/레저
	2 - 디지털/가전
	3 - 출산/육아
	4 - 여행/문화
	5 - 패션의류
	6 - 가구/인테리어
	7 - 화장품/미용
	8 - 생활/건강
	9 - 식품
	10- 면세점
	*/
	
	private String categoryList[] = new String[11];
	private String word;   
	
	public Word(){
		word = "";
		for(int i=0; i<11; i++){
			categoryList[i] = "0";
		}
	}
	
	public String getWord() { return word; }
	public String[] getCategoryList() { return categoryList; }
	
	public void setWord(String word) { this.word = word; }
	public void setCategoryList(String categoryNum, int index) {
		this.categoryList[index] = categoryNum ;
	}
	
}
