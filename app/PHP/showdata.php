<?php
require "DataBase.php";
//include('DataBase.php');

$stmt = $connect->prepare("SELECT fullName, ic, email, address FROM userdata");

$stmt ->execute();
$stmt -> bind_result($fullName, $ic, $email, $address);

$personaldata = array();

while($stmt ->fetch()){

    $temp = array();

    $temp['fullName'] = $fullName;
    $temp['ic'] = $ic;
    $temp['email'] = $email;
    $temp['address'] = $address;

    array_push($personaldata,$temp);
}

echo json_encode($personaldata);

?>

<?php
require "DataBase.php";
$db = new DataBase();
$con=$db->dbConnect();
$query=mysqli_query($con, "SELECT fullName, ic, email, address FROM userdata where username='leqing0915'");

$result = $query;
$row = mysqli_fetch_assoc($result);
printf("%s (%s)\n", $row["fullName"]);

mysqli_free_result($result);
//{
//while($row=mysqli_fetch_assoc($query))
//	{
//	$flag=$row;
//	}
//print(json_encode($flag));
//}
mysqli_close($con);
?>