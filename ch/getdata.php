<?php

    $server		= "localhost"; 
	$user		= "root"; 
	$password	= ""; 
	$database	= "tugas";
	
	$con = mysqli_connect($server, $user, $password, $database);
	if (mysqli_connect_errno()) {
		echo "Gagal terhubung MySQL: " . mysqli_connect_error();
    }
    
    $query = "SELECT * FROM tugas"; 
    $sql = mysqli_query($con, $query);
    $arraydata = array();
    while ($data = mysqli_fetch_array($sql)) {
        $arraydata[] = $data;
    }
 
    echo json_encode($arraydata);
 
?>
