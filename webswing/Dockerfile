# Use a base image with a Java runtime
FROM openjdk:11-jre-slim

# Set the working directory
WORKDIR /app

# Copy the WebSwing files into the container
COPY . .

# Expose the port used by WebSwing
EXPOSE 8080

# Start WebSwing
CMD ["java", "-jar", "server/webswing-jetty-launcher.jar", "-j", "jetty.properties", "-serveradmin", "-pfa", "admin/webswing-admin.properties", "-adminctx", "/admin", "-aw", "admin/webswing-admin-server.war"]
