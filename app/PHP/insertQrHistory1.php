<?php

	require_once "dbconnection.php";
	$userid=$_POST['userid'];
	$name=$_POST['name'];
	$detail=$_POST['detail'];
	$username=$_POST['username'];
	//$sql="select userid from userdata where username='$username'";
	//$userid=$conn->query($sql);
	$sql= "INSERT into loginqrhistory (name, detail, userid, username) VALUES ( '" . $name . "','" . $detail . "','" . $userid . "','" . $username . "' )";
	if($conn->query($sql)) {
		echo "Success";
	}else{
		echo "Failed";
	}

?>