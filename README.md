# Meal Card Authentication App
## Project Overview
	The Meal Card Authentication App is an Android application designed to authenticate 
	students using their University IDs QR Code. It provides a seamless and secure way
	for service providers to verify when students want to access meal services.
##  Features
	* User authentication via QR Code. 
	* MySQL database integration for user data storage.
	* XAMPP server for hosting PHP scripts.
	* Simple and user-friendly interface.
## 📂 Project Structure

	MealCardAuthentication
	   │── MealCardAuthentication
	   │	    └──  app
	   │              ├── manifests  
	   │	          ├── java  
	   │              └── res  
	   │	               ├── drawable  
	   │		       ├── layout  
	   │		       ├── mipmap  
	   │		       ├── values  
	   │		       └── xml  
	   │── Server
	   │	  ├── api
	   │      │    ├── get
	   │      │    │    ├── get_food_menu.php
	   │      │    │    ├── get_lostID.php
	   │      │    │    └── get_remark.php
	   │      │    └── post
	   │      │         ├── post_login.php
	   │      │         ├── post_lost_item.php
	   │      │         ├── post_meal_card_authenticate.php
	   │      │         └── post_report.php
	   │      ├──  include
	   │      │     ├── db_connection.php
	   │      │     └── meal_time.php
	   │      ├── storage
	   │      │    └── profile-picture
	   │      │          ├──img-1.png
	   │      │          ├──img-2.png
	   │      │          ├──img-3.png
	   │      │          └──img-4.png
	   │      └──  tools
	   │            └── Passkey_Hash.php
	   ├──  Database
	   │       ├── meal_card_authentication_user.sql
	   │       ├── meal_card_authentication_student.sql
	   │       ├── meal_card_authentication_food_menu.sql
	   │       ├── meal_card_authentication_lost_id.sql
	   │       ├── meal_card_authentication_remark.sql
	   │       ├── meal_card_authentication_breakfast.sql
	   │       ├── meal_card_authentication_lunch.sql
	   │       └── meal_card_authentication_dinner.sql
	   └── README.md 				
## Technologies Used
	* Android (XML/Java) – Mobile app development.
	* MySQL – Database to store user data and meal details.
	* PHP – Backend API for database interaction.
	* XAMPP – Local server for hosting PHP scripts.
##  Installation Guide
##### Set Up MySQL Database
	Install XAMPP and start Apache & MySQL.
	Open phpMyAdmin and create a database (meal_card_authentication).
	Import all the provided SQL file in Database folder.
	
##### Configure PHP Backend
	Copy the Server folder to htdocs inside XAMPP.
	Open C:\xampp\apache\conf\extra and add the apache code to httpd-vhosts.conf
	Open the hosts file located at C:\Windows\System32\drivers\etc and add the hosts code to hosts.
	In the Server\include folder you have db_connection.php it is configuration fill make sure everything is correct.
	Start XAMPP and ensure all the scripts are accessible via http://server.local
	
```apache
<VirtualHost *:80>
    ServerName server.local
    DocumentRoot "C:\xampp\htdocs\Server"
    <Directory "C:\xampp\htdocs\Server">
        AllowOverride All
        Require all granted
    </Directory>
</VirtualHost>
```

```hosts
# localhost name resolution is handled within DNS itself.
		192.168.1.4     server.local
# Note use your current IP address that your machine is connected to you can check by opening CMD and typing ipconfig then use the IP address written
```

##### Configure Android App
	Open the MealCardAuthentication project in Android Studio
	Build and run the app on an Android emulator or real device

#### #Note 
* Use Passkey_Hash.php to hash passkeys for the database to store.
* This project is for educational purposes only.
#### Full Contact
	Name: Nathnael Balcha Edaie
	Phone No: +2519 3788 6922
	Email Address: nathnaelbalcha96@gmail.com
