<?php
if(isset($_POST['userid'])&&isset($_POST['name'])&&isset($_POST['detail'])){
	require_once "dbconnection.php";
	$userid=$_POST['userid'];
	$name=$_POST['name'];
	$detail=$_POST['detail'];
	$insert= "insert into qrhistory (name,detail,userid) values ( '" . $name . "','" . $detail . "','$userid')";
	if($conn->query($insert)){
		echo "Success";
	}else{
		echo "Failed";
	}
}else echo "Missing required fields";
?>