FROM node:lts-alpine as build-stage
WORKDIR /app
RUN apk update && apk add yarn python g++ make && rm -rf /var/cache/apk/*
COPY package*.json ./
RUN npm install
EXPOSE 8080
CMD ["/bin/sh",  "-c",  "npm run serve"]