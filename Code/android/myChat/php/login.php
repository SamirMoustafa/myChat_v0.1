<?php 
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "accounts";

// Create connection
$con = mysqli_connect($servername, $username, $password, $dbname);	
$email=$_POST['email'];
$password=$_POST['password'];
$query="SELECT * FROM data WHERE email = '$email' AND password = '$password'";
$res = mysqli_query($con,$query);
if (  mysqli_num_rows($res) > 0){
	while ($row = mysqli_fetch_assoc($res)) {
		 echo $row['id']." ". $row['name']." ".$row['email']." ".$row['password'];
	
}}
else {
	echo "Wrong data";
}
?>