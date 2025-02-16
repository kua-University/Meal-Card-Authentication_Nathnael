<?php
$servername = "localhost";
$username = "root";
$password = "root";
$dbname = "meal_card_authentication";

$con = new mysqli($servername, $username, $password, $dbname);

if ($con->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
