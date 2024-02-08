FROM python:3.11-slim-bookworm

WORKDIR setup

COPY main.py requirements.txt ./

RUN pip install -r requirements.txt

ENTRYPOINT ["python3", "./main.py"]