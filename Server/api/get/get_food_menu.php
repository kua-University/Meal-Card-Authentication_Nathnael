<?php
require('../../include/db_connection.php');
header("Content-Type: application/json");

if ($con) {
    $sql = "SELECT * FROM food_menu WHERE date = DAYNAME(CURDATE())";
    $result = mysqli_query($con, $sql);

    if ($result) {
        $response = [];
        while ($row = mysqli_fetch_assoc($result)) {
            $response[] = [
                'id' => $row['id'],
                'food_name' => $row['food_name'],
                'type' => $row['type'],
                'date' => $row['date'],
                'created_at' => $row['created_at'],
                'updated_at' => $row['updated_at'],
            ];
        }
        $output = ['Food_Menu' => $response];
        echo json_encode($output, JSON_PRETTY_PRINT);
    } else {
        $response["LostItem_PostRequest"][0]["status"] = "402_error";
        $response["LostItem_PostRequest"][0]["message"] = "Query failed: " . $con->error;
        echo json_encode($response);
    }
} else {
    $response["LostItem_PostRequest"][0]["status"] = "402_error";
    $response["LostItem_PostRequest"][0]["message"] = "Database error: " . $con->error;
    echo json_encode($response);
}

$con->close();
