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
$result = mysqli_query($db->connect(),"SELECT * FROM users") or die(mysqli_error());

// check for empty result
if (mysqli_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["users"] = array();

    while ($row = mysqli_fetch_array($result)) {
        // temp user array
        $users = array();
        $users["id"] = $row["id"];
        $users["name"] = $row["name"];
        $users["email"] = $row["email"];
        $users["phone"] = $row["phone"];
        // push single product into final response array
        array_push($response["users"], $users);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No products found";

    // echo no users JSON
    echo json_encode($response);
}
?>