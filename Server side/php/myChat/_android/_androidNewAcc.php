<?php

$name = $_POST['name'];
$age = $_POST['age'];
$isActive = false;
$email = $_POST['email'];
$pass = $_POST['password'];
$mobile = $_POST['mobile'];
$photo = $email;


$m = new MongoClient();
$db = $m->selectDB('test');
$collection = new MongoCollection($db, 'myChat');
$cursor = $collection->insert(
    array(
    'isActive' => $isActive,
    'picture' => $photo,
    'age' => (float)$age,
    'name' => $name,
    'email' => $email,
    'password' => $pass,
    'phone' => $mobile,
    'friends' =>new stdClass()   ));

if($cursor != NULL)
    print_r(("Done"));
else
    print_r(("Error"));

?>