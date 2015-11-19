# Minecraft Server Control Panel
This is a tool that I've created, it's cross platform, means it (SHOULD) work with windows, linux, mac, your home tv...
(In case any of the above do not work, please contact me, or leave a message below)¸

![alt tag](http://i.imgur.com/ui2zDmj.png)

*This tool allows you complete control over your server, provided you have either RCON access or host it localy.*

#SERVER MODES

##1. JAR MODE
![alt tag](http://i.imgur.com/6qR7ohQ.png)

In this mode you can select the server jar file(forge also supporded, just choose forge jar file) and it will start the server.
All the information that you can read on console will be as if you were running the server with regular gui, or console.
The player list on it's right side will update automatically every time someone joins/leaves the server.

##2. RCON Mode
![alt tag](http://i.imgur.com/hAF28ST.png)

This is the standard RCON mode, the console here will only contain the stuff you get after you use a command. The player list will update upon connecting and once per minute after that (using "list" command forces a refresh)

#GENERAL INFO

As you notice the green buttons are the gamerules. Upon connecting/starting the server they will be refreshed from the server, turning red means the rule is off, green means on and if the text is grey the rule is not available on the server (you also won't be able to change it (duh))

Other buttons should be pretty much self explanatory, if not, feel free to ask: **WHAT DOES THIS BUTTON DO?**

Connecting it with the server is done by clicking on "Manage" then choosing the mode.
- use rcon: the string expected is like this: `server_address:rcon_port:rcon_password`
Mistakes here will not crash your pc, the only problem will be that there will be no connection with the server.
- use direct access: click on it and a file dialog will pop up, where you can select the .jar file that you would like to run.
Note: if you already happened to enter the info previously all you need to do is click confirm and magic *WILL* happen.

#ADDITIONAL INFO

##The config File
After running the control pannel for the first time you will notice a file called config.cfg will be created, in it you will find:
- a spam line of when the file was created (do what you want with it)
- *server-ip* - this is the ip/url of the server, in case you so desire to edit it manualy
- *server-port* - this is the port, on which rcon will be trying to connect
- *rcon-password* - this is the password

Note: all of the above can be changed by clicking on "Manage", then "Use rcon" and clicking the "Click here..." text.

Also in config.cfg:
- *server-jar* - the path to the jar of the server, only used if you use direct access.
- *server-param* - should you want to send some extra info to the server when using direct access (command line)
- *min-memory* - initial ammount of memory given to Java when starting the server
- *max-memoty* - max memory when hosting the server
- *java-param* - extra info sent to java (default -XX:MaxPermSize=64M, since I've personaly needed it), separate these with space, start them with a - sign.

#Changelog
- 1.10.2014:
  Version 1.0.0: Released
