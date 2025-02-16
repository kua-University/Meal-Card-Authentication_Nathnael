<?php
require('../../include/db_connection.php');
require('../../include/meal_time.php');
header('Content-Type: application/json');

$response = [
    "Meal_Card_Authenticate" => [
        [
            "status" => "",
            "message" => ""
        ]
    ]
];

// Get and validate student ID from POST data
$studentId = isset($_POST['Id']) ? trim($_POST['Id']) : '';
if (empty($studentId)) {
    $response["Meal_Card_Authenticate"][0]["status"] = "error";
    $response["Meal_Card_Authenticate"][0]["message"] = "ID is Empty or Null.";
    echo json_encode($response);
    exit;
}

function isCafeUser($con, $studentId)
{
    global $response;

    $sql = "SELECT is_cafe, user_id, name, profile_picture FROM student WHERE user_id = ?";
    $stmt = $con->prepare($sql);
    $stmt->bind_param("s", $studentId);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();
        $response["Meal_Card_Authenticate"][0]["Id"] = $row['user_id'];
        $response["Meal_Card_Authenticate"][0]["Name"] = $row['name'];
        $response["Meal_Card_Authenticate"][0]["Images"] = $row['profile_picture'];
        return $row['is_cafe'] == 1;
    } else {
        $response["Meal_Card_Authenticate"][0]["status"] = "103_failed";
        $response["Meal_Card_Authenticate"][0]["message"] = "The ID: $studentId doesn't exist in the Database.";
        echo json_encode($response);
        exit;
    }
}

function insertMealRecord($con, $studentId, $mealTime)
{
    global $response;

    $sqlCheck = "SELECT remark_id FROM remark WHERE remark_id = ?";
    $stmt = $con->prepare($sqlCheck);
    $stmt->bind_param("s", $studentId); // Assuming remark_id is of type string
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows == 0) {
        $today = date("Y-m-d");
        $sqlCheck = "SELECT id FROM $mealTime WHERE student_id = ? AND DATE(created_at) = ?";
        $stmt = $con->prepare($sqlCheck);
        $stmt->bind_param("ss", $studentId, $today);
        $stmt->execute();
        $result = $stmt->get_result();

        if ($result->num_rows == 0) {
            $sqlInsert = "INSERT INTO $mealTime (student_id, created_at) VALUES (?, NOW())";
            $stmt = $con->prepare($sqlInsert);
            $stmt->bind_param("s", $studentId);

            if ($stmt->execute()) {
                $response["Meal_Card_Authenticate"][0]["status"] = "success";
                $response["Meal_Card_Authenticate"][0]["message"] = "Record inserted successfully for $mealTime.";
            } else {
                $response["Meal_Card_Authenticate"][0]["status"] = "402_error";
                $response["Meal_Card_Authenticate"][0]["message"] = "Database error: " . $con->error;
            }
        } else {
            $response["Meal_Card_Authenticate"][0]["status"] = "exists";
            $response["Meal_Card_Authenticate"][0]["message"] = "Student already has a record for $mealTime today.";
        }
    } else {
        $response["Meal_Card_Authenticate"][0]["status"] = "reported";
        $response["Meal_Card_Authenticate"][0]["message"] = "The Student has been Marketed or Reported.";
    }
}

// Check if student is a cafe user
if (isCafeUser($con, $studentId)) {
    $mealTime = getMealTime();

    if ($mealTime) {
        insertMealRecord($con, $studentId, $mealTime);
    } else {
        $response["Meal_Card_Authenticate"][0]["status"] = "102_failed";
        $response["Meal_Card_Authenticate"][0]["message"] = "Cafe is not open at this time.";
    }
} else {
    $response["Meal_Card_Authenticate"][0]["status"] = "101_failed";
    $response["Meal_Card_Authenticate"][0]["message"] = "The student is not a cafe user.";
}

// Return the JSON response
echo json_encode($response);

// Close the database connection
$con->close();
