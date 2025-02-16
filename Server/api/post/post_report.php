<?php
require('../../include/db_connection.php');
header('Content-Type: application/json');

$response = [
    "Report_PostRequest" => [
        [
            "status" => "",
            "message" => ""
        ]
    ]
];

$id = $_POST['id'] ?? '';
if (empty($id)) {
    $response["Report_PostRequest"][0]["status"] = "failure";
    $response["Report_PostRequest"][0]["message"] = "ID are required.";
    echo json_encode($response);
    exit;
}

$sql = "SELECT name, department, profile_picture FROM student WHERE user_id = ?";
$stmt = $con->prepare($sql);

if (!$stmt) {
    $response["Report_PostRequest"][0]["status"] = "error";
    $response["Report_PostRequest"][0]["message"] = "Database error: " . $con->error;
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
    $check_sql = "SELECT remark_id FROM remark WHERE remark_id = ?";
    $check_stmt = $con->prepare($check_sql);

    if (!$check_stmt) {
        $response["Report_PostRequest"][0]["status"] = "402_error";
        $response["Report_PostRequest"][0]["message"] = "Database error: " . $con->error;
        echo json_encode($response);
        exit;
    }

    $check_stmt->bind_param("s", $id);
    $check_stmt->execute();
    $check_result = $check_stmt->get_result();

    if ($check_result->num_rows > 0) {
        $response["Report_PostRequest"][0]["status"] = "exists";
        $response["Report_PostRequest"][0]["message"] = "The ID already exists in the remark_id table.";
    } else {
        $insert_sql = "INSERT INTO remark (remark_id, name, department, profile_picture, academic_year) VALUES (?, ?, ?, ?, ?)";
        $insert_stmt = $con->prepare($insert_sql);

        if (!$insert_stmt) {
            $response["Report_PostRequest"][0]["status"] = "402_error";
            $response["Report_PostRequest"][0]["message"] = "Database error: " . $con->error;
            echo json_encode($response);
            exit;
        }

        $insert_stmt->bind_param("sssss", $id, $name, $department, $profile_picture, $academicYear);

        if ($insert_stmt->execute()) {
            $response["Report_PostRequest"][0]["status"] = "success";
            $response["Report_PostRequest"][0]["message"] = "Remark Id reported successfully.";
        } else {
            $response["Report_PostRequest"][0]["status"] = "error";
            $response["Report_PostRequest"][0]["message"] = "Failed to report remark Id: " . $insert_stmt->error;
        }

        $insert_stmt->close();
    }

    $check_stmt->close();
} else {
    $response["Report_PostRequest"][0]["status"] = "404_error";
    $response["Report_PostRequest"][0]["message"] = "The ID you provided is unregistered or invalid.";

    echo json_encode($response);
}

echo json_encode($response);

$stmt->close();
$con->close();
