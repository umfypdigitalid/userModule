<?php
require_once "dbconnection.php";

$username=$_POST['username'];
$icimage=$_POST['icimage'];

$filename="IMG".rand().".jpg";
file_put_contents("images/".$filename,base64_decode($icimage));
$sql = "update users SET icimage='$filename', userstatus='verified' where username = '$username'";
if(!$conn->query($sql)){
		echo "Failed";
	} else{
		echo "Success";
	}
?>