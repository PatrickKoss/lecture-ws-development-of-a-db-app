FROM node:lts-alpine
WORKDIR /app
RUN apk update && apk add yarn python g++ make && rm -rf /var/cache/apk/*
COPY package*.json ./
RUN npm install
EXPOSE 8080
EXPOSE 8081
CMD ["/bin/sh",  "-c",  "npm run serve"]