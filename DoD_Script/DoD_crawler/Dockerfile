FROM python:3.12.6

WORKDIR /app
RUN apt-get -y update && \
    apt install wget && \
    wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb && \
    apt -y install ./google-chrome-stable_current_amd64.deb
# Google Chrome 다운로드 및 설치

COPY . .

COPY requirements.txt .
RUN pip install -r requirements.txt

CMD ["python", "SteamCrawler.py"]