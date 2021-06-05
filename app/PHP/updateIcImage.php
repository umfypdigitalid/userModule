<?php
if (isset($_POST['icimage'])&&isset($_POST['username'])) {
	require_once "dbconnection.php";
	$icimage = $_POST['icimage'];
	//$icimage =base64_encode($_POST['icimage'])
	$username = $_POST['username'];
	$userstatus = $_POST['userstatus'];
	$sql = "UPDATE users SET icimage = 'icimage', userstatus = '$userstatus' WHERE username = '$username'";
	$result = mysqli_query($conn,$sql);
	if($result){
		echo "Success";
	} else{
		echo "Failed";
	}
}else echo "Missing icimage or username";
?>