package DATA;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InternalDB {

	public void insert(ArrayList<Word> BoardList){
		
		Connection _con = null;
		java.sql.Statement _stmt = null;
		ResultSet _rs = null;
		
		System.out.println("Please, Interanl DB Connection!");
		try {
			InternalDBConn conn = new InternalDBConn();
			_con = conn.getConnection();

			if (_con != null) {
				System.out.println("Internal DB, Connection Success!");
			} else {
				System.out.println("Internal DB, Connection Fail!");
			}
			
			_stmt = _con.createStatement();
			for(int i=0; i<BoardList.size(); i++){

				String sql = "insert into kmeansinputdata VALUES ('" + i + "', '" + BoardList.get(i).getWord() + "', '"
						+ Integer.parseInt(BoardList.get(i).getCategoryList()[0]) + "', '" + Integer.parseInt(BoardList.get(i).getCategoryList()[1]) + "', '" + Integer.parseInt(BoardList.get(i).getCategoryList()[2]) + "', '" +
						  Integer.parseInt(BoardList.get(i).getCategoryList()[3]) + "', '" + Integer.parseInt(BoardList.get(i).getCategoryList()[4]) + "', '" + Integer.parseInt(BoardList.get(i).getCategoryList()[5]) + "', '" +
						  Integer.parseInt(BoardList.get(i).getCategoryList()[6]) + "', '" + Integer.parseInt(BoardList.get(i).getCategoryList()[7]) + "', '" + Integer.parseInt(BoardList.get(i).getCategoryList()[8]) + "', '" +
						  Integer.parseInt(BoardList.get(i).getCategoryList()[9]) + "', '" + Integer.parseInt(BoardList.get(i).getCategoryList()[10]) + "')";
			
				_stmt.executeUpdate(sql);			
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		System.out.println("Internal DB Conn, Exit");
		InternalDBConn.close(_rs, _stmt, _con);
	}
	
	public void insertionSuin()
	{
		Connection _con = null;
		java.sql.Statement _stmt = null;
		ResultSet _rs = null;
		
		try
		{			
			InternalDBConn conn = new InternalDBConn();
			_con = conn.getConnection();

			if (_con != null) {
				System.out.println("Internal DB, Connection Success!");
			} else {
				System.out.println("Internal DB, Connection Fail!");
			}
			_stmt = _con.createStatement();
			
			String sql = "select * from kmeansinputdata";
			_rs = _stmt.executeQuery(sql);

			while(_rs.next())
			{
				double a = 0;
				Word b = new Word();
			//	String category[]
				int index = _rs.getInt("identifier");
				b.setWord(_rs.getString("hashTag"));
				int sum = 0;
			//	double list[] = new double[11];
				for(int j =0; j<11; j++)
				{
					String s = "category" + Integer.toString(j);
					int n = _rs.getInt(s);
					
					s = Integer.toString(n);
					b.setCategoryList(s, j);
					sum += n;
				}
				for(int j = 0; j<11; j++)
				{
					a = Integer.parseInt(b.getCategoryList()[j])/(double)sum;
					String str  = String.format("%.4f", a);
					b.setCategoryList(str, j);
				}
				sum = 0;
				
				sql = "insert into suin VALUES ('" + index + "', '" + b.getWord() + "', '"
						+ Double.valueOf(b.getCategoryList()[0]).doubleValue() + "', '" + Double.valueOf(b.getCategoryList()[1]).doubleValue() + "', '" + Double.valueOf(b.getCategoryList()[2]).doubleValue() + "', '" +
						  Double.valueOf(b.getCategoryList()[3]).doubleValue() + "', '" + Double.valueOf(b.getCategoryList()[4]).doubleValue() + "', '" + Double.valueOf(b.getCategoryList()[5]).doubleValue() + "', '" +
						  Double.valueOf(b.getCategoryList()[6]).doubleValue() + "', '" + Double.valueOf(b.getCategoryList()[7]).doubleValue() + "', '" + Double.valueOf(b.getCategoryList()[8]).doubleValue() + "', '" +
						  Double.valueOf(b.getCategoryList()[9]).doubleValue() + "', '" + Double.valueOf(b.getCategoryList()[10]).doubleValue() + "')";
			
				System.out.println(sql);
				
				_stmt.executeUpdate(sql);

			}
			
			
		}
		catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		System.out.println("Internal DB Conn, Exit");
		InternalDBConn.close(_rs, _stmt, _con);
		
	}	
}
