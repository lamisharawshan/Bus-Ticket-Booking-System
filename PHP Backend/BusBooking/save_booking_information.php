<?php

/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['routeId']) && isset($_POST['cost']) && isset($_POST['person_number'])&& isset($_POST['card_number'])&& isset($_POST['cvc'])&& isset($_POST['surname'])&& isset($_POST['name'])) {

    $routeId = $_POST['routeId'];
    $cost = $_POST['cost'];
    $person_number = $_POST['person_number'];
    $cardnumber = $_POST['card_number'];
    $cvc= $_POST['cvc'];
    $surname = $_POST['surname'];
    $name = $_POST['name'];
    $userID=$_POST['user_id'];

    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();
    $con=$db->connect();
    $result = mysqli_query($con,"INSERT INTO booking(route_id, userid, ticket_number,cost) VALUES('$routeId', '$userID', '$person_number','$cost')");
    $last_id = mysqli_insert_id($con);


    if ($result) {
        $result = mysqli_query($db->connect(),"INSERT INTO payment(bookingID, cardnumber, cvc,name,surname, cost) VALUES(' $last_id ', '$cardnumber', '$cvc','$name','$surname','$cost')");
        $response["lastid"] = $last_id ;
        $response["success"] = 1;
        $response["message"] = "booked";
        $result=mysqli_query($db->connect(),"SELECT available_seat FROM `route_time` WHERE route_time_id=$routeId");
        $result = mysqli_fetch_array($result);
        $availa_seat=(int) $result[available_seat];
        $newavaila_seat=$availa_seat-$person_number;
        $result=mysqli_query($db->connect(),"UPDATE route_time set available_seat=$newavaila_seat WHERE route_time_id=$routeId");

        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";

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