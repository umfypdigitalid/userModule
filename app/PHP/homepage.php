<?php
require "DataBase.php";
$db = new DataBase();
$con=$db->dbConnect();

//$sql = "SELECT fullName FROM userdata where username='$username'";

$query = mysqli_query($con, "SELECT userstatus FROM userdata where username='".$_GET['username']."'");
if($result=$query){
	while($row = $result -> fetch_row()){
		printf("%s" , $row[0]);
	}
	$result-> free_result();
}
mysqli_close($con);
?>