<?php

/*
 * Following code will update a product information
 * A product is identified by product id (pid)
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['id']) && isset($_POST['phone']) && isset($_POST['email']) && isset($_POST['username'])) {

    $id = $_POST['id'];
    $name = $_POST['username'];
    $phone = $_POST['phone'];
    $email = $_POST['email'];

    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql update row with matched pid
    $result = mysqli_query($db->connect(),"UPDATE users SET name = '$name', phone = '$phone', email = '$email' WHERE id = '$id'");

    // check if row inserted or not
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "User Details Successfully Updated.";

        // echoing JSON response
        echo json_encode($response);
    } else {

    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}
?>