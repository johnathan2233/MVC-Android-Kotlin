<?php 
// buat select semua jurusan gak perlu get/post


//langsung Koneksi database MySQL masukin server, user, pass, nama database
$koneksi = mysqli_connect("localhost","root","","tugas") 
    or die("Error ".mysqli_error($koneksi));


//lanjut bikin query nya.. select semua pke *, tinggal ganti nama table aja
$sql = "SELECT * FROM tugas ORDER BY id ASC";


//eksekusi queri
$hasil = mysqli_query($koneksi, $sql);


//cek hasilnya ada atau gak
if (mysqli_num_rows($hasil) > 0) {

	//klo ada, ubah hasil dr mysql jd array
	
    while($row = mysqli_fetch_assoc($hasil)) {
    	$hasil_array[] = $row;
    }

    //tampilkan ke format json
    echo json_encode($hasil_array);

} else {

	#klo gak ada hasil, tampilkan 0
	echo json_decode(0);

}

//tutup koneksi
mysqli_close($koneksi);

?>