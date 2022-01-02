<?php
if (isset($_POST['fullname']) && isset($_POST['username']) && isset($_POST['password'])&& isset($_POST['ic'])&& isset($_POST['email'])) {
	require_once "dbconnection.php";
	$fullname = $_POST['fullname'];
	$username = $_POST['username'];
	$password = password_hash($_POST['password'], PASSWORD_DEFAULT);
	$ic = $_POST['ic'];
	$email = $_POST['email'];
	$sql = "select * from userdata where username ='$username' or ic='$ic'";
    $sql2 = "insert into userdata (fullname, username, password, ic, email) VALUES ('" . $fullname . "','" . $username . "','" . $password . "','" . $ic . "','" . $email . "')";
    $result = $conn->query($sql);
    if($result->num_rows>0){
        echo "Username or IC already exists.";
    }else {
        if (!$conn->query($sql2)) {
            echo "Sign Up Failed";
        } else echo "Sign Up Success";
    }
}else echo "All fields are required";
?>