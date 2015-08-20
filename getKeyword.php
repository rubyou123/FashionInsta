<?php
@header("Content-Type: text/html; charset=UTF-8");

	$db_hostname = "127.0.0.1";
	$db_username = "root";
	$db_password = "apmsetup";
	$db_database = "instagram";
	$db_server = mysql_connect($db_hostname, $db_username, $db_password);

	if(!$db_server) die ("Unable to connect to MySQL :" . mysql_error());

	mysql_select_db($db_database) or die("Unable to select Database : " . mysql_error());

	mysql_query("set names utf8;");

	mysql_query("set session character_set_connection=utf8;");
	mysql_query("set session character_set_results=utf8;");
	mysql_query("set session character_set_client=utf8;");

	$query = "select idkeyword, word from keyword" ; 

	$result = mysql_query($query) ; 
	$total_record = mysql_num_rows($result);
	 // JSONArray 형식으로 만들기 위해서...

   	echo "{\"result\":[ ";
	  // 반환된 각 레코드별로 JSONArray 형식으로 만들기.

   	for ($i=0; $i < $total_record; $i++)                    
   	{
     	// 가져올 레코드로 위치(포인터) 이동  
      		mysql_data_seek($result, $i);       
        
      		$row = mysql_fetch_array($result);

   		echo "{\"idkeyword\": \"$row[idkeyword]\", \"word\": \"$row[word]\"}";

 
   // 마지막 레코드 이전엔 ,를 붙인다. 그래야 데이터 구분이 되니깐.  
   		if($i<$total_record-1){
      			echo ",";
   		}
    
   	}

   // JSONArray의 마지막 닫기

   	echo "]}";

?>
