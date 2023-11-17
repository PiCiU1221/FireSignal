FROM openjdk:17-jdk-slim

# Install dependencies
RUN apt-get update && \
    apt-get install -y xvfb libxext6 libxi6 libxtst6 libxrender1 libpangoft2-1.0-0

# Set environment variables
ENV DISPLAY=:99

# Expose the port used by WebSwing
EXPOSE 8080

# Copy the WebSwing files into the container
COPY . .

# Copy the entry point script
COPY entrypoint.sh /entrypoint.sh

# Give execute permissions to the entry point script
RUN chmod +x /entrypoint.sh

# Set the entry point for the container
ENTRYPOINT ["/entrypoint.sh"]
