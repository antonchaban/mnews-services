# # syntax=docker/dockerfile:1

# FROM python:3.10-slim-buster

# # Set environment variables
# ENV VIRTUAL_ENV=/opt/venv
# ENV PATH="$VIRTUAL_ENV/bin:$PATH"

# # Set working directory
# WORKDIR /UserInfoService

# # Copy entire project directory
# COPY . .

# # Install Flask and other dependencies
# RUN pip install Flask scikit-learn

# EXPOSE 5000
# # Command to run the Flask application
# CMD ["python", "-m", "flask", "run", "--host=0.0.0.0"]

FROM python:3.10-slim-buster
WORKDIR /app
COPY ./requirements.txt /app
RUN pip install -r requirements.txt
COPY . .
EXPOSE 5000
ENV FLASK_APP=app.py
CMD ["flask", "run", "--host", "0.0.0.0"]
