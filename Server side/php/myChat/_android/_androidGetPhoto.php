<?php

$filename = $_GET['email'];

try {
  // open connection to MongoDB server
  $conn = new MongoClient();

  // access database
  $db = $conn->selectDB('test');

  // get GridFS files collection
  $grid = $db->getGridFS();
  
  // retrieve file from collection
  header('Content-type: image/png');
  $file = $grid->findOne(array('filename' => $filename));

  // send headers and file data

  echo $file->getBytes();
  exit;  

  // disconnect from server
  $conn->close();
} catch (MongoConnectionException $e) {
  die('Error connecting to MongoDB server');
} catch (MongoException $e) {
  die('Error: ' . $e->getMessage());
}

?>