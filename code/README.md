## Clone our repo: https://github.com/AustinDrakeHodge/swe-3133-spring-2025-team-6.git

## Install MySQL Community Server (https://dev.mysql.com/downloads/mysql/)
 - Use "Typical" Installation
- When it's done, confirm the option to start the MySQL Server Configurator is checked
- Continue through the wizard leaving all options default until it forces you to enter a MySQL root password.
- You can either use the one we specified in our project "team6", or provide your own.
- If you provide your own, you'll need to change the one listed in the application.properties file in our repo to get our project to run correctly.
- Continue clicking Next until you get to the end
- Click "Execute" to start the server
- Click Next and Finish when it's done

## Install IntelliJ Ultimate Edition (https://www.jetbrains.com/idea/download/?section=windows).
- In IntelliJ, click "Open"
- Navigate to where you cloned the git repo, then open "\swe-3133-spring-2025-team-6\code" and double-click pom.xml at the bottom.
- Click "Open as Project" in the ensuing popup.
- If you get a "Trust and Open Project?" popup, trust us. It's fine.
-> If you see "project jdk is not defined" errors, click the Menu button in the top left > Project Structure... and set SDK to openjdk-24 (you may have to download it in the same window if your system is out of date).
>> If you set a password other than "team6" in the MySQL setup, you'll need to open code/src/main/resources/application.properties and change "spring.datasource.password" to the one you specified

## Running the program
> Click the ">" run button at the top in IntelliJ.
> Open a browser window and navigate to localhost:8080/login
> There are two initial users you can login with:
>> "user1" : "password"
>> "admin" : "password"
> user1 has no admin priveleges, while admin does. You can also register to create your own user, but you'll have to use the admin login to promote it or any other user.
