#!/bin/bash

# Define the image name (lowercase)
IMAGE_NAME="be_sacabank"

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
docker run -d -p 8080:8080 --name your-container-name $IMAGE_NAME
