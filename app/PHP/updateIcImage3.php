<?php
if (isset($_POST['icimage'])) {
	require_once "dbconnection.php";
	$icimage = $_POST['icimage'];
	//$username = $_POST['username'];
	$sql = "UPDATE users SET icimage='$icimage' WHERE username = 'leqing99'";
	if(!$conn->query($sql)){
		echo "Failed";
	} else{
		echo "Success";
	}
//$conn->close();
}else echo "Missing icimage or username";
?>