#/bin/bash
curl -X POST -d "msg=Oi 1 testando&author=marcos" http://localhost:8080/simpleWebService/rest/post
curl -X POST -d "msg=Oi 2 testando&author=joao" http://localhost:8080/simpleWebService/rest/post
curl -X POST -d "msg=Oi 3 testando&author=pedro" http://localhost:8080/simpleWebService/rest/post
curl -X POST -d "msg=Oi 4 testando&author=maria" http://localhost:8080/simpleWebService/rest/post

curl -X POST -d "msg=Ei 1  testando&author=pedro" http://localhost:8080/simpleWebService/rest/post/1/comment
curl -X POST -d "msg=Ei 1  testando 2&author=joao" http://localhost:8080/simpleWebService/rest/post/1/comment
curl -X POST -d "msg=Ei 2  testando&author=marcos" http://localhost:8080/simpleWebService/rest/post/2/comment
curl -X POST -d "msg=Ei 3  testando&author=topera" http://localhost:8080/simpleWebService/rest/post/3/comment
curl -X POST -d "msg=Ei 4  testando&author=topera2" http://localhost:8080/simpleWebService/rest/post/4/comment
