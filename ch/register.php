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

    $nama = $_POST["nama"];
	$nim = $_POST["nim"];
	$password = $_POST["password"];
	$confirm_password = $_POST["confirm_password"];

    if ((empty($nama))) {
		$response = new usr();
		$response->success = 0;
		$response->message = "Kolom nama tidak boleh kosong";
		die(json_encode($response));
	}else if ((empty($nim))) {
		$response = new usr();
		$response->success = 0;
		$response->message = "Kolom nim tidak boleh kosong";
		die(json_encode($response));
	} else if ((empty($password))) {
		$response = new usr();
		$response->success = 0;
		$response->message = "Kolom password tidak boleh kosong";
		die(json_encode($response));
	} else if ((empty($confirm_password)) || $password != $confirm_password) {
		$response = new usr();
		$response->success = 0;
		$response->message = "Konfirmasi password tidak sama";
		die(json_encode($response));
	} else {
		if (!empty($nim) && $password == $confirm_password){
			$num_rows = mysqli_num_rows(mysqli_query($con, "SELECT * FROM tugas WHERE nim='".$nim."'"));

			if ($num_rows == 0){
				$query = mysqli_query($con, "INSERT INTO tugas (id, nama, nim, password) VALUES(0,'".$nama."','".$nim."','".$password."')");

				if ($query){
					$response = new usr();
					$response->success = 1;
					$response->message = "Register berhasil, silahkan login.";
					die(json_encode($response));

				} else {
					$response = new usr();
					$response->success = 0;
					$response->message = "NIM sudah ada";
					die(json_encode($response));
				}
			} else {
				$response = new usr();
				$response->success = 0;
				$response->message = "NIM sudah ada";
				die(json_encode($response));
			}
		}
	}

	mysqli_close($con);

?>	