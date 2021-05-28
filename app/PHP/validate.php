<?php
function validate($data){
	//remove unnecessary char such as extra space/tab
	$data = trim($data);
	//remove backslash
	$data = stripslashes($data);
	// convert special character to html entities
	//prevent attacker exploit code
	$data = htmlspecialchars($data);
	return $data;
}
?>