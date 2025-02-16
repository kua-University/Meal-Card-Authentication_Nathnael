package com.mu.mealcardauthentication;

public class ApiConfiguration {
    public static final String Domain = "http://server.local/";
    public static final String GET = Domain + "api/get/";
    public static final String POST = Domain + "api/post/";
    public static final String Storage_URL = Domain + "storage/profile-picture/";


//    Post Matted
    public static final String LOGIN_URL = POST + "post_login.php";
    public static final String LOST_ITEM_URL = POST + "post_lost_item.php";
    public static final String REPORT_URL = POST + "post_report.php";
    public static final String MEAL_CARD_AUTHENTICATE_URL = POST + "post_meal_card_authenticate.php";

//    Get Matted
    public static final String LostID_URL = GET + "get_lostID.php";
    public static final String FOOD_MENU_URL = GET + "get_food_menu.php";
    public static final String Remark_URL = GET + "get_remark.php";
}