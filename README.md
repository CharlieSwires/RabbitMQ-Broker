rabbit
------
<p>in git bash</p>
<p>git clone https://gitlab.com/UnicardLtd/product/RabbitMQ-Broker.git</p>
<p>This contains the java</p>

build
-----

<p>you'll need an application.properties file for the RabbitMQ settings and spring</p>
<p>mvn package</p>

<p>produces rabbit-0.0.1-SNAPSHOT.jar in target</p>


deploy
------
<p>docker run -d --name some-mongo \
	-e MONGO_INITDB_ROOT_USERNAME=root \
	-e MONGO_INITDB_ROOT_PASSWORD=? \
	mongo:6.0
</p>
<p>If there isn't one deployed and running you'll need rabbitmq.</p>
<p>docker pull rabbitmq:3.10-management</p>
<p>docker run --name rabbitmq --rm -it -p 15672:15672 -p 5672:5672 rabbitmq:3.10-management</p>
<br>
<p>docker build --tag rabbit:latest .</p>
<p>docker run --name rabbit --link rabbitmq -d -p 9900:8080 rabbit:latest</p>


RESTful
-------
<code> payload:
[
    {
        "customerId": "charlie2",
        "usedDate": "2022-10-12T00:00:00.500+00:00",
        "xmlData": "<start attrib=\"ishvie\">hurbfwrbwrb</start>"
    },
    {
        "customerId": "swires2",
        "usedDate": "2022-10-13",
        "xmlData": "<start></start>"
    }
]
</code>
<p>POST - http://localhost:9900/UniCard/api/v1/dataarray/?</p>
<p>GET - http://localhost:9900/UniCard/api/v1/dataarray/2020-01-01/2023-01-01/0/?</p>
<p> where ? can be one or two.</p>

