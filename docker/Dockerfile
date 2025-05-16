FROM gcc:latest

COPY helloworld.cpp /app/helloworld.cpp

RUN g++ /app/helloworld.cpp -o /app/helloworld

CMD ["/app/helloworld"]