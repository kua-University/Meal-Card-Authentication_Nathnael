<?php
function getMealTime()
{
    $currentTime = date("H:i");

    if ($currentTime >= "04:00" && $currentTime <= "18:30") {
        return "breakfast";
    } elseif ($currentTime >= "06:30" && $currentTime <= "02:00") {
        return "lunch";
    } elseif ($currentTime >= "01:00" && $currentTime <= "01:30") {
        return "dinner";
    } else {
        return null;
    }
}
