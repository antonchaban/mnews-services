# Use the official Node.js image as the base image
FROM node:latest as build

# Set the working directory in the container
WORKDIR /app

# Copy package.json and package-lock.json to the working directory
COPY package.json package-lock.json ./

# Install dependencies
RUN npm install

# Copy the entire project directory into the container
COPY . .

# Build the React app for production
RUN npm run build

# Use Nginx as the base image for serving the React app
FROM nginx:alpine

# Copy the built React app from the previous stage to the nginx public directory
COPY --from=build /app/build /usr/share/nginx/html

# Expose port 80 to the outside world
EXPOSE 80

# Start Nginx server when the container starts
CMD ["nginx", "-g", "daemon off;"]
