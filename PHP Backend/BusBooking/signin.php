<?php

$response = array();

// check for required fields
if (isset($_POST['email']) && isset($_POST['password']) ){

    $email = $_POST['email'];
    $password = md5($_POST['password']);


    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql inserting a new row
    $result = mysqli_query($db->connect(),"select * from users where email = '$email' AND password = '$password' Limit 1");

    // check if row inserted or not

if(mysqli_num_rows($result) > 0){

    mysqli_close($db->connect());
    $result = mysqli_fetch_array($result);

    $response["success"] = 1;
    $response["message"] = "login Successful";
    $response["name"] = $result["name"];
    $response["email"] = $result["email"];
    $response["phone"] = $result["phone"];
    $response["id"] = $result["id"];

   echo json_encode($response);
}

else {
        // failed to insert row
        $response["success"] =  0;
        $response["message"] = "Wrong input";

        // echoing JSON response
        echo json_encode($response);
    }

} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}

?>