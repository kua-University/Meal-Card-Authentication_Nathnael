<?php
require('../../include/db_connection.php');
header('Content-Type: application/json');

// Initialize the response array
$response = [
    "Login_Validation" => [
        [
            "status" => "",
            "message" => "",
            "user" => null
        ]
    ]
];

// Get the phone number and passkey from the POST request
$phoneNo = $_POST['phoneNo'] ?? '';
$passkey = $_POST['Passkey'] ?? '';

// Validate input
if (empty($phoneNo) || empty($passkey)) {
    $response["Login_Validation"][0]["status"] = "failure";
    $response["Login_Validation"][0]["message"] = "Phone number and Passkey are required.";
    echo json_encode($response);
    exit;
}

// Prepare and execute the SQL query
$stmt = $con->prepare("SELECT passkey, name, profile_picture FROM user WHERE phone_no = ?");
if (!$stmt) {
    $response["Login_Validation"][0]["status"] = "error";
    $response["Login_Validation"][0]["message"] = "Database error: " . $con->error;
    echo json_encode($response);
    exit;
}

$stmt->bind_param("s", $phoneNo);
$stmt->execute();
$result = $stmt->get_result();

// Check if the user exists
if ($result->num_rows > 0) {
    $row = $result->fetch_assoc();
    $hashedPasskey = $row['passkey'];

    // Verify the passkey
    if (password_verify($passkey, $hashedPasskey)) {
        $response["Login_Validation"][0]["status"] = "success";
        $response["Login_Validation"][0]["message"] = "Login successful.";
        $response["Login_Validation"][0]["user"] = $row['name'];
        $response["Login_Validation"][0]["profile_picture"] = $row['profile_picture'];
    } else {
        $response["Login_Validation"][0]["status"] = "failure";
        $response["Login_Validation"][0]["message"] = "Invalid Passkey.";
    }
} else {
    $response["Login_Validation"][0]["status"] = "failure";
    $response["Login_Validation"][0]["message"] = "User not found.";
}

// Send the JSON response
echo json_encode($response);

// Close the statement and connection
$stmt->close();
$con->close();
