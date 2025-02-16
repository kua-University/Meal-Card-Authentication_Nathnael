<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hashing Input</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }

        input[type="text"],
        input[type="submit"] {
            padding: 10px;
            margin-top: 10px;
        }

        .result {
            margin-top: 20px;
        }
    </style>
</head>

<body>
    <h1>Hash Your Input</h1>
    <form method="post" action="">
        <label for="inputValue">Enter Value to Hash:</label><br>
        <input type="text" id="inputValue" name="inputValue" required>
        <br>
        <input type="submit" value="Hash It!">
    </form>

    <div class="result">
        <?php
        if ($_SERVER["REQUEST_METHOD"] == "POST") {
            $inputValue = $_POST['inputValue'];
            $hashedValue = password_hash($inputValue, PASSWORD_DEFAULT);
            echo "<h3>Hashed Value:</h3>";
            echo "<p>$hashedValue</p>";
        }
        ?>
    </div>
</body>

</html>