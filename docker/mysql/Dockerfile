FROM mysql:8.0
EXPOSE 3306
COPY ./my.cnf /etc/mysql/my.conf
RUN chmod 644 /etc/mysql/my.conf
COPY ./sql_init /docker-entrypoint-initdb.d
