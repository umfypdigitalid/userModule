<?php
require "DataBase.php";
$db = new DataBase();
$con=$db->dbConnect();

$sql = "SELECT * FROM userdata where username='".$_GET['username']."'";
if(!$con->query($sql)){
	echo "Error in connecting to Database.";
}
else{
	$result = $con->query($sql);
	if($result->num_rows>0){
		$return_arr['personaldata'] = array();
		while($row = $result->fetch_array()){
			array_push($return_arr['personaldata'], array(
				'fullName'=>$row['fullName'],
				'ic'=>$row['ic'],
				'birthDate'=>$row['birthDate'],
				'email'=>$row['email'],
				'address'=>$row['address']
			));
		}
		echo json_encode($return_arr);
	}
}

?>