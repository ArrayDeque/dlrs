# API Usage
DLRS exposes a few endpoints for your apps to store and retrieve atomic user activities. Every 'measurable' user activity is represented by a 'Statement' or action. 

A 'Statement' can be sent to a DLRS with 3 primary payload details:

* **Actor** - Who is this user?
* **Verb** - What did he do?
* **Object** - What is he learning?

## To record a statement
**Case -** A user is going through a learning activity and I want to record what he is doing so I can analyze the progress later.

To record a Statement, send a POST request.

```http
POST /learn/statement
```
with the following JSON request body:

```json
{
  "actor": {
    "objectType": "User",
    "name": "Abel Osbert",
    "mbox": "mailto:aosbert@example.com"
  },
  "verb": {
    "id": "http://adlnet.gov/expapi/verbs/completed",
    "display": {
      "en-US": "completed"
    }
  },
  "object": {
    "objectType": "Activity",
    "id": "http://www.example.com/activities/001",
    "definition": {
      "name": {
        "en-US": "Example Activity"
      },
    "type": "http://adlnet.gov/expapi/activities/course"
    }
  }
}
```
This API returns a Statement ID.

A Python example:

```python
import http.client

conn = http.client.HTTPConnection("127.0.0.1:8678")

payload = "{\n\"actor\": {\n\"name\": \"Sally\",\n\"mbox\": \"mailto:sally@example.com\"\n},\n\"verb\": {\n\"id\":\"http://adlnet.gov/expapi/verbs/experienced\",\n\"display\": {\"en-US\": \"experienced\"}\n},\n\"object\": {\n\"id\": \"http://example.com/activities/solo-hang-gliding\",\n\"definition\": {\n\"name\": { \"en-US\":\"Hitchhiking\" }\n}\n}\n}"

headers = {
    'content-type': "application/json",
    'cache-control': "no-cache"
    }

conn.request("POST", "/learn/statement", payload, headers)

res = conn.getresponse()
data = res.read()

print(data.decode("utf-8"))
```

A PHP example:

```PHP
<?php

$client = new http\Client;
$request = new http\Client\Request;

$body = new http\Message\Body;
$body->append('{
    "actor": {
        "name": "Sally",
        "mbox": "mailto:sally@example.com"
     },
     "verb": {
         "id": "http://adlnet.gov/expapi/verbs/experienced",
         "display": {"en-US": "experienced"}
         },
     "object": {
         "id": "http://example.com/activities/solo-hang-gliding",
         "definition": {
             "name": { "en-US": "Hitchhiking" }
         }
     }
}');

$request->setRequestUrl('http://127.0.0.1:8678/learn/statement');
$request->setRequestMethod('POST');
$request->setBody($body);

$request->setHeaders(array(
  'cache-control' => 'no-cache',
  'content-type' => 'application/json'
));

$client->enqueue($request)->send();
$response = $client->getResponse();

echo $response->getBody();
```

## To get a statement
**Case -** I need to know the details of a particular user activity so that I can find who participated and what was really done.

To get details about a statement from the Statement ID, send a GET request.

```http
GET /learn/statement/<STATEMENT ID>
```

For example:

```http
GET /learn/statement/a29f2f75-37cb-44b8-83fc-54a812a1d018
```

Example success response:

```json
{
  "Statement":[
    "a29f2f75-37cb-44b8-83fc-54a812a1d018",
    "{\"objectType\":\"Agent\",\"name\":\"Joe Schmoe\",\"mbox\":\"mailto:joe@example.com\"}",
    "{\"id\":\"http://adlnet.gov/expapi/verbs/completed\",\"display\":{\"en-US\":\"completed\"}}",
    "{\"objectType\":\"Activity\",\"id\":\"http://www.example.com/activities/001\",\"definition\":{\"name\":{\"en-US\":\"Example              Activity\"},\"type\":\"http://adlnet.gov/expapi/activities/course\"}}",
        1455111694507
  ]
}
```

A Python example:

```python
import http.client

conn = http.client.HTTPConnection("127.0.0.1:8678")

headers = {
    'content-type': "application/json",
    'cache-control': "no-cache"
    }

conn.request("GET", "/learn/statement/9e746713-055e-45de-a1d0-6044509bc52d", headers=headers)

res = conn.getresponse()
data = res.read()

print(data.decode("utf-8"))
```

