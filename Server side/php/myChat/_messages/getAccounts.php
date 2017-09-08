<?php

header('Content-type: application/json');

$m = new MongoClient();
$db = $m->selectDB('test');
$collection = new MongoCollection($db, 'myChat');

$cursor = $collection->find();

print_r( json_encode(iterator_to_array($cursor,0)));

?>