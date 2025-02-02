Spring Boot WebSocket Chat Application
A simple real-time chat application built using Spring Boot, WebSockets, and Redis. This application supports multiple users chatting in real-time, with the ability to join and leave the chat.

Features
Real-Time Communication: Utilizes WebSockets and Redis for broadcasting messages to all connected clients.
User Management: Users can join with a username, and the system displays notifications when users join or leave the chat.
Message Broadcasting: Chat messages are sent and received by all connected users in real-time.
Technologies Used
Backend:

Spring Boot for the application framework.
WebSocket (STOMP) for real-time communication.
Redis for message brokering.
DragonflyDB (used in place of Redis for better performance in some scenarios).
Frontend:

HTML and JavaScript for the user interface.
SockJS and STOMP.js for WebSocket communication.
Docker:

DragonflyDB is containerized with Docker.
How to Run
Backend (Spring Boot)
Clone the repository and open it in your favorite IDE.
Ensure you have Java 11+ installed and Maven or Gradle for building the project.
Build and run the Spring Boot application:
bash
Copy
Edit
mvn spring-boot:run
Redis & DragonflyDB
DragonflyDB is used to handle the Redis communication layer.

Run DragonflyDB using Docker (included in docker-compose.yml):
bash
Copy
Edit
docker-compose up
Frontend
Open the index.html in a web browser.
Enter a username and click Start Chatting to join the chat room.
Type messages and watch them broadcast to all connected clients.
Directory Structure
graphql
Copy
Edit
src/
  ├── main/
  │   ├── java/
  │   │   └── com/
  │   │       └── smalpeddi/
  │   │           └── chatapp/
  │   │               ├── controller/      # WebSocket controllers
  │   │               ├── listener/        # Redis message listeners
  │   │               ├── config/          # WebSocket and Redis configuration
  │   │               └── dto/             # Data transfer objects (ChatMessage, MessageType)
  │   └── resources/
  │       └── application.properties
  └── public/                    # Frontend (HTML, JS)
      └── index.html              # Chat interface
How it Works
WebSocket: The frontend establishes a WebSocket connection using SockJS and STOMP.
Message Exchange: Messages are exchanged between clients using the /app/chat.sendChatMessage and /app/chat.addUser endpoints. These messages are forwarded to the public topic (/topic/public).
Redis for Message Broadcasting: Redis is used to propagate messages across multiple instances of the application to ensure scalability and performance.
DragonflyDB: DragonflyDB is used as the Redis backend to improve performance and scalability.
Configuration
WebSocket: Configured in the Websocket.java class with an endpoint /chat-app.
Redis: Managed using RedisTemplate for sending and receiving messages.
Docker: The docker-compose.yml file includes configuration for DragonflyDB.
