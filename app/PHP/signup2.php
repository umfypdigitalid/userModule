<?php
if (isset($_POST['fullname']) && isset($_POST['email']) && isset($_POST['username']) && isset($_POST['password'])&& isset($_POST['ic'])&& isset($_POST['birthdate'])&& isset($_POST['address'])) {
	require_once "dbconnection.php";
	//require_once "validate.php";
	//$fullname = validate($_POST['fullname']);
	$fullname = $_POST['fullname'];
	$email = $_POST['email'];
	$username = $_POST['username'];
	$password = password_hash($_POST['password'], PASSWORD_DEFAULT);
	$ic = $_POST['ic'];
	$birthdate = $_POST['birthdate'];
	$address = $_POST['address'];
	//$icimage = $_POST['icimage'];
	$sql = "insert into users (fullname, username, password, ic, birthdate, email, address) VALUES ('" . $fullname . "','" . $username . "','" . $password . "','" . $ic . "','" . $birthdate . "','" . $email . "','" . $address . "')";
	if(!$conn->query($sql)){
		echo "Sign Up Failed";
	}else echo "Sign Up Success";
}else echo "All fields are required";
?>