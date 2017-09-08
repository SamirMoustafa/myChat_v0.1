<?php

$base64image = $_POST['image'];
$imageName = $_POST['email'];


$m = new MongoClient();

$gridfs = $m->selectDB('test')->getGridFS();

$image = base64_decode($base64image);

file_put_contents('/_androidImage.png', $image);


$id = $gridfs->storeFile('/_androidImage.png', array('filename' => $imageName, 'contentType'=>null, 'aliases'=>null));

$gridfsFile = $gridfs->get($id);

print_r(json_encode($gridfsFile->file));


?>


