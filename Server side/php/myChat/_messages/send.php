<?php

$sender = $_POST['sender'];
$receiver = $_POST['receiver'];
$senderName = $_POST['senderName'];
$receiverName = $_POST['receiverName'];
$text = $_POST['message'];


$m = new MongoClient();
$db = $m->selectDB('test');
$collection = new MongoCollection($db, 'messages');
$cursor = $collection->insert(
    array(
    'sender' => $sender,
    'receiver' => $receiver,
    'senderName' => $senderName,
    'receiverName' => $receiverName,
    'text' => $text
    ));

if($cursor != NULL)
    print_r(("sended"));
else
    print_r(("Error"));
?>