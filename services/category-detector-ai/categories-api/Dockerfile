# syntax=docker/dockerfile:1

FROM python:3.10-slim-buster

# Set environment variables
ENV VIRTUAL_ENV=/opt/venv
ENV PATH="$VIRTUAL_ENV/bin:$PATH"

# Set working directory
WORKDIR /UserInfoService

# Copy entire project directory
COPY . .

# Install Flask and other dependencies
RUN pip install Flask scikit-learn


# Command to run the Flask application
CMD ["python", "-m", "flask", "run", "--host=0.0.0.0"]
