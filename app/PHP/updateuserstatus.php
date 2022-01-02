<?php
	require_once "dbconnection.php";
	$username = $_GET['username'];
	$sql = "UPDATE userdata SET userstatus = 'VERIFIED' WHERE username = '$username'";
	$result = mysqli_query($conn,$sql);
	if($result){
		echo "Success";
	} else{
		echo "Failed";
	}
?>