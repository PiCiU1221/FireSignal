#!/bin/bash
Xvfb $DISPLAY -screen 0 1024x768x16 &
sleep 5
java -jar server/webswing-jetty-launcher.jar -j jetty.properties -serveradmin -pfa admin/webswing-admin.properties -adminctx /admin -aw admin/webswing-admin-server.war
