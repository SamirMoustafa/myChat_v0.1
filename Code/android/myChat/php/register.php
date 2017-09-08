<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "accounts";

// Create connection
$conn = mysqli_connect($servername, $username, $password, $dbname);
$name =$_POST['name'];
$email=$_POST['email'];
$password=$_POST['password'];
// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

$sql = "INSERT INTO data (name,email,password) VALUES ('$name','$email','$password')";

if (mysqli_query($conn, $sql)) {
    echo "Register Successfuly";
} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($conn);
}

mysqli_close($conn);
?>