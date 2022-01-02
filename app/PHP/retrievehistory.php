
<?php
	require_once "dbconnection.php";
	$username=$_GET['username'];
	$stmt=$conn->prepare("select name, scannedon from loginqrhistory where username='$username'");
	$stmt->execute();
	$stmt->bind_result($name,$scannedon);
	$history = array();
	while($stmt->fetch()){
		$temp=array();
		$temp['name']=$name;
		$temp['scannedon']=$scannedon;
		array_push($history,$temp);
	}
	echo json_encode($history);
?>