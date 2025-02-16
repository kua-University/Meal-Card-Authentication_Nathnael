<?php
require('../../include/db_connection.php');
header("Content-Type: application/json");

if ($con) {
    $sql = "SELECT * FROM lost_id ORDER BY created_at DESC";
    $result = mysqli_query($con, $sql);

    if ($result) {
        $response = [];
        while ($row = mysqli_fetch_assoc($result)) {
            $response[] = [
                'id' => $row['id'],
                'remark_id' => $row['lost_id'],
                'name' => $row['name'],
                'department' => $row['department'],
                'profile_picture' => $row['profile_picture'],
                'created_at' => $row['created_at'],
                'updated_at' => $row['updated_at'],
            ];
        }
        $output = ['Remark_Data' => $response];
        echo json_encode($output, JSON_PRETTY_PRINT);
    } else {
        echo json_encode(['error' => 'Query failed']);
    }
} else {
    echo json_encode(['error' => 'Database connection failed']);
}

$con->close();
