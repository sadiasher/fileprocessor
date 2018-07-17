# fileprocessor

Analyzing jackson2 streaming API, jackson2 object modal API. gson streaming API, gson object modal API and json-simple library processing time on large json file

Download citylots,json file  from github https://github.com/zemirco/sf-city-lots-json

Modify file path in class "App.java"

Technology Stack:
=================
Java8
Maven

How ro Run:
===========
java -jar ./fileprocessor/target/fileprocessor-jar-with-dependencies.jar


Output:
=======

File: citylots.json
File Size: 180.98661422729492 mb
No of times file will parse against each library: 10
Parsing Starts..... 
Parsing completed.....

[
    {
        "parsingApi": "GsonStreamApi",
        "timeInMillis": [
            4496,
            4246,
            4123,
            4113,
            4062,
            4002,
            4365,
            4810,
            3996,
            4168
        ],
        "averageTime": 4238.1
    },
    {
        "parsingApi": "GsonObjectApi",
        "timeInMillis": [
            4268,
            3962,
            5220,
            4242,
            5352,
            4849,
            4375,
            4584,
            4098,
            4053
        ],
        "averageTime": 4500.3
    },
    {
        "parsingApi": "JacksonStreamApi",
        "timeInMillis": [
            4850,
            4432,
            4049,
            3975,
            4537,
            4178,
            4422,
            4192,
            4113,
            4300
        ],
        "averageTime": 4304.8
    },
    {
        "parsingApi": "JacksonObjectApi",
        "timeInMillis": [
            5168,
            4768,
            4076,
            4316,
            4030,
            4961,
            4084,
            4729,
            4337,
            4048
        ],
        "averageTime": 4451.7
    },
    {
        "parsingApi": "JsonSimpleApi",
        "timeInMillis": [
            6510,
            7817,
            7022,
            6667,
            6980,
            7137,
            6970,
            6877,
            6861,
            7569
        ],
        "averageTime": 7041
    }
]
