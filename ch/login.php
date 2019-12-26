<?php

	$server		= "localhost"; 
	$user		= "root"; 
	$password	= ""; 
	$database	= "tugas";
	
	$con = mysqli_connect($server, $user, $password, $database);
	if (mysqli_connect_errno()) {
		echo "Gagal terhubung MySQL: " . mysqli_connect_error();
	}

	class usr{}
	
	$nim = $_POST["nim"];
	$password = $_POST["password"];
	
	if ((empty($nim)) || (empty($password))) { 
		$response = new usr();
		$response->success = 0;
		$response->message = "Kolom tidak boleh kosong"; 
		die(json_encode($response));
	}
	
	$query = mysqli_query($con, "SELECT * FROM tugas WHERE nim='$nim' AND password='$password'");
	
	$row = mysqli_fetch_array($query);
	
	if (!empty($row)){
		$response = new usr();
		$response->success = 1;
		$response->message = "Selamat datang ".$row['nim'];
		$response->id = $row['id'];
		$response->nim = $row['nim'];
		die(json_encode($response));
		
	} else { 
		$response = new usr();
		$response->success = 0;
		$response->message = "nim atau password salah";
		die(json_encode($response));
	}
	
	mysqli_close($con);

?>