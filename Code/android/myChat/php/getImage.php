<?php
  $servername = "localhost";
$username = "root";
$password = "";
$dbname = "accounts";

// Create connection
$con = mysqli_connect($servername, $username, $password, $dbname);

 if($_SERVER['REQUEST_METHOD']=='GET'){
 $id = $_GET['id'];
 $sql = "select * from images where id = '$id'";
 
 $r = mysqli_query($con,$sql);
 
 $result = mysqli_fetch_array($r);
 
 header('content-type: image/jpeg');
 
 echo base64_decode($result['image']);
 
 mysqli_close($con);
 
 }else{
 echo "Error";
 }
 ?>