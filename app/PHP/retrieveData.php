<?php
require "DataBase.php";
$db = new DataBase();
$con=$db->dbConnect();

//$sql = "SELECT fullName FROM userdata where username='$username'";

$query = mysqli_query($con, "SELECT fullName, ic, birthDate, email, address, userstatus FROM userdata where username='".$_GET['username']."'");
if($result=$query){
	while($row = $result -> fetch_row()){
		printf("%s !%s !%s !%s !%s !%s" , $row[0], $row[1], $row[2], $row[3],  $row[4], $row[5]);
	}
	$result-> free_result();
}
mysqli_close($con);
?>