<?php
if (isset($_POST['username']) && isset($_POST['password'])) {
	require_once "dbconnection.php";
	require_once "validate.php";
	$username = validate($_POST['username']);
	$password = validate($_POST['password']);
	$sql ="select * from users where username='$username'";
	$result = mysqli_query($conn,$sql);
	$row = mysqli_fetch_assoc($result);
	if($result->num_rows>0){
		$dbusername = $row['username'];
		$dbpassword = $row['password'];
		if ($dbusername == $username && password_verify($password, $dbpassword)) {
			echo "Log in Success";
		}
	} else {
		echo "Log in Failed";
	}
} else echo "All fields are required";
?>