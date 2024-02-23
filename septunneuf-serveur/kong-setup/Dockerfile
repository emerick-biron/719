FROM python:3.11-slim-bookworm

WORKDIR setup

COPY main.py requirements.txt create-service-bodies.json create-route-bodies.json ./

RUN pip install -r requirements.txt

ENTRYPOINT ["python3", "./main.py"]