A PHP example:

```php
<?php

$client = new http\Client;
$request = new http\Client\Request;

$request->setRequestUrl('http://127.0.0.1:8678/learn/statement/9e746713-055e-45de-a1d0-6044509bc52d');
$request->setRequestMethod('GET');
$request->setHeaders(array(
  'cache-control' => 'no-cache',
  'content-type' => 'application/json'
));

$client->enqueue($request)->send();
$response = $client->getResponse();

echo $response->getBody();
```

## To find all statements for an actor
**Case -** How has this user learned something? Is there any way I can enumerate a list of all his activities?

To find all statements for an actor, send a POST request.

```http
POST /learn/statement/findstatementsforactor
```
with the following JSON request body:

```json
{

"actor": {
  "objectType": "User",
  "name": "Abel Osbert",
  "mbox": "mailto:aosbert@example.com"
  }
}
```

Example success response:

```json
{"Statements": [
    "36318877-208c-4ab5-a42b-a1ca3f45ca52",
    "e2cd0c7a-a2e6-451c-b2ef-7fdb8031bef7",
    "5ba063d8-61c3-4200-8326-eaa6f566e353",
    "e2bf61b9-dd8f-4ece-97d3-094e57f35cad",
    "5daa8b09-d982-42e6-9236-5883da2da08f",
    "25017371-1d76-4134-be23-79938c4050a9",
    "a29f2f75-37cb-44b8-83fc-54a812a1d018"
]}
```

Every Statement denotes a discreet, recorded, learning action of the user. Use ```findstatementsforactor``` endpoint to get the Statement details. Note that you can pass any JSON payload as part of actor, verb, and object.

A Python example:

```python
import http.client

conn = http.client.HTTPConnection("127.0.0.1:8678")

headers = {
    'content-type': "application/json",
    'cache-control': "no-cache"
    }

conn.request("GET", "/learn/statement/9e746713-055e-45de-a1d0-6044509bc52d", headers=headers)

res = conn.getresponse()
data = res.read()

print(data.decode("utf-8"))
```

A PHP example:

```php
<?php

$curl = curl_init();

curl_setopt_array($curl, array(
  CURLOPT_PORT => "8678",
  CURLOPT_URL => "http://127.0.0.1:8678/learn/findstatementsforactor",
  CURLOPT_RETURNTRANSFER => true,
  CURLOPT_ENCODING => "",
  CURLOPT_MAXREDIRS => 10,
  CURLOPT_TIMEOUT => 30,
  CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
  CURLOPT_CUSTOMREQUEST => "POST",
  CURLOPT_POSTFIELDS => "{\n\"actor\": {\n\"name\": \"Sally Glider\",\n\"mbox\":\"mailto:sally@example.com\"\n},\n\"verb\": {\n\"id\":\"http://adlnet.gov/expapi/verbs/experienced\",\n\"display\": {\"en-US\":\"experienced\"}\n},\n\"object\": {\n\"id\":\"http://example.com/activities/solo-hang-gliding\",\n\"definition\": {\n\"name\": {\"en-US\": \"Solo Hang Gliding\"}\n}\n}\n}",
  CURLOPT_HTTPHEADER => array(
    "cache-control: no-cache",
    "content-type: application/json"
  ),
));

$response = curl_exec($curl);
$err = curl_error($curl);

curl_close($curl);

if ($err) {
  echo "cURL Error #:" . $err;
} else {
  echo $response;
}
```

Or, simply cURL it up:

```curl
curl -X POST -H "Content-Type: application/json" -H "Cache-Control: no-cache"
 -d '{
    "actor": {
        "name": "Sally Glider",
        "mbox": "mailto:sally@example.com"
     },
     "verb": {
         "id": "http://adlnet.gov/expapi/verbs/experienced",
         "display": {"en-US": "experienced"}
         },
     "object": {
         "id": "http://example.com/activities/solo-hang-gliding",
         "definition": {
             "name": { "en-US": "Solo Hang Gliding" }
         }
     }
}' "http://127.0.0.1:8678/learn/findstatementsforactor"
```

