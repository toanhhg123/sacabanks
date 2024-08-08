@echo off

REM Define the image name
SET IMAGE_NAME=be_sacabank

REM Check if the image exists
FOR /F "tokens=*" %%i IN ('docker images -q %IMAGE_NAME%') DO SET IMAGE_ID=%%i

REM Remove the old image if it exists
IF DEFINED IMAGE_ID (
    echo Removing old Docker image: %IMAGE_NAME%
    docker rmi -f %IMAGE_NAME%
) ELSE (
    echo No existing image to remove.
)

REM Build the new Docker image
docker build -t %IMAGE_NAME% .

REM Run the Docker container
docker run -d -p 8080:8080 --name your-container-name %IMAGE_NAME%
