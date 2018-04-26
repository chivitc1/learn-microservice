FROM postgres:9.6
MAINTAINER chi.nguyen

# add user
RUN groupadd multiplication
RUN useradd multiplication -g multiplication -m -s /bin/bash
RUN echo "multiplication:multiplication" | chpasswd

# mv setup files
ADD ./files/docker-entrypoint-initdb.d/* /docker-entrypoint-initdb.d/
ADD ./files/postgresql-conf.d/* /postgresql-conf.d/
RUN chown multiplication:multiplication /postgresql-conf.d/* -R

## setup container
ENV PGDATA /usr/local/data/
ENV TZ Asia/Hanoi
ADD ./files/etc/profile /etc/profile
COPY ./files/docker-entrypoint.sh /
ENTRYPOINT ["/docker-entrypoint.sh", "multiplication"]
