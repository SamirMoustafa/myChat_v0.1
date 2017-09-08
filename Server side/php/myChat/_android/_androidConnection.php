<?php
header('Content-type: application/json');

$email = $_POST['email'];
$pass = $_POST['password'];

$m = new MongoClient();
$db = $m->selectDB('test');
$collection = new MongoCollection($db, 'myChat');
$cursor = $collection->find(array('email' => $email,'password' => $pass));
if($cursor == NULL)
    print_r("no account");
else
    foreach ($cursor as $value)
        print_r(json_encode(array($value)));


?>