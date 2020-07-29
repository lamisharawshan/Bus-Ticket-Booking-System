<?php

/*
 * Following code will get single product details
 * A product is identified by product id (pid)
 */

// array for JSON response
$response = array();

// include db connect class


// check for post data
if (isset($_POST['id'])){
    $id= $_POST['id'];
    // get a product from products table
    require_once __DIR__ . '/db_connect.php';

// connecting to db
    $db = new DB_CONNECT();
   $result = mysqli_query($db->connect(),"SELECT arrival_time,departure_time,rent,route_date,available_seat,bookingID ,cost,ticket_number, route.source as SID, route.destination as DID, (Select places.placename from places where places.placeID=SID) as PSN, (Select places.placename from places where places.placeID=DID) as PDN  FROM `booking`, `route_time`,`route` WHERE  booking.userid=$id and booking.route_id=route_time.route_time_id and  route.rid=route_time.rid ORDER BY booking.bookingID DESC");

    if (!empty($result)) {

        // check for empty result
        if (mysqli_num_rows($result) > 0) {
            $response["products"] = array();
            while ($row = mysqli_fetch_array($result)) {
                $product = array();
                $product["arrival_time"] = $row["arrival_time"];
                $product["departure_time"] = $row["departure_time"];
                $product["rent"] = $row["rent"];
                $product["route_date"] = $row["route_date"];
                $product["available_seat"] = $row["available_seat"];
                $product["bookingID"] = $row["bookingID"];
                $product["cost"] = $row["cost"];
                $product["ticket_number"] = $row["ticket_number"];
                $product["PSN"] = $row["PSN"];
                $product["PDN"] = $row["PDN"];
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