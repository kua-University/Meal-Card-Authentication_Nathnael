<?php
require('../../include/db_connection.php');
header('Content-Type: application/json');

$response = [
    "LostItem_PostRequest" => [
        [
            "status" => "",
            "message" => ""
        ]
    ]
];

$id = $_POST['id'] ?? '';
$academicYear = $_POST['academicYear'] ?? '';
if (empty($id) || empty($academicYear)) {
    $response["LostItem_PostRequest"][0]["status"] = "failure";
    $response["LostItem_PostRequest"][0]["message"] = "ID and Academic Year are required.";
    echo json_encode($response);
    exit;
}

$sql = "SELECT name, department, profile_picture FROM student WHERE user_id = ?";
$stmt = $con->prepare($sql);

if (!$stmt) {
    $response["LostItem_PostRequest"][0]["status"] = "error";
    $response["LostItem_PostRequest"][0]["message"] = "Database error: " . $con->error;
    echo json_encode($response);
    exit;
}

$stmt->bind_param("s", $id);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    $row = $result->fetch_assoc();
    $name = $row['name'];
    $department = $row['department'];
    $profile_picture = $row['profile_picture'];
    $check_sql = "SELECT lost_id FROM lost_id WHERE lost_id = ?";
    $check_stmt = $con->prepare($check_sql);

    if (!$check_stmt) {
        $response["LostItem_PostRequest"][0]["status"] = "402_error";
        $response["LostItem_PostRequest"][0]["message"] = "Database error: " . $con->error;
        echo json_encode($response);
        exit;
    }

    $check_stmt->bind_param("s", $id);
    $check_stmt->execute();
    $check_result = $check_stmt->get_result();

    if ($check_result->num_rows > 0) {
        $response["LostItem_PostRequest"][0]["status"] = "exists";
        $response["LostItem_PostRequest"][0]["message"] = "The ID already exists in the lost_id table.";
    } else {
        $insert_sql = "INSERT INTO lost_id (lost_id, name, department, profile_picture, academic_year) VALUES (?, ?, ?, ?, ?)";
        $insert_stmt = $con->prepare($insert_sql);

        if (!$insert_stmt) {
            $response["LostItem_PostRequest"][0]["status"] = "402_error";
            $response["LostItem_PostRequest"][0]["message"] = "Database error: " . $con->error;
            echo json_encode($response);
            exit;
        }

        $insert_stmt->bind_param("sssss", $id, $name, $department, $profile_picture, $academicYear);

        if ($insert_stmt->execute()) {
            $response["LostItem_PostRequest"][0]["status"] = "success";
            $response["LostItem_PostRequest"][0]["message"] = "Lost Id reported successfully.";
        } else {
            $response["LostItem_PostRequest"][0]["status"] = "error";
            $response["LostItem_PostRequest"][0]["message"] = "Failed to report lost Id: " . $insert_stmt->error;
        }

        $insert_stmt->close();
    }

    $check_stmt->close();
} else {
    $response["LostItem_PostRequest"][0]["status"] = "404_error";
    $response["LostItem_PostRequest"][0]["message"] = "The ID you provided is unregistered or invalid.";
}

$stmt->close();
$con->close();

echo json_encode($response);
