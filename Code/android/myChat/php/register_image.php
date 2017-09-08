 <?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "accounts";

// Create connection
$con = mysqli_connect($servername, $username, $password, $dbname);

 if($_SERVER['REQUEST_METHOD']=='POST'){
 
 $image = $_POST['image'];
 
 $sql = "INSERT INTO images (image) VALUES (?)";
 
 $stmt = mysqli_prepare($con,$sql);
 
 mysqli_stmt_bind_param($stmt,"s",$image);
 mysqli_stmt_execute($stmt);
 
 $check = mysqli_stmt_affected_rows($stmt);
 
 if($check == 1){
 echo "Image Uploaded Successfully";
 }else{
 echo "Error Uploading Image";
 }
 mysqli_close($con);
 }else{
 echo "Error";
 }
 ?>