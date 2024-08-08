#!/bin/bash

# Define the image and container names (lowercase)
IMAGE_NAME="be_sacabank"
CONTAINER_NAME="be_sacabank"

# Stop and remove the old container if it exists
if docker ps -a --format '{{.Names}}' | grep -Eq "^${CONTAINER_NAME}$"; then
    echo "Stopping and removing old Docker container: $CONTAINER_NAME"
    docker stop $CONTAINER_NAME
    docker rm $CONTAINER_NAME
else
    echo "No existing container to remove."
fi

# Remove the old image if it exists
if docker images -q $IMAGE_NAME > /dev/null; then
    echo "Removing old Docker image: $IMAGE_NAME"
    docker rmi -f $IMAGE_NAME
else
    echo "No existing image to remove."
fi

# Build the new Docker image
docker build -t $IMAGE_NAME .

# Run the Docker container
docker run -d -p 8080:8080 --name $CONTAINER_NAME $IMAGE_NAME
