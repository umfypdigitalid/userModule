<?php
	require_once "dbconnection.php";
	$username=$_GET['username'];
	$sql="select userid from users where username='$username'";
	//$userid=$conn->query($sql);
	if($result=$conn->query($sql)){
	while($row = $result -> fetch_assoc()){
		printf("%s" , $row["userid"]);
}
	$result-> free_result();
}
?>