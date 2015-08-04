package Parsing;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import DATA.*;

public class ParsingCategoryOfNaverShoping {
	private Word hashTagOfBoard;
	
	public Word connection(String address, String wordName) {
		Document doc;
		Elements elems;
		String category = "";
		
		try {
			doc = Jsoup.connect(address).get();
			elems = doc.select(".finder_col");
			if(elems.isEmpty() || elems.equals("") || elems.get(0).text().equals("")) return null;
			category = elems.get(0).text();
			
			String removeComma = category.replace(",", "");
			category = removeComma;
			//System.out.println(category);
			String[] splitCategory = category.split(" ");
			
			hashTagOfBoard = new Word();
			hashTagOfBoard.setWord(wordName);
			
		
			
			for(int i=0; i< splitCategory.length; i++){
				
				if(splitCategory[i].indexOf("패션잡화") != -1){
					//System.out.println(splitCategory[i].substring(4));
					hashTagOfBoard.setCategoryList(splitCategory[i].substring(4), 0);
				}
				else if(splitCategory[i].indexOf("스포츠/레저") != -1){
					//System.out.println(splitCategory[i].substring(6));
					hashTagOfBoard.setCategoryList(splitCategory[i].substring(6), 1);
				}	
				else if(splitCategory[i].indexOf("디지털/가전") != -1){
					//System.out.println(splitCategory[i].substring(6));
					hashTagOfBoard.setCategoryList(splitCategory[i].substring(6), 2);
				}					
				else if(splitCategory[i].indexOf("출산/육아") != -1){
					//System.out.println(splitCategory[i].substring(5));
					hashTagOfBoard.setCategoryList(splitCategory[i].substring(5), 3);
				}
				else if(splitCategory[i].indexOf("여행/문화") != -1){
					//System.out.println(splitCategory[i].substring(5));
					hashTagOfBoard.setCategoryList(splitCategory[i].substring(5), 4);
				}
				else if(splitCategory[i].indexOf("패션의류") != -1){
					//System.out.println(splitCategory[i].substring(4));
					hashTagOfBoard.setCategoryList(splitCategory[i].substring(4), 5);
				}
				else if(splitCategory[i].indexOf("가구/인테리어") != -1){
					//System.out.println(splitCategory[i].substring(7));
					hashTagOfBoard.setCategoryList(splitCategory[i].substring(7), 6);
				}
				else if(splitCategory[i].indexOf("화장품/미용") != -1){
					//System.out.println(splitCategory[i].substring(6));
					hashTagOfBoard.setCategoryList(splitCategory[i].substring(6), 7);
				}
				else if(splitCategory[i].indexOf("생활/건강") != -1){
					//System.out.println(splitCategory[i].substring(5));
					hashTagOfBoard.setCategoryList(splitCategory[i].substring(5), 8);
				}
				else if((splitCategory[i].indexOf("식품") != -1)){
					if((47<splitCategory[i].charAt(2)) && (splitCategory[i].charAt(2)<58)){
						//System.out.println(splitCategory[i].substring(2));
						hashTagOfBoard.setCategoryList(splitCategory[i].substring(2), 9);
					}
				}
				else if(splitCategory[i].indexOf("면세점") != -1){
					//System.out.println(splitCategory[i].substring(3));
					hashTagOfBoard.setCategoryList(splitCategory[i].substring(3), 10);
				}
					
			}
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		
//		System.out.print( wordName + "  ");
//		for(int i=0; i< 11; i++){
//			System.out.print("["+i + "," + hashTagOfBoard.getCategoryList()[i]+"], ");
//		}
//		System.out.println();
		
		setNormalization(hashTagOfBoard);
		
		System.out.print( wordName + "  ");
		for(int i=0; i< 11; i++){
			System.out.print("["+i + "," + hashTagOfBoard.getCategoryList()[i]+"], ");
		}
		System.out.println();
		
		return hashTagOfBoard;
	}
	
	
	public void setNormalization(Word b){ //백분위 작업
		
		int sum = 0;
		double boardNomalization = 0;
		
		for(int j =0; j<11; j++){
			try{
			sum += Integer.parseInt(b.getCategoryList()[j]);
			}catch(NumberFormatException ex){
				System.err.println("Ilegal input");
			}
		}
		for(int j = 0; j<11; j++)
		{
			if(!b.getCategoryList()[j].equals("0")){
			//	boardNomalization = Integer.parseInt(b.getCategoryList()[j])/(double)sum;
				boardNomalization = Double.valueOf(b.getCategoryList()[j])/(double)sum;
				String str  = String.format("%.4f", boardNomalization);
				b.setCategoryList(str, j);
			}
			else
				b.setCategoryList("0", j);
		}
		
	}
}
