<?php
@header("Content-Type: text/html; charset=UTF-8");

	$db_hostname = "127.0.0.1";
	$db_username = "root";
	$db_password = "apmsetup";
	$db_database = "instagram";
	$db_server = mysql_connect($db_hostname, $db_username, $db_password);

	$id = $_GET["id"];

	//$id = URLDecoder.decode(id, "UTF-8");

	if(!$db_server) die ("Unable to connect to MySQL :" . mysql_error());

	mysql_select_db($db_database) or die("Unable to select Database : " . mysql_error());

	mysql_query("set names utf8;");

	mysql_query("set session character_set_connection=utf8;");
	mysql_query("set session character_set_results=utf8;");
	mysql_query("set session character_set_client=utf8;");

	$query = "select postID,imageURL,stringID,createdTime from board where weight > 0 and keyword_idkeyword = " .$id. " order by weight limit 0,30" ; 

	$result = mysql_query($query) ; 
	$total_record = mysql_num_rows($result);
	 // JSONArray �������� ����� ���ؼ�...

   	echo "{\"urls\":[ ";
	  // ��ȯ�� �� ���ڵ庰�� JSONArray �������� �����.

   	for ($i=0; $i < $total_record; $i++)                    
   	{
     	// ������ ���ڵ�� ��ġ(������) �̵�  
      		mysql_data_seek($result, $i);       
        
      		$row = mysql_fetch_array($result);
		$Time = date('M j, Y', $row[createdTime]);

   		echo "{\"postID\": \"$row[postID]\", \"imageURL\": \"$row[imageURL]\" , \"stringID\": \"$row[stringID]\" , \"createdTime\": \"$Time\"}";

   // ������ ���ڵ� ������ ,�� ���δ�. �׷��� ������ ������ �Ǵϱ�.  
   		if($i<$total_record-1){
      			echo ",";
   		}
    
   	}

   // JSONArray�� ������ �ݱ�

   	echo "]}";

?>
