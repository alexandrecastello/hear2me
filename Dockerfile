FROM python:3.8-buster

COPY testapi.py testapi.py
COPY transcriber.py transcriber.py
COPY preprocessing.py preprocessing.py
COPY gpt3.py gpt3.py
COPY requirements.txt /requirements.txt

RUN pip install -U pip
RUN pip install fastapi uvicorn
RUN pip install -r requirements.txt

CMD uvicorn testapi:app --host 0.0.0.0 --port $PORT
