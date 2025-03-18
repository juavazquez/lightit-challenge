#!/bin/bash

curl -X POST "http://localhost:8080/api/users" \
    -H "Content-Type: multipart/form-data" \
    -H "Authorization: Bearer token123" \
    -F "email=example@email.com" \
    -F "firstName=John" \
    -F "lastName=Doe" \
    -F "phoneNumber=+541234567890" \
    -F "streetLine=Calle 123" \
    -F "city=CABA" \
    -F "state=Buenos Aires" \
    -F "country=Argentina" \
    -F "documentImg=@files/mclovin_document.jpg"
