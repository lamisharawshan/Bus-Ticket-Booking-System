<?php

/*
 * Following code will list all the products
 */

// array for JSON response
$response = array();

// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

// get all products from products table
$result = mysqli_query($db->connect(),"SELECT * FROM notification") or die(mysqli_error());

// check for empty result
if (mysqli_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["places"] = array();

    while ($row = mysqli_fetch_array($result)) {
        // temp user array
        $places = array();
        $places["source"] = $row["Source"];
        $places["destination"] = $row["Destination"];

        array_push($response["places"], $places);
    }

    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No places found";

    // echo no users JSON
    echo json_encode($response);
}
?>