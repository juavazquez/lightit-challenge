#!/bin/bash

curl -X POST "http://localhost:8080/api/users" \
    -H "Content-Type: multipart/form-data" \
    -H "Authorization: Bearer token123" \
    -F "email=example.com"