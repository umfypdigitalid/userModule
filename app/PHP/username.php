<?php
	require_once "dbconnection.php";
	$email=$_GET['email'];
	$sql="select username from userdata where email='$email'";
	//$userid=$conn->query($sql);
	if($result=$conn->query($sql)){
	while($row = $result -> fetch_assoc()){
		printf("%s" , $row["username"]);
}
	$result-> free_result();
}
?>