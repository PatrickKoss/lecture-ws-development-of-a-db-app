FROM postgres:13

# Install Python dependencies ---------
RUN apt-get update && apt dist-upgrade -y
RUN apt-get install ca-certificates --yes
RUN gpg-agent --daemon --enable-ssh-support
RUN apt install software-properties-common --yes
RUN apt-get install ca-certificates --yes
RUN gpg-agent --daemon --enable-ssh-support
RUN add-apt-repository ppa:deadsnakes/ppa --yes
RUN apt install python3.7 --yes
RUN apt-get -y install python3-pip
RUN python3.7 -m pip install wheel

WORKDIR /app

COPY requirements.txt .
RUN pip3 install --no-cache-dir -r requirements.txt && rm requirements.txt

ENV PYTHONPATH=/app
ENV PYTHONUNBUFFERED 1
ENV PYTHONDONTWRITEBYTECODE 1

# COPY main.py .
# COPY ./backup_restore /backup_restore
