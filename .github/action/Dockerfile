FROM ubuntu:focal

RUN mkdir -p /action
COPY entrypoint.sh /action/entrypoint.sh
RUN chmod 755 /action/entrypoint.sh

CMD /action/entrypoint.sh