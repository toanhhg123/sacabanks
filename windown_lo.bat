@echo off

REM Define the image and container names (lowercase)
SET IMAGE_NAME=be_sacabank
SET CONTAINER_NAME=be_sacabank

REM Check if the container exists and remove it
docker ps -a --format "{{.Names}}" | findstr /i /r "^%CONTAINER_NAME%$" > nul
IF %ERRORLEVEL% == 0 (
    echo Stopping and removing old Docker container: %CONTAINER_NAME%
    docker stop %CONTAINER_NAME%
    docker rm %CONTAINER_NAME%
) ELSE (
    echo No existing container to remove.
)

REM Check if the image exists and remove it
docker images -q %IMAGE_NAME% > nul
IF %ERRORLEVEL% == 0 (
    echo Removing old Docker image: %IMAGE_NAME%
    docker rmi -f %IMAGE_NAME%
) ELSE (
    echo No existing image to remove.
)

REM Build the new Docker image
docker build -t %IMAGE_NAME% .

REM Run the Docker container
docker run -d -p 8080:8080 --name %CONTAINER_NAME% %IMAGE_NAME%
