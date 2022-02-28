# wefox Group Engineer Challenge

Technical Test Inc. is a clothing company that recently had a big commercial success. As a result, the number of transactions has dramatically increased, and the old system doesn't scale to the required transaction rate on peak hours. 

For this reason the system has been redesigned and one of the key pieces is the new payment processor. This new microservice will consume payments from the message broker and process them at its own pace. In addition, it will communicate via REST API with third parties for validation and logging. Your goal is to implement this microservice.