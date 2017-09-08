<?php

header('Content-type: application/json');

$sender = $_POST['sender'];
$receiver = $_POST['receiver'];



$m = new MongoClient();
$db = $m->selectDB('test');
$collection = new MongoCollection($db, 'messages');

$cursor = $collection->find(array('$or' => array(
                                                array('sender' => $sender,'receiver' => $receiver),
                                                array('sender' => $receiver,'receiver' => $sender))));

print_r( json_encode(iterator_to_array($cursor,0)));


?>