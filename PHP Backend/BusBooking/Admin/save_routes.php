<?php

/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['arrivalTime']) && isset($_POST['departureTime']) && isset($_POST['rent'])&& isset($_POST['day'])&& isset($_POST['busid'])&& isset($_POST['Route'])) {

    $arrivalTime = $_POST['arrivalTime'];
    $departureTime = $_POST['departureTime'];
    $rent = $_POST['rent'];
    $day = $_POST['day'];
    $busid=$_POST['busid'];
    $routeID=$_POST['Route'];
    $seat="30";
    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql inserting a new row
    $result = mysqli_query($db->connect(),"INSERT INTO route_time(rid, bid, arrival_time, departure_time, rent, route_date, available_seat) VALUES('$routeID', '$busid', '$arrivalTime', '$departureTime','$rent','$day','$seat')");

    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $result = mysqli_query($db->connect(),"SELECT placename from places, route where  route.rid='$routeID' and placeID=source");
        $result = mysqli_fetch_array($result);
        $source=$result[placename];
        $result = mysqli_query($db->connect(),"SELECT placename from places, route where  route.rid='$routeID' and placeID=route.destination");
        $result = mysqli_fetch_array($result);
        $destination=$result[placename];
        $result = mysqli_query($db->connect(),"INSERT INTO notification(Source, Destination) VALUES('$source', '$destination')");
        $response["success"] = 1;
        $response["message"] = "bus successfully created.";

        // echoing JSON response
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