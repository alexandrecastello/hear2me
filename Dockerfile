FROM python:3.8-buster

COPY testapi.py testapi.py
COPY transcriber.py transcriber.py
COPY preprocessing.py preprocessing.py
COPY gpt3.py gpt3.py
COPY requirements.txt /requirements.txt
COPY .env .env
COPY model.joblib model.joblib


RUN pip install -U pip
RUN pip install fastapi uvicorn
RUN pip install -r requirements.txt
RUN apt-get update -y && apt-get install -y --no-install-recommends build-essential gcc \
  libsndfile1
RUN apt-get -y update
RUN apt-get -y upgrade
RUN apt-get install -y ffmpeg

CMD uvicorn testapi:app --host 0.0.0.0 --port $PORT
