FROM ubuntu:focal

ARG DEBIAN_FRONTEND=noninteractive
RUN apt-get update -y
RUN apt-get upgrade -y

RUN apt-get install openjdk-11-jdk -y
RUN java --version

RUN apt-get install gradle -y
RUN gradle --version

RUN mkdir /work
COPY ./ /work/