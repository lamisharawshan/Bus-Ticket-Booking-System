<?php

/*
 * Following code will get single product details
 * A product is identified by product id (pid)
 */

// array for JSON response
$response = array();

// include db connect class


// check for post data
if (isset($_POST['source']) && isset($_POST['destintaion'])&& isset($_POST['date'] )){
    $source = $_POST['source'];
    $destintaion= $_POST['destintaion'];
    $date=$_POST['date'];

    // get a product from products table
    require_once __DIR__ . '/db_connect.php';

// connecting to db
    $db = new DB_CONNECT();
   $result = mysqli_query($db->connect(),"SELECT * from route_time, bus where rid in (select rid from route where source IN (select placeID from places WHERE placename='$source') and destination IN (select placeID from places WHERE placename='$destintaion')) and route_date='$date'  AND route_time.bid=bus.bid");
    //$result = mysqli_query($db->connect(),"SELECT * from route_time, bus where rid in (select rid from route where source IN (select placeID from places WHERE placename='Amsterdam Centraal') and destination IN (select placeID from places WHERE placename='Eindhoven Centraal')) and route_date='18/7/2020'  AND route_time.bid=bus.bid");
    //$result = mysqli_query($db->connect(),"SELECT * from route_time");
    if (!empty($result)) {

        // check for empty result
        if (mysqli_num_rows($result) > 0) {
            $response["products"] = array();
            while ($row = mysqli_fetch_array($result)) {
                $product = array();
                $product["source"] = $_POST['source'];
                $product["destintaion"] = $_POST['destintaion'];
                $product["name"] = $row["name"];
                $product["departuretime"] = $row["departure_time"];
                $product["arrivaltime"] = $row["arrival_time"];
                $product["rent"] = $row["rent"];
                $product["route_date"] = $row["route_date"];
                $product["id"] = $row["route_time_id"];
                $product["available_seat"] = $row["available_seat"];
                array_push($response["products"], $product);
            }

            // success
            $response["success"] = 1;

            // echoing JSON response
            echo json_encode($response);
        } else {
            // no product found
            $response["success"] = 0;
            $response["message"] = "No product found";

            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "No product found";

        // echo no users JSON
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