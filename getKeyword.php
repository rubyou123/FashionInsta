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
	 // JSONArray �������� ����� ���ؼ�...

   	echo "{\"result\":[ ";
	  // ��ȯ�� �� ���ڵ庰�� JSONArray �������� �����.

   	for ($i=0; $i < $total_record; $i++)                    
   	{
     	// ������ ���ڵ�� ��ġ(������) �̵�  
      		mysql_data_seek($result, $i);       
        
      		$row = mysql_fetch_array($result);

   		echo "{\"idkeyword\": \"$row[idkeyword]\", \"word\": \"$row[word]\"}";

 
   // ������ ���ڵ� ������ ,�� ���δ�. �׷��� ������ ������ �Ǵϱ�.  
   		if($i<$total_record-1){
      			echo ",";
   		}
    
   	}

   // JSONArray�� ������ �ݱ�

   	echo "]}";

?>
