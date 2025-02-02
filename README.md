
Here's a concise and informative README for your Spring Boot WebSocket Chat Application:

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
